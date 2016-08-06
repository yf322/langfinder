-- createUserLanguage(userId, languageInfoId, wantsToLearn, isNative, languageLevelId)
INSERT INTO
	UserLanguage
	(userId,languageInfoId,wantsToLearn,isNative,languageLevelId)
VALUES
	(@userId,@languageInfoId,@wantsToLearn,@isNative,@languageLevelId);

