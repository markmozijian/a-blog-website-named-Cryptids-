Format guide
===
Bold = primary keys (not null implicit)

italics = unique

bold + italics = foreign key


Users
===
users(__**ID**__,*username*,joined,realName,dateOfBirth,country,bio,password);

ID : int

username : nvarchar(64)

joined, dateOfBirth : date

realName,country : nvarchar(64)

bio : longtext

avatar : nvarchar(512)

password : binary(128) not null

salt : binary(16) not null

iterations : int not null


Articles
===
articles(__**ID**__,title,content,updated,__*author*__)

ID : int

title : nvarchar(1024)

content : longtext

updated : timestamp

author : int references user (id)

mainPicture: nvarchar

mainMedia:nvarchar

Comments
===
comments(__**ID**__,content,updated,__*author*__,__*article*__,__*parent*__)

ID : int

content : longtext

updated : timestamp

author : int references users(id) // allow null

article : int references articles(id) // not null

parent : int references comments(id) // allow null

Token
===
comments(__**user**__,code,created)


user : primary key int

code : int unique, reason not primary key is creating one is an exception.
We always clear any tokens if the user finally logs in.

created : timestamp, to allow for token expiry