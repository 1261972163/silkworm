create table user
(
id bigint(19) primary key AUTO_INCREMENT,
version bigint(10),
createtime timestamp(6),
lastupdatetime timestamp(6),
name varchar(255) not null,
password varchar(255),
email varchar(255)
)AUTO_INCREMENT=100;
