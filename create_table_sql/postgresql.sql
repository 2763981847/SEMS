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

