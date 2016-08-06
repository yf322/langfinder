-- deleteUserLanguage(userId, languageInfoId)
DELETE FROM
	UserLanguage
WHERE
	userId=@userId AND
	languageInfoId=@languageInfoId;
