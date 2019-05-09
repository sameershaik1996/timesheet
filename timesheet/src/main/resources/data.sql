INSERT INTO `pss_clients` (`id`,`created_by`,`created_time_stamp`,`updated_by`,`updated_time_stamp`,`about`,`client_code`,`domain`,`name`,`offering`,`specialization`,`status`,`url`,`address_id`,`billing_address_id`) VALUES (1,1,'2019-05-08 10:54:42',1,'2019-05-08 10:54:42','Belk','C001','Supplychain','Belk589','Development,Testing','Supplychain','ACTIVE','xyz.com',1,2);
INSERT INTO `pss_pocs` (`id`,`created_by`,`created_time_stamp`,`updated_by`,`updated_time_stamp`,`email`,`name`,`phone_number`,`client_id`) VALUES (1,1,'2019-05-08 10:54:42',1,'2019-05-08 10:54:42','qwertyui@gmail.com','xyz','12345678',1);

INSERT INTO `pss_addresses` (`id`,`address_line1`,`address_line2`,`country_id`,`state_id`,zip_code) VALUES (1,'dt2,My home','machavaram',1,50,"123456");
INSERT INTO `pss_addresses` (`id`,`address_line1`,`address_line2`,`country_id`,`state_id`,zip_code) VALUES (2,'dt2,My home','machavaram',1,51,"123456");

INSERT INTO `pss_projects` (`id`,`created_by`,`created_time_stamp`,`updated_by`,`updated_time_stamp`,`description`,`end_date`,`ended_on`,`estimated_cost`,`estimated_days`,`manager_id`,`name`,`project_code`,`start_date`,`started_on`,`status`,`type`,`client_id`,`rate_card_id`) VALUES (1,1,'2019-05-08 10:54:47',1,'2019-05-08 10:54:47','xyz','2019-05-31',NULL,10.30,10.00,1,'project','C001p1','2019-04-15',NULL,'ACTIVE','FIXED_BID',1,NULL);

INSERT INTO `pss_project_employees` (`project_id`,`employee_id`) VALUES (1,1);
INSERT INTO `pss_project_employees` (`project_id`,`employee_id`) VALUES (1,3);

INSERT INTO `pss_tasks` (`id`,`created_by`,`created_time_stamp`,`updated_by`,`updated_time_stamp`,`billable_hour`,`description`,`end_date`,`ended_on`,`name`,`non_billable_hour`,`start_date`,`started_on`,`status`,`task_code`,`type`,`used_hour`,`project_id`) VALUES (1,1,'2019-05-08 10:55:04',1,'2019-05-08 10:55:04',12.00,'testing','2019-04-20',NULL,'task',4.00,'2019-04-15',NULL,'UNASSIGNED','c001p1t1','BILLABLE',NULL,1);

INSERT INTO `pss_task_employees` (`task_id`,`employee_id`) VALUES (1,1);
INSERT INTO `pss_task_employees` (`task_id`,`employee_id`) VALUES (1,7);

INSERT INTO `pss_task_skills` (`task_id`,`skill_id`) VALUES (1,1);