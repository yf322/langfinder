package cs275.langfinder.data;

import java.util.Date;

public class UserMessage {
	Integer id;
	Integer owningUserId;
	Integer senderUserId;
	Integer receiverUserId;
	Date dateSentUTC;
	String subject;
	String message;
	boolean isDeleted;
	boolean isRead;
	Integer userMessageFolderId;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getOwningUserId() {
		return owningUserId;
	}
	public void setOwningUserId(Integer owningUserId) {
		this.owningUserId = owningUserId;
	}
	public Integer getSenderUserId() {
		return senderUserId;
	}
	public void setSenderUserId(Integer senderUserId) {
		this.senderUserId = senderUserId;
	}
	public Integer getReceiverUserId() {
		return receiverUserId;
	}
	public void setReceiverUserId(Integer receiverUserId) {
		this.receiverUserId = receiverUserId;
	}
	public Date getDateSentUTC() {
		return dateSentUTC;
	}
	public void setDateSentUTC(Date dateSentUTC) {
		this.dateSentUTC = dateSentUTC;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public boolean getIsDeleted() {
		return isDeleted;
	}
	public void setIsDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
	public boolean getIsRead() {
		return isRead;
	}
	public void setIsRead(boolean isRead) {
		this.isRead = isRead;
	}
	public Integer getUserMessageFolderId() {
		return userMessageFolderId;
	}
	public void setUserMessageFolderId(Integer userMessageFolderId) {
		this.userMessageFolderId = userMessageFolderId;
	}
	public UserMessage() {
		super();
	}
	public UserMessage(Integer id, Integer owningUserId, Integer senderUserId,
			Integer receiverUserId, Date dateSentUTC, String subject,
			String message, boolean isDeleted, boolean isRead,
			Integer userMessageFolderId) {
		super();
		this.id = id;
		this.owningUserId = owningUserId;
		this.senderUserId = senderUserId;
		this.receiverUserId = receiverUserId;
		this.dateSentUTC = dateSentUTC;
		this.subject = subject;
		this.message = message;
		this.isDeleted = isDeleted;
		this.isRead = isRead;
		this.userMessageFolderId = userMessageFolderId;
	}

}
