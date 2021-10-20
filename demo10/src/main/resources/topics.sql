-- auto-generated definition
create table topics
(
    id      int auto_increment,
    userid  int                                  not null,
    time    datetime   default CURRENT_TIMESTAMP not null,
    likes   int        default 0                 not null,
    reply   int        default 0                 not null,
    Sticky  tinyint(1) default 0                 not null,
    essence tinyint(1) default 0                 not null,
    constraint topics_id_uindex
        unique (id)
);

alter table topics
    add primary key (id);

