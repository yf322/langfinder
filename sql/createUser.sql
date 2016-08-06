-- createUser(email, passwordHash, firstName, lastName)
INSERT INTO
	User
	(email,passwordHash,firstName,lastName,lastActiveUTC)
VALUES
	(@email,@passwordHash,@firstName,@lastName,UTC_TIMESTAMP());
SELECT
	LAST_INSERT_ID();
