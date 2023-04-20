/*
SQLyog Ultimate v12.09 (64 bit)
MySQL - 5.7.39-log : Database - employee_management
*********************************************************************
*/


/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`employee_management` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;

USE `employee_management`;

/*Table structure for table `attendance` */

DROP TABLE IF EXISTS `attendance`;

CREATE TABLE `attendance` (
  `attendance_id` bigint(20) NOT NULL COMMENT '考勤编号',
  `employee_id` bigint(20) NOT NULL COMMENT '员工编号',
  `date` date NOT NULL COMMENT '考勤日期',
  `sign_in_time` time DEFAULT NULL COMMENT '签到时间',
  `sign_out_time` time DEFAULT NULL COMMENT '签退时间',
  PRIMARY KEY (`attendance_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='员工考勤表';


/*Table structure for table `department` */

DROP TABLE IF EXISTS `department`;

CREATE TABLE `department` (
  `department_id` bigint(20) NOT NULL COMMENT '部门编号',
  `name` varchar(50) NOT NULL COMMENT '部门名称',
  `introduction` varchar(400) DEFAULT NULL COMMENT '部门简介',
  `establishment_date` date DEFAULT NULL COMMENT '部门成立时间',
  PRIMARY KEY (`department_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='部门信息表';


/*Table structure for table `employee` */

DROP TABLE IF EXISTS `employee`;

CREATE TABLE `employee` (
  `employee_id` bigint(20) NOT NULL COMMENT '员工编号',
  `department_id` bigint(20) NOT NULL COMMENT '部门编号',
  `name` varchar(50) NOT NULL COMMENT '姓名',
  `sex` smallint(6) DEFAULT NULL COMMENT '性别',
  `id_number` varchar(30) DEFAULT NULL COMMENT '身份证号',
  `phone_number` varchar(30) DEFAULT NULL COMMENT '手机号',
  `job_title` varchar(20) DEFAULT NULL COMMENT '职位',
  `empno` bigint(20) NOT NULL COMMENT '工号',
  `age` int(11) DEFAULT NULL COMMENT '年龄',
  `enrollment_date` date DEFAULT NULL COMMENT '入职日期',
  PRIMARY KEY (`employee_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='员工信息表';


/*Table structure for table `employee_transfer` */

DROP TABLE IF EXISTS `employee_transfer`;

CREATE TABLE `employee_transfer` (
  `transfer_id` bigint(20) NOT NULL COMMENT '调动编号',
  `employee_id` bigint(20) NOT NULL COMMENT '员工编号',
  `transfer_reason` varchar(400) DEFAULT NULL COMMENT '调动原因',
  `original_job_title` varchar(20) DEFAULT NULL COMMENT '原职位',
  `new_job_title` varchar(20) DEFAULT NULL COMMENT '新职位',
  `out_dep_id` bigint(20) DEFAULT NULL COMMENT '迁出部门',
  `in_dep_id` bigint(20) DEFAULT NULL COMMENT '迁入部门',
  `transfer_date` date DEFAULT NULL COMMENT '调动日期',
  PRIMARY KEY (`transfer_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='员工调动记录表';


/*Table structure for table `salary` */

DROP TABLE IF EXISTS `salary`;

CREATE TABLE `salary` (
  `salary_id` bigint(20) NOT NULL COMMENT '工资编号',
  `employee_id` bigint(20) NOT NULL COMMENT '员工编号',
  `payday` date DEFAULT NULL COMMENT '发放日期',
  `base_salary` decimal(10,0) DEFAULT NULL COMMENT '基本工资',
  `bonus` decimal(10,0) DEFAULT NULL COMMENT '奖金',
  `fine` decimal(10,0) DEFAULT NULL COMMENT '罚金',
  PRIMARY KEY (`salary_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
