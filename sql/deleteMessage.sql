-- deleteMessage(messageId)
UPDATE
	UserMessage
SET
	isDeleted=1
WHERE
	id=@messageId;
