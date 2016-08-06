-- -----------------------------------------------------
-- Login etc.
-- -----------------------------------------------------
-- login(email, passwordHash)
SELECT
	*
FROM
	User
WHERE
	email=@email AND
	passwordHash=@passwordHash;

-- createUser(email, passwordHash, firstName, lastName)
INSERT INTO
	User
	(email,passwordHash,firstName,lastName,lastActiveUTC)
VALUES
	(@email,@passwordHash,@firstName,@lastName,UTC_TIMESTAMP());
SELECT
	LAST_INSERT_ID();

-- getUser(email)
SELECT
	*
FROM
	User
WHERE
	email=@email;

-- updateUser(userId, email, passwordHash, firstName, lastName)
UPDATE
	User
SET
	email=@email,passwordHash=@passwordHash,firstName=@firstName,lastName=@lastName
WHERE
	id=@userId;

-- updateUserLastActive(userId)
UPDATE
	User
SET
	lastActiveUTC=UTC_TIMESTAMP()
WHERE
	id=@userId;


-- -----------------------------------------------------
-- Search screen
-- -----------------------------------------------------
-- searchUsers(name, languageInfoId, languageLevelId) sorts by lastActiveUTC
SELECT
	User.*
FROM
	User
	INNER JOIN
		UserLanguage ON
		User.id=UserLanguage.userId
	INNER JOIN
		LanguageInfo ON
		UserLanguage.languageInfoId=LanguageInfo.id
	INNER JOIN
		LanguageLevel ON
		UserLanguage.languageLevelId=LanguageLevel.id
WHERE
	(@languageInfoId IS NULL OR @languageInfoId=LanguageInfo.id) AND
	(@languageLevelId IS NULL OR @languageLevelId=LanguageLevel.id) AND
	(@name IS NULL OR CONCAT(User.firstName,' ',User.lastName) LIKE CONCAT('%',@name,'%'))
ORDER BY
	User.lastActiveUTC DESC
LIMIT
	50;

-- getLanguages()
SELECT
	*
FROM
	LanguageInfo;

-- getLanguageLevels()
SELECT
	*
FROM
	LanguageLevel;

-- -----------------------------------------------------
-- Profile screen
-- -----------------------------------------------------
-- getUserWithProfile(userId)
SELECT
	User.*,
	UserProfile.aboutText UserProfileAboutText,
	UserProfile.lookingForText UserProfileLookingForText
FROM
	User
	INNER JOIN
		UserProfile ON
		User.id=UserProfile.userId
WHERE
	id=@userId;

-- getUsersAreFriends(fromUserId, otherUserId)
SELECT
	*
FROM
	UserConnection
WHERE
	user1Id=@fromUserId AND
	user2Id=@otherUserId;


-- getUserLanguages(userId)
SELECT
	UserLanguage.*,
	LanguageInfo.name LanguageInfoName,
	LanguageLevel.name languageLevelName,
	LanguageLevel.description languageLevelDescription
FROM
	UserLanguage
	INNER JOIN LanguageInfo ON
		UserLanguage.languageInfoId=LanguageInfo.id
	INNER JOIN LanguageLevel ON
		UserLanguage.languageLevelId=LanguageLevel.id
WHERE
	UserLanguage.userId=@userId;


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

-- -----------------------------------------------------
-- Friends List screen
-- -----------------------------------------------------
-- getUserInviteProfiles(userId)
SELECT
	*
FROM
	UserConnection
WHERE
	user1Id=@userId AND
	confirmed=0 AND
	ignored=0;

-- getUserConnectionProfiles(userId)
SELECT
	*
FROM
	UserConnection
WHERE
	user1Id=@userId AND
	confirmed=1;

-- -----------------------------------------------------
-- Messages List screen
-- -----------------------------------------------------
-- getMessageFolderTypes()
SELECT
	*
FROM
	UserMessageFolder;

-- getMessagesForUser(owningUserId, userMessageFolderId)
SELECT
	*
