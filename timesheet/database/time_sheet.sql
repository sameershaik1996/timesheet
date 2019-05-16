##script for time_sheet database

drop database if exists time_sheet;

create database if not exists time_sheet;

use time_sheet;
CREATE TABLE IF NOT EXISTS clients (
    id INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(32) NOT NULL,
    location VARCHAR(32) NOT NULL,
    PRIMARY KEY (id)
);


CREATE TABLE IF NOT EXISTS projects (
    id INT NOT NULL,
    name VARCHAR(32) NOT NULL,
    description VARCHAR(32),
    estimated_hours DECIMAL,
    client_id INT,
    status ENUM('open', 'closed') NOT NULL,
    type ENUM('billable', 'non-billable') NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    started_on DATE NOT NULL,
    ended_on DATE,
    create_on TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_on TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    FOREIGN KEY (client_id)
        REFERENCES clients (id)
);

CREATE TABLE IF NOT EXISTS tasks (
    id INT NOT NULL,
    name VARCHAR(32) NOT NULL,
    description VARCHAR(64) NOT NULL,
    project_id INT,
    status ENUM('open', 'Inprogress', 'closed') NOT NULL,
    task_type ENUM('billable', 'non-billable'),
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    started_on DATE NOT NULL,
    ended_on DATE,
    create_on TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_on TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    FOREIGN KEY (project_id)
        REFERENCES projects (id)
);




CREATE TABLE IF NOT EXISTS task_employee_mapping (
    id INT NOT NULL AUTO_INCREMENT,
    task_id INT NOT NULL,
    employee_id INT NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (task_id)
        REFERENCES tasks (id)
);


CREATE TABLE IF NOT EXISTS project_employee_mapping (
    id INT NOT NULL AUTO_INCREMENT,
    project_id INT NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (project_id)
        REFERENCES projects (id)
);


CREATE TABLE IF NOT EXISTS time_cards (
    id INT NOT NULL AUTO_INCREMENT,
    task_id INT NOT NULL,
    employee_id INT NOT NULL,
    status ENUM('approved', 'inProcess', 'rejected'),
    comment VARCHAR(64) NOT NULL,
    created_on timestamp not null default current_timestamp,
    PRIMARY KEY (id)
);



create table if not exists time_sheets(
	id int not null auto_increment,
    name varchar(32) not null,
    comment varchar(64) not null,
    status enum('beingVerified','approved','Rejected'),
    from_date date not null,
    to_date date not null,
    created_on timestamp not null default current_timestamp,
    PRIMARY KEY (id)
);

create table time_card_sheet_mapping(
id int not null auto_increment,
time_card_id int not null,
time_sheet_id int not null,
primary key(id),
foreign key(time_card_id) references time_cards(id),
foreign key(time_sheet_id) references time_sheets(id)

);