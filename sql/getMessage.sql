-- getMessage(messageId)
SELECT
	*
FROM
	UserMessage
WHERE
	id=@messageId;
