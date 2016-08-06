-- inviteUser(fromUserId, toUserId, inviteMessage)
INSERT INTO
	UserConnection
	(user1Id,user2Id,inviteMessage,confirmed,ignored)
VALUES
	(@fromUserId,@toUserId,@inviteMessage,0,0);
INSERT INTO
	UserConnection
	(user1Id,user2Id,inviteMessage,confirmed,ignored,complementUserConnectionId)
VALUES
	(@toUserId,@fromUserId,@inviteMessage,0,0,LAST_INSERT_ID());
UPDATE
	UserConnection
SET
	complementUserConnectionId=LAST_INSERT_ID()
WHERE
	id=@firstUserConnectionId;
