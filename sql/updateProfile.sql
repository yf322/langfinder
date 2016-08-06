-- updateProfile(userId, aboutText, lookingFor)
UPDATE
	UserProfile
SET
	aboutText=@aboutText,lookingFor=@lookingFor
WHERE
	id=@userId;
