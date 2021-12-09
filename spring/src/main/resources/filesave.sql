create database filesave;
-- auto-generated definition
create table user
(
    user_id  int auto_increment comment '用户id'
        primary key,
    username varchar(255) charset utf8 null comment '用户名',
    password varchar(255)              null comment '密码'
)
    comment '用户';

-- auto-generated definition
create table file
(
    file_id   int auto_increment comment '文件id'
        primary key,
    filename  varchar(255) charset utf8 null comment '文件名',
    user_id   int                       null comment '文件所属的用户的id',
    authority varchar(255) charset utf8 null comment '权限：公开、私密'
)
    comment '文件';
