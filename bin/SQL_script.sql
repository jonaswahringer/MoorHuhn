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

create table serialized(
    username varchar(50) not null,
    lives_available integer not null,
    score integer not null,
    current_ammo integer not null,
    lvl integer not null,
    -- moorhuhn_array blob(100),
    -- hitbox_array blob(100),
    primary key(username),
    constraint fk_uname foreign key(username) 
    references user(username) on delete cascade on update cascade
);

select * from user;