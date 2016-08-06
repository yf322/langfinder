package cs275.langfinder.data.factory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import cs275.langfinder.data.Language;
import cs275.langfinder.data.LanguageLevel;
import cs275.langfinder.data.User;
import cs275.langfinder.data.UserConnection;
import cs275.langfinder.data.UserConnectionBundle;
import cs275.langfinder.data.UserLanguage;
import cs275.langfinder.data.UserLanguageBundle;
import cs275.langfinder.data.UserProfile;
import cs275.langfinder.data.UserProfileBundle;
import cs275.langfinder.data.conn.call.DbHelper;
import cs275.langfinder.util.GsonSerializer;
import cs275.langfinder.util.HttpCacher;

public class UserController {


	public static Integer login(String email, String passwordHash) {
		int userId;
		SessionFactory factory = DbHelper.getSessionFactory();
		Session session = factory.openSession();
		try {
			List<User> users = 
				(List<User>)session.createQuery("FROM User where email = :email and passwordHash = :passwordHash")
				.setParameter("email", email)
				.setParameter("passwordHash", passwordHash)
				.list();
			if(users.size()==0)
				return null;

			User user = users.get(0);
			
			System.out.println("Saving session var userId: " + user.getId());

			HttpSession webSession = HttpCacher.getSession();

			webSession.setAttribute("userId", user.getId());
			userId = user.getId();
		} finally {
			session.close();
		}
		updateUserLastActive();
		return userId;
	}

	public static Integer getCurrentUserId() {
		HttpSession webSession = HttpCacher.getSession();
		System.out.println("Getting session var userId: " + webSession.getAttribute("userId"));
		return (Integer)webSession.getAttribute("userId");
	}

	public static void updateUserLastActive() {
		Integer userId = getCurrentUserId();
		if(userId==null) return;
		SessionFactory factory = DbHelper.getSessionFactory();
		Session session = factory.openSession();
		try {
			Transaction tx = session.beginTransaction();
			User user = (User)session.load(User.class, userId);
			if(user==null) return;
			user.setLastActiveUTC(new Date());
			session.update(user);
			tx.commit();
			session.flush();
		} finally {
			session.close();
		}
	}

	public static UserProfileBundle[] searchUsers(String name, Integer languageId, Integer languageLevelId) {
		SessionFactory factory = DbHelper.getSessionFactory();
		Session session = factory.openSession();
		try {
			List<UserProfileBundle> returnObjects = new ArrayList<UserProfileBundle>();
			List<Object[]> users = 
				session.createSQLQuery(
"Select "
+ " {User.*}, "
+ " {UserProfile.*} "
+ " FROM "
+ "    User "
+ "	left JOIN UserProfile ON"
+ "		User.id=UserProfile.userId"
+ "	left JOIN UserLanguage ON"
+ "		User.id=UserLanguage.userId"
+ "		and :languageId IS NOT NULL"
+ "		and :languageLevelId IS NOT NULL"
+ "	left JOIN Language ON"
+ "		UserLanguage.languageId=Language.id"
+ "		and :languageId IS NOT NULL"
+ "	left JOIN LanguageLevel ON"
+ "		UserLanguage.languageLevelId=LanguageLevel.id"
+ "		and :languageLevelId IS NOT NULL "
+ "WHERE"
+ "	(:languageId IS NULL OR :languageId=Language.id) AND"
+ "	(:languageLevelId IS NULL OR :languageLevelId=LanguageLevel.id) AND"
+ "	(:name IS NULL OR CONCAT(User.firstName,' ',User.lastName) LIKE CONCAT('%',:name,'%'))"
+ "ORDER BY"
+ "	User.lastActiveUTC DESC")
.addEntity("User", User.class)
.addEntity("UserProfile", UserProfile.class)
				.setParameter("languageId", languageId)
				.setParameter("languageLevelId", languageLevelId)
				.setParameter("name", name)
				.list();
			if(users.size()==0)
				return null;

			for(Object[] userObjects : users ) {
				User user = null;
				UserProfile userProfile = null;
				UserLanguage userLanguage = null;
				Language language = null;
				LanguageLevel languageLevel = null;
	
				for(Object userObject : userObjects) {
					if(userObject instanceof User)
						user = (User)userObject;
					else if(userObject instanceof UserProfile)
						userProfile = (UserProfile)userObject;
					else if(userObject instanceof UserLanguage)
						userLanguage = (UserLanguage)userObject;
					else if(userObject instanceof Language)
						language = (Language)userObject;
					else if(userObject instanceof LanguageLevel)
						languageLevel = (LanguageLevel)userObject;
				}
	
				UserProfileBundle bundle = new UserProfileBundle();
				bundle.setUser(user);
				bundle.setUserProfile(userProfile);
	
				returnObjects.add(bundle);
			}	
			return returnObjects.toArray(new UserProfileBundle[0]);
		} finally {
			session.close();
		}

	}

