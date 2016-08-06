insert into Language (Name) VALUES
	('English'),
	('French'),
	('Russian'),
	('German');


insert into LanguageLevel (name, description) VALUES
('Beginner', 'Beginner In the Language'),
('Intermediate', 'Intermediate In the Language'),
('Advanced', 'Advanced In the Language'),
('Fluent', 'Fluent In the Language');


insert into User (email, passwordHash, firstName, lastName) VALUES
('jjc43@drexl.edu', 'password','Jason', 'Cotton'),
('yf322@drexel.edu', 'password','Yongqiang', 'Fan'),
('bdm57@drexel.edu', 'password','Brandon', 'McKay');


update User set email='jjc43@drexel.edu' where email='jjc43@drexl.edu';


insert into UserProfile (
	userId,
	aboutText,
	lookingForText
)
select
	id,
	'Temporary About Text',
	'Temporary Looking For Text'
from
	User;

insert into UserMessageFolder (id, name) values
(1, 'Inbox'),
(2, 'Sent');