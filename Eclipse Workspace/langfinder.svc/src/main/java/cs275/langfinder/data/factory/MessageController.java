package cs275.langfinder.data.factory;

import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import cs275.langfinder.data.UserMessage;
import cs275.langfinder.data.UserMessageFolder;
import cs275.langfinder.data.conn.call.DbHelper;
import cs275.langfinder.util.GsonSerializer;

public class MessageController {

	public static UserMessageFolder[] getMessageFolderTypes() {
		SessionFactory factory = DbHelper.getSessionFactory();
		Session session = factory.openSession();
		try {
			List<UserMessageFolder> objects = 
				(List<UserMessageFolder>)session.createQuery("FROM UserMessageFolder")
				.list();
			return objects.toArray(new UserMessageFolder[0]);
		} finally {
			session.close();
		}
	}

	public static UserMessage[] getMessagesForUser(int owningUserId, int userMessageFolderId) {
		SessionFactory factory = DbHelper.getSessionFactory();
		Session session = factory.openSession();
		try {
			List<UserMessage> objects = 
				(List<UserMessage>)session.createQuery("FROM UserMessage where owningUserId = :owningUserId and userMessageFolderId = :userMessageFolderId and isDeleted = 0")
				.setParameter("owningUserId", owningUserId)
				.setParameter("userMessageFolderId", userMessageFolderId)
				.list();

			return objects.toArray(new UserMessage[0]);
		} finally {
			session.close();
		}
	}

	public static UserMessage getMessage(int messageId) {
		SessionFactory factory = DbHelper.getSessionFactory();
		Session session = factory.openSession();
		try {
			UserMessage message = (UserMessage)session.load(UserMessage.class, messageId);
			message = GsonSerializer.unproxy(message);
			return message;
		} finally {
			session.close();
		}
	}

	public static void updateMessageRead(int messageId, boolean isRead) {
		SessionFactory factory = DbHelper.getSessionFactory();
		Session session = factory.openSession();
		try {
			Transaction tx = session.beginTransaction();
			UserMessage message = (UserMessage)session.load(UserMessage.class, messageId);
			message.setIsRead(isRead);
			session.update(message);
			tx.commit();
			session.flush();
		} finally {
			session.close();
		}
	}

	public static void deleteMessage(int messageId) {
		SessionFactory factory = DbHelper.getSessionFactory();
		Session session = factory.openSession();
		try {
			Transaction tx = session.beginTransaction();
			UserMessage message = (UserMessage)session.load(UserMessage.class, messageId);
			message.setIsDeleted(true);
			session.update(message);
			tx.commit();
			session.flush();
		} finally {
			session.close();
		}
	}

	public static void sendMessage(int fromUserId, int toUserId, String subject, String message) {
		int sentUserMessageFolderId = 2;
		int inboxUserMessageFolderId = 1;

		UserMessage messageObjSent = new UserMessage(null, fromUserId, fromUserId, toUserId, new Date(), subject, message, false, false, sentUserMessageFolderId);
		UserMessage messageObjReceived = new UserMessage(null, toUserId, fromUserId, toUserId, new Date(), subject, message, false, false, inboxUserMessageFolderId);

		SessionFactory factory = DbHelper.getSessionFactory();
		Session session = factory.openSession();
		try {
			Transaction tx = session.beginTransaction();
			session.save(messageObjSent);
			session.save(messageObjReceived);
			tx.commit();
			session.flush();
		} finally {
			session.close();
		}
	}
}
