package cs275.langfinder.svcclient;

import cs275.langfinder.data.UserMessage;
import cs275.langfinder.data.UserMessageFolder;
import cs275.langfinder.net.LangfinderServiceCaller;

public class MessageController extends ServiceController {

	public UserMessageFolder[] getMessageFolderTypes() {
		LangfinderServiceCaller<UserMessageFolder[]> service = new LangfinderServiceCaller<UserMessageFolder[]>(UserMessageFolder[].class, serviceRoot, "getMessageFolderTypes.jsp");
		return service.executeGet();
	}

	public UserMessage[] getMessagesForUser(int owningUserId, int userMessageFolderId) {
		LangfinderServiceCaller<UserMessage[]> service = new LangfinderServiceCaller<UserMessage[]>(UserMessage[].class, serviceRoot, "getMessagesForUser.jsp");
		service.addParameter("owningUserId", owningUserId);
		service.addParameter("userMessageFolderId", userMessageFolderId);
		return service.executeGet();
	}

	public UserMessage getMessage(int messageId) {
		LangfinderServiceCaller<UserMessage> service = new LangfinderServiceCaller<UserMessage>(UserMessage.class, serviceRoot, "getMessage.jsp");
		service.addParameter("messageId", messageId);
		return service.executeGet();
	}

	public void updateMessageRead(int messageId, boolean isRead) {
		LangfinderServiceCaller<Object> service = new LangfinderServiceCaller<Object>(Object.class, serviceRoot, "updateMessageRead.jsp");
		service.addParameter("messageId", messageId);
		service.addParameter("isRead", isRead);
		service.executeGet();
	}

	public void deleteMessage(int messageId) {
		LangfinderServiceCaller<Object> service = new LangfinderServiceCaller<Object>(Object.class, serviceRoot, "deleteMessage.jsp");
		service.addParameter("messageId", messageId);
		service.executeGet();
	}

	public void sendMessage(int fromUserId, int toUserId, String subject, String message) {
		LangfinderServiceCaller<Object> service = new LangfinderServiceCaller<Object>(Object.class, serviceRoot, "sendMessage.jsp");
		service.addParameter("fromUserId", fromUserId);
		service.addParameter("toUserId", toUserId);
		service.addParameter("subject", subject);
		service.addParameter("message", message);
		service.executeGet();
	}

	public MessageController(String serviceRoot) {
		super(serviceRoot);
	}

}
