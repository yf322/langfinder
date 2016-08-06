-- getUserInviteProfiles(userId)
SELECT
	*
FROM
	UserConnection
WHERE
	user1Id=@userId AND
	confirmed=0 AND
	ignored=0;
