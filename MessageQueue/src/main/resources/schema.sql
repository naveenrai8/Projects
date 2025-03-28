create database if not exists db;

create table if not exists messageQueue1
(
	id varchar(255) not null,
    content varchar(255),
    clientId varchar(255),
    primary key (id)
)

insert into messageQueue(id, content) values
(
	"1212",'First content'
)