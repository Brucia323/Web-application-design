create table users
(
    id       int,
    name     nvarchar(40) not null,
    password varchar(20)  not null
);

create unique index users_id_uindex
    on users (id);

create unique index users_name_uindex
    on users (name);

alter table users
    add constraint users_pk
        primary key (id);

alter table users
    modify id int auto_increment;
