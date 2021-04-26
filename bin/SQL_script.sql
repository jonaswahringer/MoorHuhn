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

-- create table serialized(
--     username varchar(50) not null,
--     check_savestand boolean,
--     score integer not null,
--     lvl integer not null,
--     lives_available integer not null,
--     primary key(username),
--     constraint fk_uname foreign key(username) 
--     references user(username) on delete cascade on update cascade
-- );

select * from user;