	public static UserProfileBundle getUserWithProfile(int userId) {
		SessionFactory factory = DbHelper.getSessionFactory();
		Session session = factory.openSession();
		try {
			List<UserProfileBundle> returnObjects = new ArrayList<UserProfileBundle>();
			Object[] userObjects = (Object[])
				session.createSQLQuery(
"Select "
+ " {User.*}, "
+ " {UserProfile.*} "
+ " FROM "
+ "    User "
+ "	left JOIN UserProfile ON"
+ "		User.id=UserProfile.userId"

+ " WHERE User.id = :userId" )
.addEntity("User", User.class)
.addEntity("UserProfile", UserProfile.class)
				.setParameter("userId", userId)
				.uniqueResult();
			
			if(userObjects==null) return null;

			User user = null;
			UserProfile userProfile = null;

			for(Object userObject : userObjects) {
				if(userObject instanceof User)
					user = (User)userObject;
				else if(userObject instanceof UserProfile)
					userProfile = (UserProfile)userObject;
			}

			UserProfileBundle bundle = new UserProfileBundle();
			bundle.setUser(user);
			bundle.setUserProfile(userProfile);

			return bundle;
		} finally {
			session.close();
		}
	}

	public static boolean getUsersAreFriends(int fromUserId, int otherUserId) {
		SessionFactory factory = DbHelper.getSessionFactory();
		Session session = factory.openSession();
		try {
			UserConnection connection = 
				(UserConnection)session.createQuery("FROM UserConnection where user1Id = :fromUserId and user2Id = :otherUserId and confirmed = 1")
				.setParameter("fromUserId", fromUserId)
				.setParameter("otherUserId", otherUserId)
				.uniqueResult();

			if(connection==null) return false;
			return connection.getConfirmed();
		} finally {
			session.close();
		}
	}

	public static void removeFriend(int fromUserId, int otherUserId) {
		SessionFactory factory = DbHelper.getSessionFactory();
		Session session = factory.openSession();
		try {
			Transaction tx = session.beginTransaction();
			UserConnection connection = 
				(UserConnection)session.createQuery("FROM UserConnection where user1Id = :fromUserId and user2Id = :otherUserId")
				.setParameter("fromUserId", fromUserId)
				.setParameter("otherUserId", otherUserId)
				.uniqueResult();

			if(connection==null) return;
			session.delete(connection);

			connection = 
					(UserConnection)session.createQuery("FROM UserConnection where user1Id = :fromUserId and user2Id = :otherUserId")
					.setParameter("fromUserId", otherUserId)
					.setParameter("otherUserId", fromUserId)
					.uniqueResult();
			if(connection==null) return;
			session.delete(connection);
			tx.commit();
			session.flush();
		} finally {
			session.close();
		}
	}

