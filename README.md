# dynamictask

#### 介绍
基于springboot轻量级任务调度系统

#### 软件架构
springboot
mybatis-plus
hutool-all
druid


#### 安装教程

第一步：数据准备，创建表：task_config和task_logs

create table TASK_CONFIG ( id NUMBER not null, name VARCHAR2(200), remark VARCHAR2(500), bean_name VARCHAR2(100), cron VARCHAR2(100), status NUMBER, create_time TIMESTAMP(6), update_time TIMESTAMP(6) )

create unique index INDEX_WYSY_2019 on TASK_CONFIG (BEAN_NAME)

alter table TASK_CONFIG add constraint PK_TASK_CONFIG primary key (ID)

create sequence SEQ_TASK_CONFIG minvalue 1 maxvalue 999999999999999999999999999 start with 1 increment by 1 cache 20;

create table TASK_LOGS ( id NUMBER not null, task_id NUMBER, task_name VARCHAR2(200), task_bean VARCHAR2(200), task_cron VARCHAR2(200), log_content VARCHAR2(2000), create_date TIMESTAMP(6) )

create index INDEX_TASK_LOGS on TASK_LOGS (TASK_NAME, CREATE_DATE)

alter table TASK_LOGS add constraint PK_TASK_LOGS primary key (ID)

create sequence SEQ_TASK_LOGS minvalue 1 maxvalue 999999999999999999999999999 start with 1 increment by 1 cache 20;

第二步：实现接口，完成业务代码

请实现 接口 com.power.taskcenter.tasks.DyTask 类

例如：com.power.taskcenter.tasks.custom.TestOneDyTask implements DyTask

参数说明： name 任务名称, remark 备注, cron 计划时间，例如： "0/2 * * * * ?", status = 1 1表示禁止 0 表示启动

cron编写请参考

第三步：配置登录系统用户名和密码

application文件中spring.dynamictask.login-username:登录系统用户名。

application文件中spring.dynamictask.login-password:登录系统密码MD5加密后字符串。

 
 
