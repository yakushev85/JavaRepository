CREATE DATABASE ShopWebApp;
CREATE USER 'ShopWebApp'@'localhost' IDENTIFIED BY 'ydG8x0hXClMVn63TGoLD';
GRANT ALL PRIVILEGES ON *.* TO 'ShopWebApp'@'localhost';

USE ShopWebApp;

CREATE TABLE `User` (
`id` int(11) NOT NULL AUTO_INCREMENT,
`username` VARCHAR(255) NOT NULL,
`password` VARCHAR(255) NOT NULL,
`role` VARCHAR(255) DEFAULT NULL,
`created_at` datetime DEFAULT NULL,
	PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

CREATE TABLE `Product` (
`id` int(11) NOT NULL AUTO_INCREMENT,
`name` VARCHAR(255) DEFAULT NULL,
`price` float DEFAULT NULL,
`created_at` datetime DEFAULT NULL,
`created_by` VARCHAR(255) DEFAULT NULL,
	PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

CREATE TABLE `Transaction` (
`id` int(11) NOT NULL AUTO_INCREMENT,
`description` VARCHAR(255) DEFAULT NULL,
`product_id` int(11) DEFAULT NULL,
`user_id` int(11) DEFAULT NULL,
`created_at` datetime DEFAULT NULL,
	PRIMARY KEY (`id`),
KEY `Transaction_product_id_fkey_idx` (`product_id`),
CONSTRAINT `Transaction_product_id_fkey` FOREIGN KEY (`product_id`) REFERENCES `Product` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

INSERT INTO `Product` (`name`, `price`, `created_at`, `created_by`) VALUES ('Standard', 100.00, CURRENT_TIMESTAMP(), 'Admin');
INSERT INTO `Product` (`name`, `price`, `created_at`, `created_by`) VALUES ('Premium', 250.99, CURRENT_TIMESTAMP(), 'Admin');
INSERT INTO `Product` (`name`, `price`, `created_at`, `created_by`) VALUES ('Ultra', 345.55, CURRENT_TIMESTAMP(), 'Admin');
COMMIT;