create database if not exists employee;
use employee;
show tables;
/*!50503 set default_storage_engine = InnoDB */;
/*!50503 select CONCAT('storage engine: ', @@default_storage_engine) as INFO */;


CREATE TABLE IF NOT EXISTS designations (
    `id` INT NOT NULL AUTO_INCREMENT,
    `designation` VARCHAR(32) NOT NULL,
    PRIMARY KEY (`id`)
);


CREATE TABLE IF NOT EXISTS `employees` (
    `id` INT(11) NOT NULL AUTO_INCREMENT,
    `first_name` VARCHAR(32) NOT NULL,
    `last_name` VARCHAR(32) NOT NULL,
    `dob` DATE NOT NULL,
    `email_id` VARCHAR(255) NOT NULL,
    `gender` INT NOT NULL,
    `location` VARCHAR(32) NOT NULL,
    `designation_id` INT NOT NULL,
    `joining_date` DATE NOT NULL,
    `created_at` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` TIMESTAMP NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`designation_id`)
        REFERENCES designations (id)
);

alter table employees
	add
    foreign key(designation_id) references designations(id);



