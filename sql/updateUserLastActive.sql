-- updateUserLastActive(userId)
UPDATE
	User
SET
	lastActiveUTC=UTC_TIMESTAMP()
WHERE
	id=@userId;