	public static UserConnectionBundle[] getUserInviteProfiles(int userId) {
		SessionFactory factory = DbHelper.getSessionFactory();
		Session session = factory.openSession();
		try {
			List<UserConnectionBundle> returnObjects = new ArrayList<UserConnectionBundle>();
			List<Object[]> objects = (List<Object[]>)
				session.createSQLQuery(
"Select "
+ " {User.*}, "
+ " {UserProfile.*}, "
+ " {UserConnection.*} "
+ " FROM "
+ "    UserConnection "
+ "	left JOIN User ON"
+ "		User.id=UserConnection.user1Id"
+ "	left JOIN UserProfile ON"
+ "		User.id=UserProfile.userId"

+ " WHERE"
+ " UserConnection.confirmed = 0"
+ " and UserConnection.user2Id = :userId" )
.addEntity("User", User.class)
.addEntity("UserProfile", UserProfile.class)
.addEntity("UserConnection", UserConnection.class)
				.setParameter("userId", userId)
				.list();
			for(Object[] userObjects : objects) {
				if(userObjects==null) return null;
	
				User user = null;
				UserProfile userProfile = null;
				UserConnection userConnection = null;
	
				for(Object userObject : userObjects) {
					if(userObject instanceof User)
						user = (User)userObject;
					else if(userObject instanceof UserProfile)
						userProfile = (UserProfile)userObject;
					else if(userObject instanceof UserConnection)
						userConnection = (UserConnection)userObject;
				}
	
				UserConnectionBundle bundle = new UserConnectionBundle();
				bundle.setUser(user);
				bundle.setUser2Profile(userProfile);
				bundle.setUserConnection(userConnection);
				returnObjects.add(bundle);
			}
			
			return returnObjects.toArray(new UserConnectionBundle[0]);
		} finally {
			session.close();
		}
	}

	public static UserProfileBundle[] getUserConnectionProfiles(int userId) {
		SessionFactory factory = DbHelper.getSessionFactory();
		Session session = factory.openSession();
		try {
			List<UserProfileBundle> returnObjects = new ArrayList<UserProfileBundle>();
			List<Object[]> objects = (List<Object[]>)
				session.createSQLQuery(
"Select "
+ " {User.*}, "
+ " {UserProfile.*}, "
+ " {UserConnection.*} "
+ " FROM "
+ "    UserConnection "
+ "	left JOIN User ON"
+ "		User.id=UserConnection.user1Id"
+ "	left JOIN UserProfile ON"
+ "		User.id=UserProfile.userId"

+ " WHERE"
+ " UserConnection.confirmed = 1"
+ " and UserConnection.user2Id = :userId")
.addEntity("User", User.class)
.addEntity("UserProfile", UserProfile.class)
.addEntity("UserConnection", UserConnection.class)
				.setParameter("userId", userId)
				.list();
			for(Object[] userObjects : objects) {
				if(userObjects==null) return null;
	
				User user = null;
				UserProfile userProfile = null;
				UserConnection userConnection = null;
	
				for(Object userObject : userObjects) {
					if(userObject instanceof User)
						user = (User)userObject;
					else if(userObject instanceof UserProfile)
						userProfile = (UserProfile)userObject;
					else if(userObject instanceof UserConnection)
						userConnection = (UserConnection)userObject;
				}
	
				UserProfileBundle bundle = new UserProfileBundle();
				bundle.setUser(user);
				bundle.setUserProfile(userProfile);
				returnObjects.add(bundle);
			}
			
			return returnObjects.toArray(new UserProfileBundle[0]);
		} finally {
			session.close();
		}
	}


	public static void inviteUser(int fromUserId, int toUserId, String inviteMessage) {
		SessionFactory factory = DbHelper.getSessionFactory();
		Session session = factory.openSession();
		try {
			Transaction tx = session.beginTransaction();
			UserConnection connection = 
				(UserConnection)session.createQuery("FROM UserConnection where user1Id = :fromUserId and user2Id = :otherUserId and confirmed = 1")
				.setParameter("fromUserId", fromUserId)
				.setParameter("otherUserId", toUserId)
				.uniqueResult();

			if(connection!=null) return;

			UserConnection forward = new UserConnection(null, fromUserId, toUserId, inviteMessage, false, false, null);
			session.save(forward);
			session.flush();
			UserConnection reverse = new UserConnection(null, toUserId, fromUserId, inviteMessage, false, false, forward.getId());
			session.save(reverse);
			session.flush();
			forward.setComplementUserConnectionId(reverse.getId());
			session.update(forward);
			tx.commit();
			session.flush();
		} finally {
			session.close();
		}
	}

