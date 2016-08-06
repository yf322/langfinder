-- updateUserLanguage(userId, languageInfoId, wantsToLearn, isNative, languageLevelId)
UPDATE
	UserLanguage
SET
	wantsToLearn=@wantsToLearn,isNative=@isNative,languageLevelId=@languageLevelId
WHERE
	userId=@userId AND
	languageInfoId=@languageInfoId;
