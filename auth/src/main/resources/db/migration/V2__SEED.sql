use auth;

insert into auth_roles(name) values('EMPLOYEE');
insert into auth_roles(name) values('ADMIN');
insert into auth_roles(name) values('MANAGER');
insert into auth_roles(name) values('PM');


insert into auth_users values (1,0,'2019-05-07 23:15:53',0,'2019-05-07 23:15:53','admin@red-shift.us',0,'$2a$10$hA8gjchkjcHayuWk2fqzC.rMQXyx6yzxRArG7aBmUVMj2H6InE1vW',1,'admin',2);

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
insert into auth_permissions(name) values('employeerole_crud');
insert into auth_permissions(name) values('employeerole_get');

insert into auth_roles_permission values (2,1),(3,1),(4,1),(1,2),(2,2),(3,2),(4,2),(2,3),(3,3),(4,3),(1,4),(2,4),(3,4),(4,4),(2,5),(3,5),(4,5),(1,6),(2,6),(3,6),(4,6),(2,7),(3,7),(4,7),(1,8),(2,8),(3,8),(4,8),(1,9),(2,9),(3,9),(4,9),(2,10),(3,10),(4,10),(1,11),(2,11),(3,11),(4,11),(2,12),(3,12),(4,12),(1,13),(2,13),(3,13),(4,13),(3,14),(4,14),(1,15),(3,15),(4,15),(1,16),(2,16),(3,16),(4,16),(3,17),(4,17),(1,18),(3,18),(4,18),(1,19),(3,19),(4,19),(3,20),(4,20),(1,21),(3,21),(4,21),(1,22),(3,22),(4,22),(1,23),(3,23),(4,23),(1,24),(3,24),(4,24),(1,25),(3,25),(4,25),(1,26),(3,26),(4,26),(1,27),(3,27),(4,27),(1,28),(3,28),(4,28),(3,29),(4,29),(1,30),(3,30),(4,30),(2,31),(3,31),(4,31),(1,32),(2,32),(3,32),(4,32),(1,33),(3,33),(4,33),(1,34),(3,34),(4,34),(3,35),(4,35),(1,36),(3,36),(4,36),(1,37),(2,37),(3,37),(4,37),(1,38),(2,38),(3,38),(4,38);
