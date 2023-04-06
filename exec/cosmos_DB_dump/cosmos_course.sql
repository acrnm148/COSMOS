-- MySQL dump 10.13  Distrib 8.0.30, for Win64 (x86_64)
--
-- Host: j8e104.p.ssafy.io    Database: cosmos
-- ------------------------------------------------------
-- Server version	8.0.32

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `course`
--

DROP TABLE IF EXISTS `course`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `course` (
  `course_id` bigint NOT NULL AUTO_INCREMENT,
  `date` varchar(255) DEFAULT NULL,
  `name` varchar(400) DEFAULT NULL,
  `orders` int DEFAULT NULL,
  `wish` bit(1) DEFAULT NULL,
  `plan_id` bigint DEFAULT NULL,
  `user_seq` bigint DEFAULT NULL,
  `couple_id` bigint DEFAULT NULL,
  PRIMARY KEY (`course_id`),
  KEY `FKg0et4ds7i5rthfd005i47qlpy` (`plan_id`),
  KEY `FKgeoxt6tvwn4rget6cd62s7tb7` (`user_seq`),
  CONSTRAINT `FKg0et4ds7i5rthfd005i47qlpy` FOREIGN KEY (`plan_id`) REFERENCES `plan` (`plan_id`),
  CONSTRAINT `FKgeoxt6tvwn4rget6cd62s7tb7` FOREIGN KEY (`user_seq`) REFERENCES `user` (`user_seq`)
) ENGINE=InnoDB AUTO_INCREMENT=1577 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `course`
--

LOCK TABLES `course` WRITE;
/*!40000 ALTER TABLE `course` DISABLE KEYS */;
INSERT INTO `course` VALUES (1501,'2023-04-01','ㄴ나',1,_binary '',34,439,NULL),(1502,NULL,NULL,0,_binary '\0',NULL,440,NULL),(1503,NULL,NULL,0,_binary '\0',NULL,440,NULL),(1504,NULL,NULL,0,_binary '\0',NULL,440,NULL),(1505,NULL,NULL,0,_binary '\0',NULL,18,NULL),(1506,NULL,NULL,0,_binary '\0',NULL,438,NULL),(1507,NULL,NULL,0,_binary '\0',NULL,438,NULL),(1508,NULL,'\\\\\\\\\\',0,_binary '',NULL,438,NULL),(1509,NULL,'qwer',0,_binary '',NULL,438,NULL),(1510,NULL,NULL,0,_binary '\0',NULL,1,NULL),(1511,'2023-04-15','123',1,_binary '',32,1,NULL),(1528,NULL,'test',0,_binary '',NULL,98,NULL);
/*!40000 ALTER TABLE `course` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-04-06 11:39:44
