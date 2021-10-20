-- auto-generated definition
create table user
(
    id            int auto_increment,
    username      varchar(40) charset utf8 not null,
    password      varchar(20)              not null,
    administrator tinyint(1) default 0     not null,
    constraint user_id_uindex
        unique (id),
    constraint user_username_uindex
        unique (username)
);

alter table user
    add primary key (id);

