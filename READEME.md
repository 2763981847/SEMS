### （1）设计阶段：

- 需求分析：

  由于真实的企业员工管理系统**十分复杂**，而我们只是设计一个**简单、小型**的员工管理系统，所以这里我们只选取了**“员工打卡考勤”**、**“员工人事调动”**和**“发放员工工资”**这三个常见的行为来对其进行实现。

    1. 员工信息管理模块：该模块用于管理员工的基本信息，包括员工ID、所属部门、姓名、工号、性别、年龄、职称、身份证号码、联系电话、入职日期等。员工信息表中存储了所有员工的基本信息，员工可根据自身需要查看或修改自己的信息，管理员可根据需要进行员工信息的增删改查操作。
    2. 部门信息管理模块：该模块用于管理公司的各个部门信息，包括部门ID、部门名称、部门简介、成立日期等。部门信息表中存储了所有部门的信息，管理员可根据需要进行部门信息的增删改查操作。
    3. 员工考勤管理模块：该模块用于记录员工的考勤信息，包括考勤ID、员工ID、考勤日期、签到时间、签退时间等。考勤表中存储了所有员工的考勤信息，员工可查看自己的考勤记录，也可今日每日的签到和签退，管理员可根据需要进行考勤信息的增删改查操作。
    4. 员工调动记录管理模块：该模块用于记录员工的调动记录，包括调动ID、员工ID、原部门ID、新部门ID、调动原因、原职称、新职称、调动日期等。员工调动记录表中存储了所有员工的调动记录信息，管理员可根据需要进行调动记录信息的增删改查操作。
    5. 员工工资管理模块：该模块用于管理员工的工资信息，包括工资ID、员工ID、基本工资、奖金、罚款、发薪日期等。工资信息表中存储了所有员工的工资信息，员工可查看自己的工资记录，管理员可根据需要进行工资信息的增删改查操作。

  以上模块中，除了员工信息管理模块和部门信息管理模块外，其余模块均需要使用员工ID进行关联。同时，员工信息表、部门信息表、考勤表、员工调动记录表、工资信息表之间均存在关联关系，需要使用外键进行关联。

