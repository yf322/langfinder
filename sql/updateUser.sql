-- updateUser(userId, email, passwordHash, firstName, lastName)
UPDATE
	User
SET
	email=@email,passwordHash=@passwordHash,firstName=@firstName,lastName=@lastName
WHERE
	id=@userId;
