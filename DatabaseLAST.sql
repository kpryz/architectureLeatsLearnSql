-- MySQL dump 10.13  Distrib 8.0.12, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: learnsqlmain
-- ------------------------------------------------------
-- Server version	8.0.12

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `complexity`
--

DROP TABLE IF EXISTS `complexity`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `complexity` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `complexity` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `complexity`
--

LOCK TABLES `complexity` WRITE;
/*!40000 ALTER TABLE `complexity` DISABLE KEYS */;
INSERT INTO `complexity` VALUES (1,'EASY'),(2,'NORMAL'),(3,'HARD'),(4,'VERY_HARD');
/*!40000 ALTER TABLE `complexity` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES (1,'ADMIN'),(2,'USER');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sqltask`
--

DROP TABLE IF EXISTS `sqltask`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `sqltask` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `maxAttempts` int(11) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `postcondition` longtext,
  `precondition` longtext,
  `query` mediumtext,
  `complexity_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK8upd43fkmad5j68fl5dw0dpo` (`complexity_id`),
  CONSTRAINT `FK8upd43fkmad5j68fl5dw0dpo` FOREIGN KEY (`complexity_id`) REFERENCES `complexity` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sqltask`
--

LOCK TABLES `sqltask` WRITE;
/*!40000 ALTER TABLE `sqltask` DISABLE KEYS */;
INSERT INTO `sqltask` VALUES (1,'Get all information about customers',3,'Get Customers',NULL,NULL,'select * from customers;',2);
INSERT INTO `sqltask` VALUES (2,'Get all information about cars',3,'Get All Cars',NULL,NULL,'select * from car;',2);
INSERT INTO `sqltask` VALUES (3,'Get all orders',3,'Get All Orders',NULL,NULL,'select * from orders;',2);
INSERT INTO `sqltask` VALUES (4,'Update car with id = 3, set color to gray',3,'Update car table',NULL,NULL,'update car set color = "gray" where id = 3;',2);
INSERT INTO `sqltask` VALUES (5,'Insert Into Car table',3,'Insert into car table','DELETE FROM CAR WHERE ',NULL,'SELECT * FROM CAR WHERE color="blue" AND model="BMW";',2);
INSERT INTO `sqltask` VALUES (6,'Delete Customer Oleh Paliukh',3,'Delete customer','INSERT INTO customer (id, country, postal_code, address, city, contact_name, customer_name) VALUES (7, ''Ukraine'', ''78842'', ''Bandery 2'', ''Lviv'', ''Oleh Paliukh'', ''NULP'');',NULL,'SELECT * FROM Customer WHERE contact_name = "Oleh Paliukh";',2);
/*!40000 ALTER TABLE `sqltask` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sqltaskprogress`
--

DROP TABLE IF EXISTS `sqltaskprogress`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `sqltaskprogress` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `attempts` int(11) DEFAULT NULL,
  `lastModified` datetime DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `sqlTask_id` bigint(20) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKpex35egdeinur5br9ergv90uk` (`sqlTask_id`),
  KEY `FK9tb73fj9jk01f8uqfc1i89xyg` (`user_id`),
  CONSTRAINT `FK9tb73fj9jk01f8uqfc1i89xyg` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FKpex35egdeinur5br9ergv90uk` FOREIGN KEY (`sqlTask_id`) REFERENCES `sqltask` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sqltaskprogress`
--

LOCK TABLES `sqltaskprogress` WRITE;
/*!40000 ALTER TABLE `sqltaskprogress` DISABLE KEYS */;
INSERT INTO `sqltaskprogress` VALUES (1,5,'2019-12-09 03:12:39','IN_PROGRESS',1,1);
INSERT INTO `sqltaskprogress` VALUES (2,5,'2019-04-04 04:12:39','IN_PROGRESS',2,1);
INSERT INTO `sqltaskprogress` VALUES (3,5,'2019-11-05 05:12:39','IN_PROGRESS',3,1);
INSERT INTO `sqltaskprogress` VALUES (4,5,'2019-12-07 06:12:39','IN_PROGRESS',4,1);
INSERT INTO `sqltaskprogress` VALUES (5,5,'2019-12-01 07:12:39','IN_PROGRESS',5,1);
INSERT INTO `sqltaskprogress` VALUES (6,5,'2019-12-09 08:12:39','IN_PROGRESS',6,1);
/*!40000 ALTER TABLE `sqltaskprogress` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `active` int(11) DEFAULT NULL,
  `email` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `role_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKn82ha3ccdebhokx3a8fgdqeyy` (`role_id`),
  CONSTRAINT `FKn82ha3ccdebhokx3a8fgdqeyy` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,1,'k@gmail.com','Kostiantyn Pryzyhlei','$2a$10$YMVS4k0mZKLN9nQYzieXEeFw.bZLuqhMOdLKWxSRCofydLGRyMRY6',2),(2,1,'pryzygley@gmail.com','Kostiantyn Pryzyhlei','$2a$10$anuEkDcIgCUPSWSXRUa.v.Ysuk/yyVjWZlPcJH7rex9/JHkf65AV2',1);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-12-09  3:35:31