- E-R模型设计：

  根据上面的需求分析，我们可以得到如下的E-R模型：

  ![屏幕截图 2023-04-20 101339](https://qn.autumnclouds.cn/wp-content/uploads/202304201439549.png)

- 关系模型设计：

  使用powerdesigner的`Generate Physical Data Model`功能我们可以得到关系模型如下：

  ![屏幕截图 2023-04-20 101700](https://qn.autumnclouds.cn/wp-content/uploads/202304201439108.png)

- 系统功能模块图：

  根据业务需求，我们可以得出系统功能模块图如下：

  ![未命名绘图.drawio](https://qn.autumnclouds.cn/wp-content/uploads/202304201439761.png)

### （2）实现阶段

项目源码：https://github.com/2763981847/SEMS

1. 首先我们根据上面生成的物理模型，通过powerdesigner的`Database Gerneration`功能可以直接得出建表sql如下：

   ```sql
   /*==============================================================*/
   /* DBMS name:      PostgreSQL 9.x                               */
   /* Created on:     2023/4/20 10:21:08                           */
   /*==============================================================*/
   
   
   drop table attendance;
   
   drop table department;
   
   drop table employee;
   
   drop table employee_transfer;
   
   drop table salary;
   
   /*==============================================================*/
   /* Table: attendance                                            */
   /*==============================================================*/
   create table attendance (
      attendance_id        INT8                 not null,
      employee_id          INT8                 null,
      date                 DATE                 null,
      sign_in_time         TIME                 null,
      sign_out_time        TIME                 null,
      constraint PK_ATTENDANCE primary key (attendance_id)
   );
   
   comment on table attendance is
   '员工考勤表';
   
   /*==============================================================*/
   /* Index: attendance_PK                                         */
   /*==============================================================*/
   create unique index attendance_PK on attendance (
   attendance_id
   );
   
   /*==============================================================*/
   /* Index: Attendance_FK                                         */
   /*==============================================================*/
   create  index Attendance_FK on attendance (
   employee_id
   );
   
   /*==============================================================*/
   /* Table: department                                            */
   /*==============================================================*/
   create table department (
      department_id        INT8                 not null,
      dep_name             VARCHAR(50)          null,
      introduction         VARCHAR(400)         null,
      establish_date       DATE                 null,
      constraint PK_DEPARTMENT primary key (department_id)
   );
   
   comment on table department is
   '部门信息表';
   
   /*==============================================================*/
   /* Index: department_PK                                         */
   /*==============================================================*/
   create unique index department_PK on department (
   department_id
   );
   
   /*==============================================================*/
   /* Table: employee                                              */
   /*==============================================================*/
   create table employee (
      employee_id          INT8                 not null,
      department_id        INT8                 null,
      name                 VARCHAR(50)          null,
      empno                INT8                 null,
      sex                  INT2                 null,
      age                  INT4                 null,
      job_title            VARCHAR(20)          null,
      id_number            VARCHAR(30)          null,
      phone_number         VARCHAR(30)          null,
      enrollment_date      DATE                 null,
      constraint PK_EMPLOYEE primary key (employee_id)
   );
   
   comment on table employee is
   '员工信息表';
   
   /*==============================================================*/
   /* Index: employee_PK                                           */
   /*==============================================================*/
   create unique index employee_PK on employee (
   employee_id
   );
   
   /*==============================================================*/
   /* Index: belong_FK                                             */
   /*==============================================================*/
   create  index belong_FK on employee (
   department_id
   );
   
   /*==============================================================*/
   /* Table: employee_transfer                                     */
   /*==============================================================*/
   create table employee_transfer (
      transfer_id          INT8                 not null,
      employee_id          INT8                 null,
      out_department_id    INT8                 null,
      in_department_id     INT8                 null,
      reason               VARCHAR(400)         null,
      original_job_title   VARCHAR(20)          null,
      new_job_title        VARCHAR(20)          null,
      transfer_date        DATE                 null,
      constraint PK_EMPLOYEE_TRANSFER primary key (transfer_id)
   );
   
   comment on table employee_transfer is
   '员工调动记录表';
   
   /*==============================================================*/
   /* Index: employee_transfer_PK                                  */
   /*==============================================================*/
   create unique index employee_transfer_PK on employee_transfer (
   transfer_id
   );
   
   /*==============================================================*/
   /* Index: transfer_FK                                           */
   /*==============================================================*/
   create  index transfer_FK on employee_transfer (
   employee_id
   );
   
   /*==============================================================*/
   /* Index: transfer_in_FK                                        */
   /*==============================================================*/
   create  index transfer_in_FK on employee_transfer (
   out_department_id
   );
   
   /*==============================================================*/
   /* Index: transfer_out_FK                                       */
   /*==============================================================*/
   create  index transfer_out_FK on employee_transfer (
   in_department_id
   );
   
   /*==============================================================*/
   /* Table: salary                                                */
   /*==============================================================*/
   create table salary (
      salary_id            INT8                 not null,
      employee_id          INT8                 null,
      base_salary          DECIMAL(10)          null,
      bonus                DECIMAL(10)          null,
      fine                 DECIMAL(10)          null,
      payday               DATE                 null,
      constraint PK_SALARY primary key (salary_id)
   );
   
   comment on table salary is
   '工资信息表';
   
   /*==============================================================*/
   /* Index: salary_PK                                             */
   /*==============================================================*/
   create unique index salary_PK on salary (
   salary_id
   );
   
   /*==============================================================*/
   /* Index: belongs_FK                                            */
   /*==============================================================*/
   create  index belongs_FK on salary (
   employee_id
   );
   
   alter table attendance
      add constraint FK_ATTENDAN_ATTENDANC_EMPLOYEE foreign key (employee_id)
         references employee (employee_id)
         on delete restrict on update restrict;
   
   alter table employee
      add constraint FK_EMPLOYEE_BELONG_DEPARTME foreign key (department_id)
         references department (department_id)
         on delete restrict on update restrict;
   
   alter table employee_transfer
      add constraint FK_EMPLOYEE_TRANSFER_EMPLOYEE foreign key (employee_id)
         references employee (employee_id)
         on delete restrict on update restrict;
   
   alter table employee_transfer
      add constraint FK_EMPLOYEE_TRANSFER__DEPARTME foreign key (out_department_id)
         references department (department_id)
         on delete restrict on update restrict;
   
   alter table employee_transfer
      add constraint FK_EMPLOYEE_TRANSFER__DEPARTME2 foreign key (in_department_id)
         references department (department_id)
         on delete restrict on update restrict;
   
   alter table salary
      add constraint FK_SALARY_BELONGS_EMPLOYEE foreign key (employee_id)
         references employee (employee_id)
         on delete restrict on update restrict;
   ```

   注：因为实验要求基于GaussDB，但powerdesigner的`Database Gerneration`并不能直接选择生成GaussDB版的sql，所以我们选择了直接生成GaussDB所基于的PostgreSQL版的sql。

2. 到云数据库根据建表sql建立相应的表：

   这一步到学校提供的GaussDB云数据库控制台去执行就行了，需要注意的是上面的sql在GaussDB中不能直接执行，好像GaussDB不支持`comment`语法，把sql中的`comment`相关内容删除即可。执行完成后得到如下五个表:

   ![image-20230420132437945](temp.assets/image-20230420132437945.png)

3. 后端开发：

   后端方面选择基于springboot来进行一个快速的开发

    1. 首先是数据库链接，我们在maven中央仓库能找到[OpenGauss JDBC Driver](https://mvnrepository.com/artifact/org.opengauss/opengauss-jdbc)，将其引入我们的项目

       ```xml
       <!-- https://mvnrepository.com/artifact/org.opengauss/opengauss-jdbc -->
       <dependency>
           <groupId>org.opengauss</groupId>
           <artifactId>opengauss-jdbc</artifactId>
           <version>3.0.0</version>
       </dependency>
       ```

       再在`application.yaml`配置中配置好相应数据源即可：

       ```yaml
       spring:
         datasource:
           # opengauss数据库配置
           driver-class-name: org.opengauss.Driver
           url: jdbc:opengauss://110.41.126.115:8000/CQU_DB2021
           username: db_user2021_276
           password: db_user@123
       ```

    2. 之后便是项目主体功能的编写，主要是一些简单的增删改查，并不是我们这次实验的重点，这里不再赘述具体实现过程，具体见项目源码：https://github.com/2763981847/SEMS

    3. 测试，借助knife4j做了简单的接口调用测试，可能还有很多BUG没发现……

4. 前端开发：

   由于前端部分实验并未做具体要求，所以并未单独编写前端页面，而是使用knife4j生成了项目的接口文档以做演示。

5. 部署上线：

   借助maven的`package`命令将项目打成了jar包，并借助宝塔面板实现了项目的快速部署，在线演示地址：http://sems.autumnclouds.cn/doc.html

   注：jar包已一同提交，下载后使用`java -jar`命令即可运行，默认运行在本机8090端口。运行后访问http://localhost:8090/doc.html即可。