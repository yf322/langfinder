-- inviteUser(fromUserId, toUserId, inviteMessage)
DECLARE
	@firstUserConnectionId int;
DECLARE
	@secondUserConnectionId int;
INSERT INTO
	UserConnection
	(user1Id,user2Id,inviteMessage,confirmed,ignored)
VALUES
	(@fromUserId,@toUserId,@inviteMessage,0,0);
SELECT
	@firstUserConnectionId=LAST_INSERT_ID();
INSERT INTO
	UserConnection
	(user1Id,user2Id,inviteMessage,confirmed,ignored,complementUserConnectionId)
VALUES
	(@toUserId,@fromUserId,@inviteMessage,0,0,@firstUserConnectionId);
SELECT
	@secondUserConnectionId=LAST_INSERT_ID();
UPDATE
	UserConnection
SET
	complementUserConnectionId=@secondUserConnectionId
WHERE
	id=@firstUserConnectionId;
