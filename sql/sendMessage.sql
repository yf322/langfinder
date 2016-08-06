-- sendMessage(fromUserId, toUserId, subject, message)
INSERT INTO
	UserMessage
	(owningUserId,senderUserId,receiverUserId,dateSentUTC,subject,message,isDeleted,isRead,userMessageFolderId)
VALUES
	(@fromUserId,@fromUserId,@toUserId,UTC_TIMESTAMP(),@subject,@message,0,1,2);
INSERT INTO
	UserMessage (owningUserId,senderUserId,receiverUserId,dateSentUTC,subject,message,isDeleted,isRead,userMessageFolderId)
VALUES
	(@toUserId,@fromUserId,@toUserId,UTC_TIMESTAMP(),@subject,@message,0,0,1);
