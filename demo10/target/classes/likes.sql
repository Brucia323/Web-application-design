-- auto-generated definition
create table likes
(
    userid  int           not null,
    topicid int           not null,
    replyid int default 0 not null
);

