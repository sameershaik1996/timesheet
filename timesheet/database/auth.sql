drop database if exists auth;

create database if not exists auth;

use auth;
/*!50503 set default_storage_engine = InnoDB */;
/*!50503 select CONCAT('storage engine: ', @@default_storage_engine) as INFO */;
drop table users,roles,permissions;
CREATE TABLE IF NOT EXISTS users (
    id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    employee_id INT NOT NULL,
    user_name VARCHAR(32) NOT NULL,
    password VARCHAR(256) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
alter table users change employee_id employee_id int ;

CREATE TABLE IF NOT EXISTS roles (
    id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(32)
);

CREATE TABLE IF NOT EXISTS user_role_mapping (
    user_id INT NOT NULL,
    role_id INT NOT NULL,
    FOREIGN KEY (user_id)
        REFERENCES users (id),
    FOREIGN KEY (role_id)
        REFERENCES roles (id)
);

CREATE TABLE IF NOT EXISTS permissions (
    id INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(32) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS role_permission_mapping (
    role_id INT NOT NULL,
    permission_id INT NOT NULL,
    FOREIGN KEY (permission_id)
        REFERENCES permissions (id)
);

alter table role_permission_mapping add foreign key(role_id) references roles(id);

show tables;