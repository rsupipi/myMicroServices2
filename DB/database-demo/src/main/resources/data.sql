create table user1(
    id integer not null ,
    birthday timestamp,
    name varchar(225) not null ,
    primary key (id)
);
insert into user1 values(1,sysdate(), 'pipi');
insert into user1 values(2,sysdate(), 'sama');
insert into user1 values(3,sysdate(), 'mala');
