CREATE TABLE like_status (
  id int primary key auto_increment ,
  articleID int,
  userID int,
  status int,
  time timestamp
)