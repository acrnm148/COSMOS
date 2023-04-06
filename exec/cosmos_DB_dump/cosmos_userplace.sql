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
-- Table structure for table `userplace`
--

DROP TABLE IF EXISTS `userplace`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `userplace` (
  `user_place_id` bigint NOT NULL AUTO_INCREMENT,
  `place_id` bigint DEFAULT NULL,
  `user_seq` bigint DEFAULT NULL,
  PRIMARY KEY (`user_place_id`),
  KEY `FKji01rqityaljjwa6haf7sjk7l` (`place_id`),
  KEY `FKsgp5je0jrix44vpt8hpv2pukg` (`user_seq`),
  CONSTRAINT `FKji01rqityaljjwa6haf7sjk7l` FOREIGN KEY (`place_id`) REFERENCES `place` (`place_id`),
  CONSTRAINT `FKsgp5je0jrix44vpt8hpv2pukg` FOREIGN KEY (`user_seq`) REFERENCES `user` (`user_seq`)
) ENGINE=InnoDB AUTO_INCREMENT=110 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `userplace`
--

LOCK TABLES `userplace` WRITE;
/*!40000 ALTER TABLE `userplace` DISABLE KEYS */;
INSERT INTO `userplace` VALUES (14,72,1),(15,74,1),(16,76,1),(25,316,1),(26,3816,1),(27,37628,1),(28,38776,1),(30,72,98),(31,74,98),(32,77,98),(33,80,98),(34,81,98),(35,82,98),(36,83,98),(38,29411,1),(39,4719,1),(40,9032,1),(41,9388,1),(42,1845,1),(43,32565,1),(44,8599,1),(45,203,1),(46,283,1),(47,294,1),(49,970,1),(54,389,1),(55,541,1);
/*!40000 ALTER TABLE `userplace` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-04-06 11:39:49
