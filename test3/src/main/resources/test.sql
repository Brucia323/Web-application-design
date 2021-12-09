create database test;
use test;

-- auto-generated definition
create table ware
(
    wareid    int auto_increment
        primary key,
    warename  varchar(255) charset utf8 not null,
    wareprice int                       not null,
    waretype  varchar(255) charset utf8 not null,
    constraint ware_wareid_uindex
        unique (wareid)
);
-- auto-generated definition
create table shop
(
    subid    int                       not null
        primary key,
    warename varchar(255) charset utf8 not null,
    wsum     int                       not null,
    constraint shop_subid_uindex
        unique (subid)
);
