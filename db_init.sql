CREATE DATABASE `movie_service`;

CREATE TABLE `cinema_halls` (
                                `id` bigint NOT NULL AUTO_INCREMENT,
                                `capacity` int NOT NULL,
                                `description` varchar(255) DEFAULT NULL,
                                PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `movie_sessions` (
                                  `id` bigint NOT NULL AUTO_INCREMENT,
                                  `showTime` datetime(6) DEFAULT NULL,
                                  `cinemaHall_id` bigint DEFAULT NULL,
                                  `movie_id` bigint DEFAULT NULL,
                                  PRIMARY KEY (`id`),
                                  KEY `FKk04umq7ulfxwqtgoqf59a0reu` (`cinemaHall_id`),
                                  KEY `FKatpmn0h31nwhwdgd0ogr8q6kj` (`movie_id`),
                                  CONSTRAINT `FKatpmn0h31nwhwdgd0ogr8q6kj` FOREIGN KEY (`movie_id`) REFERENCES `movies` (`id`),
                                  CONSTRAINT `FKk04umq7ulfxwqtgoqf59a0reu` FOREIGN KEY (`cinemaHall_id`) REFERENCES `cinema_halls` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `movies` (
                          `id` bigint NOT NULL AUTO_INCREMENT,
                          `description` varchar(255) DEFAULT NULL,
                          `title` varchar(255) DEFAULT NULL,
                          PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `users` (
                         `id` bigint NOT NULL AUTO_INCREMENT,
                         `email` varchar(255) DEFAULT NULL,
                         `password` varchar(255) DEFAULT NULL,
                         `salt` tinyblob,
                         PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