FROM
	UserMessage
WHERE
	owningUserId=@owningUserId AND
	userMessageFolderId=@userMessageFolderId AND
	isDeleted=0;

-- -----------------------------------------------------
-- Message screen
-- -----------------------------------------------------
-- getMessage(messageId)
SELECT
	*
FROM
	UserMessage
WHERE
	id=@messageId;

-- updateMessageRead(messageId, isRead)
UPDATE
	UserMessage
SET
	isRead=@isRead
WHERE
	id=@messageId;

-- deleteMessage(messageId)
UPDATE
	UserMessage
SET
	isDeleted=1
WHERE
	id=@messageId;

-- -----------------------------------------------------
-- Create Message screen
-- -----------------------------------------------------
-- sendMessage(fromUserId, toUserId, subject, message)
INSERT INTO
	UserMessage
	(owningUserId,senderUserId,receiverUserId,dateSentUTC,subject,message,isDeleted,isRead,userMessageFolderId)
VALUES
	(@fromUserId,@fromUserId,@toUserId,UTC_TIMESTAMP(),@subject,@message,0,1,2);
INSERT INTO
	UserMessage (owningUserId,senderUserId,receiverUserId,dateSentUTC,subject,message,isDeleted,isRead,userMessageFolderId)
VALUES
	(@toUserId,@fromUserId,@toUserId,UTC_TIMESTAMP(),@subject,@message,0,0,1);

-- -----------------------------------------------------
-- Invite User screen
-- -----------------------------------------------------
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

-- -----------------------------------------------------
-- Accept Invitation screen
-- -----------------------------------------------------
-- updateInvite(fromUserId, toUserId, confirmed, ignored)
UPDATE
	UserConnection
SET
	confirmed=@confirmed,ignored=@ignored
WHERE
	user1Id=@fromUserId AND
	user2Id=@toUserId;
UPDATE
	UserConnection
SET
	confirmed=@confirmed,ignored=@ignored
WHERE
	user1Id=@toUserId AND
	user2Id=@fromUserId;

-- -----------------------------------------------------
-- Edit Profile screen
-- -----------------------------------------------------
-- updateProfile(userId, aboutText, lookingFor)
UPDATE
	UserProfile
SET
	aboutText=@aboutText,lookingFor=@lookingFor
WHERE
	id=@userId;

-- -----------------------------------------------------
-- Edit Languages screen
-- -----------------------------------------------------
-- getUserLanguages(userId)
-- See "Profile Screen"


-- -----------------------------------------------------
-- Edit Language screen
-- -----------------------------------------------------
-- getUserLanguage(userId, languageInfoId)
SELECT
	UserLanguage.*,
	LanguageInfo.name LanguageInfoName,
	LanguageLevel.name LanguageLevelName,
	LanguageLevel.description LanguageLevelDescription
FROM
	UserLanguage
	INNER JOIN
		LanguageLevel ON
		UserLanguage.languageLevelId=LanguageLevel.id
	INNER JOIN
		LanguageInfo ON
		UserLanguage.languageInfoId=LanguageInfo.id
WHERE
	userId=@userId AND
	languageInfoId=@languageInfoId;

-- deleteUserLanguage(userId, languageInfoId)
DELETE FROM
	UserLanguage
WHERE
	userId=@userId AND
	languageInfoId=@languageInfoId;

-- updateUserLanguage(userId, languageInfoId, wantsToLearn, isNative, languageLevelId)
UPDATE
	UserLanguage
SET
	wantsToLearn=@wantsToLearn,isNative=@isNative,languageLevelId=@languageLevelId
WHERE
	userId=@userId AND
	languageInfoId=@languageInfoId;

-- createUserLanguage(userId, languageInfoId, wantsToLearn, isNative, languageLevelId)
INSERT INTO
	UserLanguage
	(userId,languageInfoId,wantsToLearn,isNative,languageLevelId)
VALUES
	(@userId,@languageInfoId,@wantsToLearn,@isNative,@languageLevelId);

