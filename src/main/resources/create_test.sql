-- Create table
create table TEST_USER
(
  USER_ID       number(10) primary key,
  USER_NAME     nvarchar2(20) not null,
  USER_PASSWORD nvarchar2(16) not null
)
;
-- Add comments to the table 
comment on table TEST_USER
  is '测试用户表';
-- Add comments to the columns 
comment on column TEST_USER.USER_ID
  is '用户ID';
comment on column TEST_USER.USER_NAME
  is '用户名';
comment on column TEST_USER.USER_PASSWORD
  is '用户密码';
  
-- Create sequence 
create sequence TEST_USER_SEQUENCE
minvalue 1000
maxvalue 9999999999
start with 1000
increment by 1
cache 20;
