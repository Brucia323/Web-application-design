create table ShangPin
(
    id    int,
    name  nvarchar(40) not null,
    price int          not null
);

create unique index ShangPin_id_uindex
    on ShangPin (id);

alter table ShangPin
    add constraint ShangPin_pk
        primary key (id);

alter table ShangPin
    modify id int auto_increment;