	public static void updateInvite(int fromUserId, int toUserId, boolean confirmed, boolean ignored) {
		SessionFactory factory = DbHelper.getSessionFactory();
		Session session = factory.openSession();
		try {
			Transaction tx = session.beginTransaction();
			UserConnection connection = 
				(UserConnection)session.createQuery("FROM UserConnection where user1Id = :fromUserId and user2Id = :otherUserId")
				.setParameter("fromUserId", fromUserId)
				.setParameter("otherUserId", toUserId)
				.uniqueResult();

			if(connection==null) return;
			connection.setConfirmed(confirmed);
			connection.setIgnored(ignored);

			session.update(connection);

			connection = 
					(UserConnection)session.createQuery("FROM UserConnection where user1Id = :fromUserId and user2Id = :otherUserId")
					.setParameter("fromUserId", toUserId)
					.setParameter("otherUserId", fromUserId)
					.uniqueResult();

			if(connection==null) return;
			connection.setConfirmed(confirmed);
			connection.setIgnored(ignored);
			session.update(connection);
			tx.commit();
			session.flush();
		} finally {
			session.close();
		}
	}

	public static void updateProfile(int userId, String aboutText, String lookingFor) {
		SessionFactory factory = DbHelper.getSessionFactory();
		Session session = factory.openSession();
		try {
			Transaction tx = session.beginTransaction();
			UserProfile userProfile = 
					(UserProfile)session.createQuery("FROM UserProfile where userId = :userId")
					.setParameter("userId", userId)
					.uniqueResult();
			if(userProfile==null) throw new RuntimeException("Cannot find user");
			userProfile.setAboutText(aboutText);
			userProfile.setLookingForText(lookingFor);
			session.update(userProfile);
			tx.commit();
			session.flush();
		} finally {
			session.close();
		}
	}

	public static UserLanguageBundle[] getUserLanguages(int userId) {
		SessionFactory factory = DbHelper.getSessionFactory();
		Session session = factory.openSession();
		try {
			List<UserLanguageBundle> returnObjects = new ArrayList<UserLanguageBundle>();
			List<Object[]> objects = 
				session.createSQLQuery(
"Select "
+ " {UserLanguage.*}, "
+ " {Language.*}, "
+ " {LanguageLevel.*} "
+ " FROM "
+ "    User "
+ "	inner JOIN UserProfile ON"
+ "		User.id=UserProfile.userId"
+ "	inner JOIN UserLanguage ON"
+ "		User.id=UserLanguage.userId"
+ "	left JOIN Language ON"
+ "		UserLanguage.languageId=Language.id"
+ "	left JOIN LanguageLevel ON"
+ "		UserLanguage.languageLevelId=LanguageLevel.id"
+ " WHERE"
+ "	:userId =User.id "
)
.addEntity("UserLanguage", UserLanguage.class)
.addEntity("Language", Language.class)
.addEntity("LanguageLevel", LanguageLevel.class)
				.setParameter("userId", userId)
				.list();

			for(Object[] rowObjects : objects ) {
				UserLanguage userLanguage = null;
				Language language = null;
				LanguageLevel languageLevel = null;
	
				for(Object singleObject : rowObjects) {
					if(singleObject instanceof UserLanguage)
						userLanguage = (UserLanguage)singleObject;
					else if(singleObject instanceof Language)
						language = (Language)singleObject;
					else if(singleObject instanceof LanguageLevel)
						languageLevel = (LanguageLevel)singleObject;
				}
	
				UserLanguageBundle bundle = new UserLanguageBundle();
				bundle.setUserLanguage(userLanguage);
				bundle.setLanguage(language);
				bundle.setLanguageLevel(languageLevel);
				returnObjects.add(bundle);
			}	
			return returnObjects.toArray(new UserLanguageBundle[0]);
		} finally {
			session.close();
		}

	}

