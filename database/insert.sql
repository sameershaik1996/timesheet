use employee;

SELECT 
    *
FROM
    emp_employees;
desc  emp_employees;
INSERT INTO `emp_designations` VALUES 	(1,'2019-05-07 23:15:21','2019-05-07 23:15:21','SA'),
										(2,'2019-05-07 23:18:27','2019-05-07 23:18:27','SSA'),
										(3,'2019-05-08 08:48:42','2019-05-08 08:48:42','Manager');
								
INSERT INTO `emp_skills` VALUES 		(1,'2019-05-07 23:15:04','2019-05-07 23:15:04','Angular'),
										(2,'2019-05-07 23:18:18','2019-05-07 23:18:18','Spring');
                                        
INSERT INTO `emp_employees` VALUES 		(1,'2019-05-07 23:15:52','2019-05-07 23:15:52','1997-01-20','skaramsetty@red-shift.us','rs_000001','sreenivas','MALE','2019-03-14','karamsetty','SINGLE','9944431759',NULL,NULL,1,1),
										(3,'2019-05-07 23:19:22','2019-05-07 23:19:22','2008-01-01','Ssameer@red-shift.us','rs_000002','Sameer','MALE','2019-03-15','Shaik','SINGLE','9944431751',NULL,null,1,2),
                                        (4,'2019-05-07 23:20:05','2019-05-07 23:20:05','2008-01-01','silango@red-shift.us','rs_000003','Soorya','MALE','2019-03-15','Kumar','SINGLE','9944431752',NULL,null,0,2),
                                        (5,'2019-05-08 08:51:35','2019-05-08 08:51:35','1990-01-01','meher@red-shift.us','rs_000004','meher','MALE','2019-12-11','vaddadi','MARRIED','1234567890',NULL,null,1,3);
									
				
                
use auth;                
INSERT INTO `auth_users` VALUES (1,0,'2019-05-07 23:15:53',0,'2019-05-07 23:15:53','admin@red-shift.us',0,'$2a$10$hA8gjchkjcHayuWk2fqzC.rMQXyx6yzxRArG7aBmUVMj2H6InE1vW','admin',2);
drop database auth;
create database auth;                                
use auth;
select * from auth_users;
select * from auth_roles;

select * from auth_roles_permission;

insert into auth_roles(name) values('ADMIN');
insert into auth_roles(name) values('MANAGER');
insert into auth_roles(name) values('PM');
insert into auth_roles(name) values('EMPLOYEE');

insert into auth_permissions(name) values('designation_crud');
insert into auth_permissions(name) values('designation_get');
insert into auth_permissions(name) values('skill_crud');
insert into auth_permissions(name) values('skill_get');
insert into auth_permissions(name) values('employee_crud');
insert into auth_permissions(name) values('employee_get');
insert into auth_permissions(name) values('user_crud');
insert into auth_permissions(name) values('user_get');
insert into auth_permissions(name) values('user_common');
insert into auth_permissions(name) values('role_crud');
insert into auth_permissions(name) values('role_common');
insert into auth_permissions(name) values('permission_crud');
insert into auth_permissions(name) values('address_common');
insert into auth_permissions(name) values('client_crud');
insert into auth_permissions(name) values('client_get');
insert into auth_permissions(name) values('client_common');
insert into auth_permissions(name) values('project_crud');
insert into auth_permissions(name) values('project_get');
insert into auth_permissions(name) values('project_common');
insert into auth_permissions(name) values('task_crud');
insert into auth_permissions(name) values('task_get');
insert into auth_permissions(name) values('task_common');
insert into auth_permissions(name) values('taskcard_crud');
insert into auth_permissions(name) values('taskcard_common');
insert into auth_permissions(name) values('timesheet_crud');
insert into auth_permissions(name) values('timesheet_common');










  

insert into auth_roles_permission values(2,6);                                