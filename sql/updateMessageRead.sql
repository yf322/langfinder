-- updateMessageRead(messageId, isRead)
UPDATE
	UserMessage
SET
	isRead=@isRead
WHERE
	id=@messageId;
