drop database if exists moorhuhn;
create database moorhuhn default character set=utf8 default collate utf8_general_ci;

use moorhuhn;
show tables;

create table user(
    username varchar(50) not null,
    userpassword varchar(50) not null,
    highscore integer default 0,
    primary key (username)
);

insert into user values('thedestroyer','123', 10);
insert into user values('thedestroyer2','123', 11);
insert into user values('thedestroyer3','123', 12);

select * from user;