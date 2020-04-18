	create TABLE `countries` (
	  `id` bigint(20) NOT NULL AUTO_INCREMENT,
	  `country_code` varchar(255) DEFAULT NULL,
	  `country_name` varchar(255) DEFAULT NULL,
	  `phone_code` int(11) NOT NULL,
	  PRIMARY KEY (`id`)
	);

	create TABLE `states` (
	  `id` bigint(20) NOT NULL AUTO_INCREMENT,
	  `state_name` varchar(255) DEFAULT NULL,
	  `country_id` bigint(20) DEFAULT NULL,
	  PRIMARY KEY (`id`),
	  FOREIGN KEY (`country_id`) REFERENCES `countries` (`id`)
	);




	create TABLE `address` (
		`id` BIGINT(30) NOT NULL AUTO_INCREMENT,
		`address_line_1` VARCHAR(100) NOT NULL,
		`address_line_2` VARCHAR(50),
		`zip_code` INT(11) DEFAULT NULL,
		`country` VARCHAR(50) DEFAULT NULL,
		`state` VARCHAR(30) DEFAULT NULL,
		`city` VARCHAR(30) DEFAULT NULL,

		PRIMARY KEY (`id`)
	);

	create TABLE `vendors`(
		`id` BIGINT(30) NOT NULL AUTO_INCREMENT,
		`vendor_id` VARCHAR(10) NOT NULL ,
		 `vendor_name`  VARCHAR(30) NOT NULL,
		`description` VARCHAR(100),
		`created_timestamp` DATETIME NOT NULL,
		`updated_timestamp` DATETIME NOT NULL,
		`created_by` VARCHAR(20),
		`updated_by` VARCHAR(20),

		 PRIMARY KEY (`id`),
		 UNIQUE KEY(`vendor_id`)
	);


	create TABLE `systems` (
		`id` BIGINT(30) NOT NULL AUTO_INCREMENT,
		`system_id` VARCHAR(10)NOT NULL,
		`system_name` VARCHAR(50),
		`description` VARCHAR(100),
		`created_timestamp` DATETIME NOT NULL,
		`updated_timestamp` DATETIME NOT NULL,
		`created_by` VARCHAR(20),
		`updated_by` VARCHAR(20),
		PRIMARY KEY (`id`),
		UNIQUE(system_id)
	);

	create TABLE `privileges` (
		`id` BIGINT(30) NOT NULL AUTO_INCREMENT,
		`privilege_id` VARCHAR(10)NOT NULL,
		`privilege_name` VARCHAR(50),
         `resource_url` LONGTEXT NOT NULL ,
		`description` VARCHAR(100),
		`created_timestamp` DATETIME NOT NULL,
		`updated_timestamp` DATETIME NOT NULL,
		`created_by` VARCHAR(20),
		`updated_by` VARCHAR(20),
		PRIMARY KEY (`id`),
		UNIQUE(privilege_id)
	);


	create TABLE `organizations`(
		`id` BIGINT(30) NOT NULL AUTO_INCREMENT,
		`org_id` VARCHAR(10) NOT NULL,
		`org_name` VARCHAR(50) NOT NULL,
		`org_address_id` BIGINT(20) NOT NULL,
		`org_web_url` VARCHAR(30),
		`org_spoc_address_id` BiGINT(30) NOT NULL,
		`parent_org_id` VARCHAR(10) ,

		`created_timestamp` DATETIME NOT NULL,
		`updated_timestamp` DATETIME NOT NULL,
		`created_by` VARCHAR(20),
		`updated_by` VARCHAR(20),
		PRIMARY KEY (`id`),
		UNIQUE(`org_id`),
		FOREIGN KEY(`org_address_id`)
			REFERENCES `address`(`id`)

	);
	create TABLE `facilities` (
		`id` BIGINT(30) NOT NULL AUTO_INCREMENT,
		`fac_name` VARCHAR(50) NOT NULL,
		`fac_type` VARCHAR(20) NOT NULL,
		`fac_id` VARCHAR(10) NOT NULL,
		`org_id` VARCHAR(10) NOT NULL,
		`fac_address_id` BIGINT(20) NOT NULL,
		`fac_spoc_name` VARCHAR(50),
		`fac_spoc_email` VARCHAR(30),
		`fac_spoc_phone` VARCHAR(20),
		`time_zone` VARCHAR(100),
		`fac_status` VARCHAR(10) NOT NULL DEFAULT 'ACTIVE',
		`created_timestamp` DATETIME NOT NULL,
		`updated_timestamp` DATETIME NOT NULL,
		`created_by` VARCHAR(20),
		`updated_by` VARCHAR(20),
		PRIMARY KEY (`id`),
		UNIQUE (`fac_id`,`org_id`),
		FOREIGN KEY (`fac_address_id`)
			REFERENCES `address` (`id`),
		FOREIGN KEY (`org_id`)
			REFERENCES `organizations` (`org_id`)
	);

    create TABLE facility_detail (
    `id` BIGINT(30) NOT NULL AUTO_INCREMENT,
    `fac_id` BIGINT(30) NOT NULL,
    `vendor_id` VARCHAR(10) NOT NULL,
    `system_id` VARCHAR(10) NOT NULL,
    `created_timestamp` DATETIME NOT NULL,
    `updated_timestamp` DATETIME NOT NULL,
    `created_by` VARCHAR(20),
    `updated_by` VARCHAR(20),
    PRIMARY KEY (`id`),
    unique key(`fac_id`,`vendor_id`,`system_id`),
    FOREIGN KEY (`fac_id`)
        REFERENCES facilities (`id`)
);



	create TABLE `roles` (
		`id` BIGINT(30) NOT NULL AUTO_INCREMENT,
		`role_id` VARCHAR(30) NOT NULL ,
		`role_name` VARCHAR(20)NOT NULL,
	    `org_id` VARCHAR(10) NOT NULL,
        `Status` VARCHAR(10) NOT NULL DEFAULT 'ACTIVE',
		`created_timestamp` DATETIME NOT NULL,
		`updated_timestamp` DATETIME NOT NULL,
		`created_by` VARCHAR(20),
		`updated_by` VARCHAR(20),
		PRIMARY KEY (`id`),
		UNIQUE (`role_id`,`org_id`)
	);

	create TABLE `role_privilege` (
		`id` BIGINT(30) NOT NULL AUTO_INCREMENT,
		`role_id` BIGINT(30) NOT NULL,
		`privilege_id` BIGINT(30) NOT NULL,
		PRIMARY KEY (`id`),
		UNIQUE (`role_id` , `privilege_id`),
		FOREIGN KEY (`role_id`)
			REFERENCES `roles` (id),
		FOREIGN KEY (`privilege_id`)
			REFERENCES `privileges` (id)
	);

	create TABLE `users` (
		`id` BIGINT(30) NOT NULL AUTO_INCREMENT,
		`user_id` VARCHAR(20) NOT NULL,
		`email` VARCHAR(30) NOT NULL,

		`org_id` VARCHAR(10) NOT NULL,
		`fac_id` VARCHAR(10),
		`user_first_name` VARCHAR(50) NOT NULL,
		`user_last_name` VARCHAR(50) NOT NULL,
		`password` VARCHAR(100) DEFAULT NULL,
		`role_id` VARCHAR(30) NOT NULL,
		`authentication_type` VARCHAR(50),
		`user_type` VARCHAR(20) DEFAULT 'USER',
        `expire_token_at` DATETIME,
		`user_team_name` VARCHAR(50),
		`user_status` VARCHAR(10) NOT NULL DEFAULT 'ACTIVE',
		`user_last_logged` DATETIME,
		`created_timestamp` DATETIME NOT NULL,
		`updated_timestamp` DATETIME NOT NULL,
		`created_by` VARCHAR(20),
		`updated_by` VARCHAR(20),
		PRIMARY KEY (`id`),
		UNIQUE (`user_id`),
        UNIQUE (`email`)

	);


	create TABLE `interface_mappings` (
		`id` BIGINT(30) NOT NULL AUTO_INCREMENT,
		`source_vendor_id` bigint(30) NOT NULL,
		`destination_vendor_id` bigint(30) NOT NULL,
		`interface` VARCHAR(20),
		`interface_type`  VARCHAR(20),
		`created_timestamp` DATETIME NOT NULL,
		`updated_timestamp` DATETIME NOT NULL,
		`created_by` VARCHAR(20),
		`updated_by` VARCHAR(20),
		`status` VARCHAR(20),
		PRIMARY KEY (`id`),
		FOREIGN KEY (source_vendor_id)
			REFERENCES  vendors(id),
		FOREIGN KEY (destination_vendor_id)
			REFERENCES  vendors(id),
		unique(`interface_type`)
	);


	#alter table interface_mappings add column interface_type varchar(20) after id; S
	create TABLE `source_meta_data` (
		`id` BIGINT(30) NOT NULL AUTO_INCREMENT,
		`created_timestamp` DATETIME NOT NULL,
		`updated_timestamp` DATETIME NOT NULL,
		`created_by` VARCHAR(20),
		`updated_by` VARCHAR(20),
		`source` LONGTEXT,
		`interface_mapping_id` BIGINT(30) NOT NULL,
		PRIMARY KEY (`id`),
		FOREIGN KEY (interface_mapping_id)
			REFERENCES  interface_mappings(id)
	);


	create TABLE `destination_meta_data` (
		`id` BIGINT(30) NOT NULL AUTO_INCREMENT,
		`created_timestamp` DATETIME NOT NULL,
		`updated_timestamp` DATETIME NOT NULL,
		`created_by` VARCHAR(20),
		`updated_by` VARCHAR(20),
		`destination` LONGTEXT,
		`interface_mapping_id` BIGINT(30) NOT NULL,
		PRIMARY KEY (`id`),
		FOREIGN KEY (interface_mapping_id)
			REFERENCES  interface_mappings(id)
	);

	create TABLE `jolt_specs` (
		`id` BIGINT(30) NOT NULL AUTO_INCREMENT,
		`created_timestamp` DATETIME NOT NULL,
		`updated_timestamp` DATETIME NOT NULL,
		`created_by` VARCHAR(20),
		`updated_by` VARCHAR(20),
		`jolt_spec` LONGTEXT,
		`interface_mapping_id` BIGINT(30) NOT NULL,
		PRIMARY KEY (`id`),
		FOREIGN KEY (interface_mapping_id)
			REFERENCES  interface_mappings(id)
	);

	create TABLE `transactions` (
		`id` BIGINT(30) NOT NULL AUTO_INCREMENT,
		`created_timestamp` DATETIME NOT NULL,
		`updated_timestamp` DATETIME NOT NULL,
		`created_by` VARCHAR(20),
		`updated_by` VARCHAR(20),
		`transaction_type` VARCHAR(100),
		`status` VARCHAR(20) NOT NULL DEFAULT 'FAILED',
		PRIMARY KEY (`id`)
	);


	create TABLE `transaction_requests` (
		`id` BIGINT(30) NOT NULL AUTO_INCREMENT,
		`created_timestamp` DATETIME NOT NULL,
		`updated_timestamp` DATETIME NOT NULL,
		`created_by` VARCHAR(20),
		`updated_by` VARCHAR(20),
		`transaction_request` LONGTEXT,
		`transaction_id` BIGINT(30) NOT NULL,
		PRIMARY KEY (`id`),
		FOREIGN KEY (transaction_id)
			REFERENCES transactions (id)
	);

	create TABLE `transaction_responses` (
		`id` BIGINT(30) NOT NULL AUTO_INCREMENT,
		`created_timestamp` DATETIME NOT NULL,

	  `updated_timestamp` DATETIME NOT NULL,
		`created_by` VARCHAR(20),
		`updated_by` VARCHAR(20),
		`transaction_response` LONGTEXT,
		`transaction_id` BIGINT(30) NOT NULL,
		PRIMARY KEY (`id`),
		FOREIGN KEY (transaction_id)
			REFERENCES transactions (id)
	);

    create TABLE `password_policy`(
    `id` BIGINT(5) NOT NULL AUTO_INCREMENT,
    `user_type` VARCHAR(20)NOT NULL,
    `password_pattern` VARCHAR(50) ,
    `expiration_limit` LONG,
    PRIMARY KEY(`id`)
    );

    create TABLE `menus`(
		`id` BIGINT(5) NOT NULL auto_increment,
        `menu_prefix` VARCHAR(10) NOT NULL,
        `menu_linked_to` VARCHAR(10),
        PRIMARY KEY(`id`)
    );

