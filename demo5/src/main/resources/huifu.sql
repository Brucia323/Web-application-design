create table huifu
(
    id      int,
    huatiid int           not null,
    zan     int default 0 not null,
    huifu   int default 0 not null,
    top     int default 0 not null,
    userid  int           not null,
    time    datetime      not null
);

create unique index huifu_id_uindex
    on huifu (id);

alter table huifu
    add constraint huifu_pk
        primary key (id);

alter table huifu
    modify id int auto_increment;

