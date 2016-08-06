-- getMessagesForUser(owningUserId, userMessageFolderId)
SELECT
	*
FROM
	UserMessage
WHERE
	owningUserId=@owningUserId AND
	userMessageFolderId=@userMessageFolderId AND
	isDeleted=0;
