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
