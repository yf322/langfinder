-- login(email, passwordHash)
SELECT
	*
FROM
	User
WHERE
	email=@email AND
	passwordHash=@passwordHash;
