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
