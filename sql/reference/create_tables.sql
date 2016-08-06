

-- -----------------------------------------------------
-- Table User
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS User (
  id INT UNSIGNED NOT NULL AUTO_INCREMENT,
  email VARCHAR(255) NOT NULL,
  passwordHash VARCHAR(256) NOT NULL,
  firstName VARCHAR(256) NOT NULL,
  lastName VARCHAR(256) NOT NULL,
  lastActiveUTC DATETIME NULL,
  PRIMARY KEY (id),
  UNIQUE INDEX email_UNIQUE (email ASC)
)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table UserProfile
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS UserProfile (
  id INT UNSIGNED NOT NULL AUTO_INCREMENT,
  userId INT UNSIGNED NOT NULL,
  aboutText VARCHAR(512) NULL,
  lookingForText VARCHAR(512) NULL,
  PRIMARY KEY (id),
  UNIQUE INDEX userId_UNIQUE (userId ASC),
  CONSTRAINT fk_UserProfile_User
    FOREIGN KEY (userId)
    REFERENCES User (id)
)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table Language
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS Language (
  id INT UNSIGNED NOT NULL AUTO_INCREMENT,
  name VARCHAR(255) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE INDEX name_UNIQUE (name ASC)
)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table LanguageLevel
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS LanguageLevel (
  id INT UNSIGNED NOT NULL AUTO_INCREMENT,
  name VARCHAR(255) NOT NULL,
  description VARCHAR(512) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE INDEX name_UNIQUE (name ASC)
)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table UserLanguage
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS UserLanguage (
  id INT UNSIGNED NOT NULL AUTO_INCREMENT,
  userId INT UNSIGNED NOT NULL,
  languageId INT UNSIGNED NOT NULL,
  wantsToLearn BIT(1) NULL,
  isNative BIT(1) NULL,
  languageLevelId INT UNSIGNED NOT NULL,
  PRIMARY KEY (id),
  UNIQUE INDEX userId_languageId_UNIQUE (userId ASC, languageId ASC),
  CONSTRAINT fk_UserLanguage_User
    FOREIGN KEY (userId)
    REFERENCES User (id),
  CONSTRAINT fk_UserLanguage_Language
    FOREIGN KEY (languageId)
    REFERENCES Language (id),
  CONSTRAINT fk_UserLanguage_LanguageLevel
    FOREIGN KEY (languageLevelId)
    REFERENCES LanguageLevel (id)
)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table UserConnection
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS UserConnection (
  id INT UNSIGNED NOT NULL AUTO_INCREMENT,
  user1Id INT UNSIGNED NOT NULL,
  user2Id INT UNSIGNED NOT NULL,
  inviteMessage varchar(255) NULL,
  confirmed BIT(1) NULL,
  ignored BIT(1) NULL,
  complementUserConnectionId INT UNSIGNED NULL,
  PRIMARY KEY (id),
  UNIQUE INDEX user1Id_user2Id_UNIQUE (user1Id ASC, user2Id ASC),
  UNIQUE INDEX complementUserConnectionId_UNIQUE (complementUserConnectionId ASC),
  CONSTRAINT fk_UserConnection_User_1
    FOREIGN KEY (user1Id)
    REFERENCES User (id),
  CONSTRAINT fk_UserConnection_User_2
    FOREIGN KEY (user2Id)
    REFERENCES User (id),
  CONSTRAINT fk_UserConnection_UserConnection
    FOREIGN KEY (complementUserConnectionId)
    REFERENCES UserConnection (id)
)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table UserMessageFolder
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS UserMessageFolder (
  id INT UNSIGNED NOT NULL AUTO_INCREMENT,
  name VARCHAR(256) NOT NULL,
  PRIMARY KEY (id)
)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table UserMessage
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS UserMessage (
  id INT UNSIGNED NOT NULL AUTO_INCREMENT,
  userMessageFolderId INT UNSIGNED NOT NULL,
  owningUserId INT UNSIGNED NOT NULL,
  senderUserId INT UNSIGNED NOT NULL,
  receiverUserId INT UNSIGNED NOT NULL,
  dateSentUTC DATETIME NOT NULL,
  subject VARCHAR(256) NOT NULL,
  message VARCHAR(512) NOT NULL,
  isDeleted BIT(1) NULL,
  isRead BIT(1) NULL,
  PRIMARY KEY (id),
    CONSTRAINT fk_UserMessage_User_1
    FOREIGN KEY (owningUserId)
    REFERENCES User (id),
  CONSTRAINT fk_UserMessage_User_2
    FOREIGN KEY (senderUserId)
    REFERENCES User (id),
  CONSTRAINT fk_UserMessage_User_3
    FOREIGN KEY (receiverUserId)
    REFERENCES User (id),
  CONSTRAINT fk_UserMessage_UserMessageFolder
    FOREIGN KEY (userMessageFolderId)
    REFERENCES UserMessageFolder (id)
)
ENGINE = InnoDB;

