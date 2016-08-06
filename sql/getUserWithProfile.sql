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
