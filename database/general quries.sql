select T1.name,T2.skill_id from tasks as T1 join task_skill_mapping as T2;

insert into tasks(name,description,project_id,status,start_date,end_date,started_on,ended_on) values('task1','asd',1,'open','2019-12-21','2019-12-21','2019-12-21','2019-12-21');