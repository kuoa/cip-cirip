/* mysql -h 132.227.201.129 -P 33306 -u "gr3_postaru" -pWdSPtM < createDataBase.sql */

USE gr3_postaru

DROP TABLE IF EXISTS users ;
DROP TABLE IF EXISTS `session`;
DROP TABLE IF EXISTS friends ; 


CREATE TABLE users 
      (id INTEGER PRIMARY KEY auto_increment,
      login VARCHAR (20) NOT NULL UNIQUE,
      password VARCHAR (64) NOT NULL,
      email VARCHAR (255),
      name VARCHAR (255)
      );


CREATE TABLE `session`
       (`key` VARCHAR (32) PRIMARY KEY,
       `user_id` INTEGER,
       expires TIMESTAMP,
       root BOOLEAN,
       INDEX(`key`, `user_id`, `expires`)
       );


CREATE TABLE friends
       (`from` INTEGER,
       `to` INTEGER,
       `date` TIMESTAMP,

       PRIMARY key (`from`, `to`)
       );
