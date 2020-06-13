CREATE TABLE users (
  id INT PRIMARY KEY AUTO_INCREMENT,
  username NVARCHAR(64) UNIQUE NOT NULL,
  joined DATE NOT NULL,
  dateOfBirth DATE,
  realName NVARCHAR(64),
  country NVARCHAR(64),
  bio LONGTEXT,
  avatar NVARCHAR(128),
  email NVARCHAR(255),
  password BINARY(128) NOT NULL,
  salt BINARY(16) NOT NULL
);
CREATE TABLE articles (
  id INT PRIMARY KEY AUTO_INCREMENT,
  title NVARCHAR(1024),
  content LONGTEXT,
  author INT,
  updated TIMESTAMP,
  mainPicture NVARCHAR(128),
  mainMedia NVARCHAR(128),
  redacted BOOL NOT NULL DEFAULT 0,
  FOREIGN KEY (author) REFERENCES users(id) ON DELETE SET NULL
);
CREATE TABLE comments (
  id INT PRIMARY KEY AUTO_INCREMENT,
  content LONGTEXT,
  updated TIMESTAMP,
  author INT,
  article INT,
  parent INT,
  redacted BOOL NOT NULL DEFAULT 0,
  FOREIGN KEY (author) REFERENCES users(id) ON DELETE SET NULL ,
  FOREIGN KEY (article) REFERENCES articles(id) ON DELETE CASCADE,
  FOREIGN KEY (parent) REFERENCES comments(id) ON DELETE SET NULL
);
CREATE TABLE tokens (
  user INT PRIMARY KEY ,
  code INT UNIQUE NOT NULL ,
  created TIMESTAMP,
  FOREIGN KEY (user) REFERENCES users(id) ON DELETE CASCADE
);