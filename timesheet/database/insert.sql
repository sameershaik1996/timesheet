use employee;

select * from emp_employees;

INSERT INTO `emp_designations` VALUES 	(1,'2019-05-07 23:15:21','2019-05-07 23:15:21','SA'),
										(2,'2019-05-07 23:18:27','2019-05-07 23:18:27','SSA'),
										(3,'2019-05-08 08:48:42','2019-05-08 08:48:42','Manager');
                                        
INSERT INTO `emp_employees` VALUES 		(1,'2019-05-07 23:15:52','2019-05-07 23:15:52',NULL,'1997-01-20','skaramsetty@red-shift.us','rs_000001','sreenivas','MALE','2019-03-14','karamsetty','SINGLE','9944431759',NULL,1),
										(3,'2019-05-07 23:19:22','2019-05-07 23:19:22',NULL,'2008-01-01','Ssameer@red-shift.us','rs_000002','Sameer','MALE','2019-03-15','Shaik','SINGLE','9944431751',NULL,2),
                                        (4,'2019-05-07 23:20:05','2019-05-07 23:20:05',NULL,'2008-01-01','silango@red-shift.us','rs_000003','Soorya','MALE','2019-03-15','Kumar','SINGLE','9944431752',NULL,2),
                                        (5,'2019-05-08 08:51:35','2019-05-08 08:51:35',NULL,'1990-01-01','meher@red-shift.us','rs_000004','meher','MALE','2019-12-11','vaddadi','MARRIED','1234567890',NULL,3);
									
				
                
use auth;                
INSERT INTO `auth_users` VALUES (1,'2019-05-07 23:15:53','2019-05-07 23:15:53','skaramsetty@red-shift.us',1,'$2a$10$mNpys.cFyvayDc6Ls3J3I.EFYQquDphEf3Tl04QOb4l.puFjwAOjO','skaramsetty'),
								(2,'2019-05-07 23:19:22','2019-05-07 23:19:22','Ssameer@red-shift.us',3,'$2a$10$tJ00gOIqfdXVVALtDQj9jOM8crItGwsEkhusin1gEKTnQktWxiwTO','Ssameer'),
                                (3,'2019-05-07 23:20:05','2019-05-07 23:20:05','silango@red-shift.us',4,'$2a$10$5WkW5ZUKUOaH8F/K9LHnf.0XWHZYT76pAx.apZorF2l.WIeGwfHLm','silango'),
								(4,'2019-05-08 08:51:36','2019-05-08 08:51:36','meher@red-shift.us',5,'$2a$10$Tu7IrPRJC1hMY38NxQO4e.XK96mr.oA96b/plDKCOzr7kd1b.Vl0K','meher');