	public static UserLanguageBundle getUserLanguage(int userId, int lanuageInfoId) {
		SessionFactory factory = DbHelper.getSessionFactory();
		Session session = factory.openSession();
		try {
			Object[] rowObjects = (Object[])
				session.createSQLQuery(
"Select "
+ " {UserLanguage.*}, "
+ " {Language.*}, "
+ " {LanguageLevel.*} "
+ " FROM "
+ "    User "
+ "	inner JOIN UserProfile ON"
+ "		User.id=UserProfile.userId"
+ "	inner JOIN UserLanguage ON"
+ "		User.id=UserLanguage.userId"
+ "	left JOIN Language ON"
+ "		UserLanguage.languageId=Language.id"
+ "	left JOIN LanguageLevel ON"
+ "		UserLanguage.languageLevelId=LanguageLevel.id"
+ " WHERE"
+ "	:userId =User.id "
)
.addEntity("UserLanguage", UserLanguage.class)
.addEntity("Language", Language.class)
.addEntity("LanguageLevel", LanguageLevel.class)
				.setParameter("userId", userId)
				.uniqueResult();

				UserLanguage userLanguage = null;
				Language language = null;
				LanguageLevel languageLevel = null;
	
				for(Object singleObject : rowObjects) {
					if(singleObject instanceof UserLanguage)
						userLanguage = (UserLanguage)singleObject;
					else if(singleObject instanceof Language)
						language = (Language)singleObject;
					else if(singleObject instanceof LanguageLevel)
						languageLevel = (LanguageLevel)singleObject;
				}
	
				UserLanguageBundle bundle = new UserLanguageBundle();
				bundle.setUserLanguage(userLanguage);
				bundle.setLanguage(language);
				bundle.setLanguageLevel(languageLevel);
				return bundle;
				
		} finally {
			session.close();
		}

	}

	public static void deleteUserLanguage(int userId, int languageId) {
		SessionFactory factory = DbHelper.getSessionFactory();
		Session session = factory.openSession();
		try {
			Transaction tx = session.beginTransaction();
			UserLanguage userLanguage = 
				(UserLanguage)session.createQuery("FROM UserLanguage where userId = :userId and languageId = :languageId")
				.setParameter("userId", userId)
				.setParameter("languageId", languageId)
				.uniqueResult();

			if(userLanguage==null) return;

			session.delete(userLanguage);
			tx.commit();
			session.flush();
		} finally {
			session.close();
		}
	}

	public static void updateUserLanguage(int userId, int languageId, boolean wantsToLearn, boolean isNative, int languageLevelId) {
		SessionFactory factory = DbHelper.getSessionFactory();
		Session session = factory.openSession();
		try {
			Transaction tx = session.beginTransaction();
			UserLanguage userLanguage = 
				(UserLanguage)session.createQuery("FROM UserLanguage where userId = :userId and languageId = :languageId")
				.setParameter("userId", userId)
				.setParameter("languageId", languageId)
				.uniqueResult();

			if(userLanguage==null) return;

			userLanguage.setIsNative(isNative);
			userLanguage.setLanguageLevelId(languageLevelId);
			session.save(userLanguage);
			tx.commit();
			session.flush();
		} finally {
			session.close();
		}
	}

	public static void createUserLanguage(int userId, int languageId, boolean wantsToLearn, boolean isNative, int languageLevelId) {
		SessionFactory factory = DbHelper.getSessionFactory();
		Session session = factory.openSession();
		try {
			Transaction tx = session.beginTransaction();
			UserLanguage userLanguage = new UserLanguage(null, userId, languageId, wantsToLearn, isNative, languageLevelId);
			session.save(userLanguage);
			tx.commit();
			session.flush();
		} finally {
			session.close();
		}
	}
	
	public static Integer createUser(String email, String passwordHash, String firstName, String lastName) {
		SessionFactory factory = DbHelper.getSessionFactory();
		Session session = factory.openSession();
		try {
			Transaction tx = session.beginTransaction();
			User user = new User(null, email, passwordHash, true, firstName, lastName);
			session.save(user);
			session.flush();
			UserProfile profile = new UserProfile(null, user.getId(), "", "");
			session.save(profile);
			tx.commit();
			session.flush();
			return user.getId();
		} finally {
			session.close();
		}
	}
	
	public static User getUser(int userId) {
		SessionFactory factory = DbHelper.getSessionFactory();
		Session session = factory.openSession();
		try {
			User user = (User)session.load(User.class, userId);
			user = GsonSerializer.unproxy(user);
			return user;
		} finally {
			session.close();
		}
	}


	public static void main(String[] args) {
		UserProfileBundle[] bundles =searchUsers(null, null, null);
		for(UserProfileBundle bundle : bundles) {
			System.out.println("User: " + bundle.getUser().getFirstName());
			System.out.println("About: " + bundle.getUserProfile().getAboutText());
		}
		DbHelper.getSessionFactory().close();
	}
}
