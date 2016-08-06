-- getUsersAreFriends(fromUserId, otherUserId)
SELECT
	*
FROM
	UserConnection
WHERE
	user1Id=@fromUserId AND
	user2Id=@otherUserId;
