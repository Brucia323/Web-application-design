create table user
(
    id int,
    name nvarchar(40) not null,
    password varchar(20) not null
);

create unique index user_id_uindex
    on user (id);

create unique index user_name_uindex
    on user (name);

alter table user
    add constraint user_pk
        primary key (id);

alter table user
    modify id int auto_increment;

