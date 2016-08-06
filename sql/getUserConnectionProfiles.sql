-- getUserConnectionProfiles(userId)
SELECT
	*
FROM
	UserConnection
WHERE
	user1Id=@userId AND
	confirmed=1;
