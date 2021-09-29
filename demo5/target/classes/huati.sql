create table huati
(
    id int,
    title nvarchar(255) not null,
    zan int default 0 not null,
    huifu int default 0 not null,
    top int default 0 not null,
    jing int default 0 not null,
    userid int not null,
    time datetime not null
);

create unique index huati_id_uindex
    on huati (id);

alter table huati
    add constraint huati_pk
        primary key (id);

alter table huati modify id int auto_increment;

