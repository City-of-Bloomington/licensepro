 CREATE TABLE `depts` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `active` char(1) DEFAULT 'y',
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=utf8 ;
;;
;;

CREATE TABLE `divisions` (
  `id` int(11) NOT NULL,
  `dept_id` int(11) DEFAULT NULL,
  `name` varchar(30) DEFAULT NULL,
  `active` char(1) DEFAULT 'y',
  PRIMARY KEY (`id`),
  KEY `dept_id` (`dept_id`),
  CONSTRAINT `divisions_ibfk_1` FOREIGN KEY (`dept_id`) REFERENCES `depts` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ;
;;
;;
 CREATE TABLE `employee_restrictions` (
  `emp_id` int(11) NOT NULL,
  `restr_id` int(11) NOT NULL,
  UNIQUE KEY `emp_id` (`emp_id`,`restr_id`),
  KEY `restr_id` (`restr_id`),
  CONSTRAINT `employee_restrictions_ibfk_1` FOREIGN KEY (`emp_id`) REFERENCES `employees` (`id`),
  CONSTRAINT `employee_restrictions_ibfk_2` FOREIGN KEY (`restr_id`) REFERENCES `restrictions` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ;
;;
;;
CREATE TABLE `employee_selections` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `emp_id` int(11) DEFAULT NULL,
  `test_select_id` int(11) DEFAULT NULL,
  `retduty` char(1) DEFAULT NULL,
  `followup` char(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `emp_id` (`emp_id`),
  KEY `test_select_id` (`test_select_id`),
  CONSTRAINT `employee_selections_ibfk_1` FOREIGN KEY (`emp_id`) REFERENCES `employees` (`id`),
  CONSTRAINT `employee_selections_ibfk_2` FOREIGN KEY (`test_select_id`) REFERENCES `test_selections` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1426 DEFAULT CHARSET=utf8 ;
;;
;;
CREATE TABLE `employees` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `fname` varchar(30) NOT NULL,
  `mname` varchar(30) DEFAULT NULL,
  `lname` varchar(30) NOT NULL,
  `div_id` int(11) DEFAULT NULL,
  `dept_id` int(11) DEFAULT NULL,
  `lic_number` varchar(20) DEFAULT NULL,
  `dob` date DEFAULT NULL,
  `driver` char(1) DEFAULT NULL,
  `use_vehicle` char(1) DEFAULT NULL,
  `city_vehicle` char(1) DEFAULT NULL,
  `own_vehicle` char(1) DEFAULT NULL,
  `cdl_required` char(1) DEFAULT NULL,
  `state_id` char(2) DEFAULT NULL,
  `type_id` int(11) DEFAULT NULL,
  `userid` varchar(10) DEFAULT NULL,
  `date` date DEFAULT NULL,
  `userid2` varchar(10) DEFAULT NULL,
  `date2` date DEFAULT NULL,
  `active` char(1) DEFAULT 'y',
  `exp_date` date DEFAULT NULL,
  `employee_num` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `type_id` (`type_id`),
  KEY `state_id` (`state_id`),
  KEY `div_id` (`div_id`),
  CONSTRAINT `employees_ibfk_1` FOREIGN KEY (`type_id`) REFERENCES `types` (`id`),
  CONSTRAINT `employees_ibfk_2` FOREIGN KEY (`state_id`) REFERENCES `states` (`id`),
  CONSTRAINT `employees_ibfk_3` FOREIGN KEY (`div_id`) REFERENCES `divisions` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7687 DEFAULT CHARSET=utf8 ;
;;
;;
 CREATE TABLE `inactive_checks` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `date` date DEFAULT NULL,
  `confirmed` char(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8 ;
;;
;;
CREATE TABLE `inactive_employees` (
  `check_id` int(11) NOT NULL,
  `emp_id` int(11) NOT NULL,
  PRIMARY KEY (`check_id`,`emp_id`),
  KEY `emp_id` (`emp_id`),
  CONSTRAINT `inactive_employees_ibfk_1` FOREIGN KEY (`check_id`) REFERENCES `inactive_checks` (`id`),
  CONSTRAINT `inactive_employees_ibfk_2` FOREIGN KEY (`emp_id`) REFERENCES `employees` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
;;
;;
 CREATE TABLE `new_hires` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `date` date DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=51 DEFAULT CHARSET=utf8;
;;
;;
CREATE TABLE `newhire_employees` (
  `newhire_id` int(11) NOT NULL,
  `emp_id` int(11) NOT NULL,
  PRIMARY KEY (`newhire_id`,`emp_id`),
  KEY `emp_id` (`emp_id`),
  CONSTRAINT `newhire_employees_ibfk_1` FOREIGN KEY (`newhire_id`) REFERENCES `new_hires` (`id`),
  CONSTRAINT `newhire_employees_ibfk_2` FOREIGN KEY (`emp_id`) REFERENCES `employees` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ;
;;
;;
CREATE TABLE `nwdept_to_depts` (
  `nwdept_id` int(11) NOT NULL,
  `dept_id` int(11) NOT NULL,
  KEY `dept_id` (`dept_id`),
  CONSTRAINT `nwdept_to_depts_ibfk_1` FOREIGN KEY (`dept_id`) REFERENCES `depts` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ;
;;
;;
 CREATE TABLE `restrictions` (
  `id` int(11) NOT NULL,
  `type` char(1) DEFAULT NULL,
  `code` char(1) DEFAULT NULL,
  `name` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ;
;;
;;
CREATE TABLE `states` (
  `id` char(2) NOT NULL,
  `name` varchar(30) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ;
;;
;;
 CREATE TABLE `test_periods` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(10) DEFAULT NULL,
  `frequency` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 ;
;;
;;
CREATE TABLE `test_runs` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `year` int(11) DEFAULT NULL,
  `test_run` int(11) DEFAULT NULL,
  `done` char(1) DEFAULT NULL,
  `quant_alco` int(11) DEFAULT NULL,
  `quant_drug` int(11) DEFAULT NULL,
  `total_pool` int(11) DEFAULT NULL,
  `witness` varchar(30) DEFAULT NULL,
  `witness2` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=92 DEFAULT CHARSET=utf8;
;;
;;
 CREATE TABLE `test_selections` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `select_date` date DEFAULT NULL,
  `select_time` time DEFAULT NULL,
  `type` enum('Alcohol','Drug') DEFAULT NULL,
  `elegible` int(11) DEFAULT NULL,
  `selected` int(11) DEFAULT NULL,
  `percent` int(11) DEFAULT NULL,
  `test_run_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=207 DEFAULT CHARSET=utf8 ;
;;
;;
CREATE TABLE `test_types` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
;;
;;
CREATE TABLE `to_be_tested` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `emp_id` int(11) DEFAULT NULL,
  `select_date` date DEFAULT NULL,
  `select_time` time DEFAULT NULL,
  `test_type` enum('Alcohol','Drug') DEFAULT NULL,
  `dept_name` varchar(30) DEFAULT NULL,
  `division` varchar(30) DEFAULT NULL,
  `lname` varchar(30) DEFAULT NULL,
  `fname` varchar(30) DEFAULT NULL,
  `retduty` char(1) DEFAULT NULL,
  `followup` char(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `emp_id` (`emp_id`),
  CONSTRAINT `to_be_tested_ibfk_1` FOREIGN KEY (`emp_id`) REFERENCES `employees` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1034 DEFAULT CHARSET=utf8 ;
;;
;;
CREATE TABLE `types` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=66 DEFAULT CHARSET=utf8;
;;
;;
CREATE TABLE `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userid` varchar(50) DEFAULT NULL,
  `fullname` varchar(70) NOT NULL,
  `role` enum('View','Edit','Edit:Delete','Edit:Delete:Admin') DEFAULT NULL,
  `active` char(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `userid` (`userid`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;
;;
;;
CREATE TABLE `year_tests` (
  `year` int(11) NOT NULL,
  `per_alcohol` int(11) DEFAULT NULL,
  `per_drug` int(11) DEFAULT NULL,
  `period_type` enum('Monthly','Quarterly','Annually') DEFAULT NULL,
  `period_count` int(11) DEFAULT NULL,
  `checksum` int(11) DEFAULT NULL,
  PRIMARY KEY (`year`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ;
;;

