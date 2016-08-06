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
