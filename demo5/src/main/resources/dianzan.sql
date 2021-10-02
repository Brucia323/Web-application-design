create table dianzan
(
    userid  int not null,
    huatiid int not null,
    huifuid int not null
);

alter table dianzan
    alter column huifuid set default 0;

