use auth;

insert into auth_roles(name) values('EMPLOYEE');
insert into auth_roles(name) values('ADMIN');
insert into auth_roles(name) values('MANAGER');
insert into auth_roles(name) values('PM');


INSERT INTO auth_users VALUES (1,0,'2019-05-07 23:15:53',0,'2019-05-07 23:15:53','admin@red-shift.us',0,'$2a$10$hA8gjchkjcHayuWk2fqzC.rMQXyx6yzxRArG7aBmUVMj2H6InE1vW',1,'admin',2);

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
insert into auth_permissions(name) values('taskcard_get');
insert into auth_permissions(name) values('taskcard_common');
insert into auth_permissions(name) values('timesheet_crud');
insert into auth_permissions(name) values('timesheet_get');
insert into auth_permissions(name) values('timesheet_common');
insert into auth_permissions(name) values('invoice_crud');
insert into auth_permissions(name) values('invoice_common');
insert into auth_permissions(name) values('location_crud');
insert into auth_permissions(name) values('location_get');
insert into auth_permissions(name) values('taskcarddetail_crud');
insert into auth_permissions(name) values('taskcarddetail_get');
insert into auth_permissions(name) values('ratecard_crud');
insert into auth_permissions(name) values('ratecard_get');


INSERT INTO auth_roles_permission VALUES (2,1),(3,1),(1,2),(2,2),(3,2),(2,3),(3,3),(1,4),(2,4),(3,4),(2,5),(3,5),(1,6),(2,6),(3,6),(2,7),(3,7),(1,8),(2,8),(3,8),(1,9),(2,9),(3,9),(2,10),(3,10),(1,11),(2,11),(3,11),(2,12),(3,12),(1,13),(2,13),(3,13),(2,14),(3,14),(1,15),(2,15),(3,15),(1,16),(2,16),(3,16),(2,17),(3,17),(1,18),(2,18),(3,18),(1,19),(2,19),(3,19),(2,20),(3,20),(1,21),(2,21),(3,21),(1,22),(2,22),(3,22),(1,23),(2,23),(3,23),(1,24),(2,24),(3,24),(1,25),(2,25),(3,25),(1,26),(2,26),(3,26),(1,27),(2,27),(3,27),(1,28),(2,28),(3,28),(2,29),(3,29),(1,30),(2,30),(3,30),(2,31),(3,31),(1,32),(2,32),(3,32),(1,33),(2,33),(3,33),(1,34),(2,34),(3,34),(2,35),(3,35),(1,36),(2,36),(3,36);
