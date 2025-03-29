create database if not exists db;

create table if not exists messageQueue
(
	id varchar(255) not null,
    content varchar(255),
    clientId varchar(255),
    leaseTill long,
    primary key (id)
)

insert into messageQueue(id, content) values
(
	"1212",'First content'
)