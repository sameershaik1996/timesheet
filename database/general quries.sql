drop database auth;

create database auth;

use auth;

show tables;
delete from auth_roles where id=3 or id =4;

INSERT INTO auth_roles(name) VALUES('EMPLOYEE');
INSERT INTO auth_roles(name) VALUES('MANAGER');

INSERT INTO auth_permissions(name) VALUES('employee_save');
INSERT INTO auth_permissions(name) VALUES('employee_update');
INSERT INTO auth_permissions(name) VALUES('employee_get');

insert into auth_roles_permission values(6,3);


select * from auth_roles;
select  * from auth_permissions order by id;

select * from auth_employee_roles;
select * from auth_roles_permission;


select * from auth_users;

