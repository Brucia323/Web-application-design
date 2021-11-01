CREATE DATABASE students;
create table stu_info
(
    id     int auto_increment,
    name   varchar(20) charset utf8 null,
    sex    varchar(10) charset utf8 null,
    age    int                      null,
    weight double                   null,
    hight  double                   null,
    constraint stu_info_id_uindex
        unique (id)
);

alter table stu_info
    add primary key (id);
