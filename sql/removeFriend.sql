-- removeFriend(fromUserId, otherUserId)
DELETE FROM
	UserConnection
WHERE
	user1Id=@fromUserId AND
	user2Id=@otherUserId
DELETE FROM
	UserConnection
WHERE
	user1Id=@otherUserId AND
	user2Id=@fromUserId;
