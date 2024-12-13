create table message
(
    id bigint not null,
    content varchar(1024) not null,
    consumer_id varchar(255)
);

insert into message (id, content, consumer_id)
values (1, 'test message', '1'),
(2, 'test message 2', null);