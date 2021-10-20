-- auto-generated definition
create table reply
(
    id      int auto_increment,
    topicid int                                  not null,
    likes   int        default 0                 not null,
    reply   int        default 0                 not null,
    sticky  tinyint(1) default 0                 not null,
    userid  int                                  not null,
    time    datetime   default CURRENT_TIMESTAMP null,
    replyid int        default 0                 not null,
    constraint reply_id_uindex
        unique (id)
);

alter table reply
    add primary key (id);

