create database  if not exists mybatis; -- 字符集与排序规则按版本需要再改 本机测试使用8.0.2版本,默认为utf8
drop table  country;
drop table sys_user;
create table if not exists country
(
    id           bigint auto_increment
        primary key,
    country_name varchar(255) null,
    country_code varchar(255) null
);

create table if not exists sys_user
(
    id          bigint auto_increment
        primary key,
    name        varchar(50)                         null,
    password    varchar(255)                        null,
    email       varchar(50)                         null,
    info        text                                null,
    head_img    blob                                null,
    create_time timestamp default CURRENT_TIMESTAMP null
);
set autocommit=false;
INSERT ignore INTO mybatis.country (id, country_name, country_code)
VALUES (1, '中国', 'CN');
INSERT ignore INTO mybatis.country (id, country_name, country_code)
VALUES (2, '美国', 'US');
INSERT ignore INTO mybatis.country (id, country_name, country_code)
VALUES (5, '法国', 'FR');
INSERT ignore INTO mybatis.country (id, country_name, country_code)
VALUES (6, '英国', 'GB');
commit ;
set autocommit=true;
