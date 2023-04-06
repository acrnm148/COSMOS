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
-- Table structure for table `image`
--

DROP TABLE IF EXISTS `image`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `image` (
  `image_id` bigint NOT NULL AUTO_INCREMENT,
  `couple_id` bigint DEFAULT NULL,
  `created_time` varchar(20) DEFAULT NULL,
  `image_url` text,
  `review_id` bigint DEFAULT NULL,
  PRIMARY KEY (`image_id`),
  KEY `FK2fyo6jg4fpq9108cbi16v37ft` (`review_id`),
  CONSTRAINT `FK2fyo6jg4fpq9108cbi16v37ft` FOREIGN KEY (`review_id`) REFERENCES `review` (`review_id`)
) ENGINE=InnoDB AUTO_INCREMENT=508 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `image`
--

LOCK TABLES `image` WRITE;
/*!40000 ALTER TABLE `image` DISABLE KEYS */;
INSERT INTO `image` VALUES (1,850517939,'20230330','https://user-images.githubusercontent.com/87971876/224617778-7c532060-7f94-4082-9c1c-e27bff0b30cd.png',49),(2,850517939,'20230330','https://user-images.githubusercontent.com/87971876/224617778-7c532060-7f94-4082-9c1c-e27bff0b30cd.png',49),(3,850517939,'20230330','https://user-images.githubusercontent.com/87971876/224617778-7c532060-7f94-4082-9c1c-e27bff0b30cd.png',49),(56,850517939,'20230401','https://user-images.githubusercontent.com/87971876/224617778-7c532060-7f94-4082-9c1c-e27bff0b30cd.png',49),(57,850517939,'20230401','https://user-images.githubusercontent.com/87971876/224617778-7c532060-7f94-4082-9c1c-e27bff0b30cd.png',50),(58,850517939,'20230402','https://user-images.githubusercontent.com/87971876/224617778-7c532060-7f94-4082-9c1c-e27bff0b30cd.png',51),(59,850517939,'20230402','https://user-images.githubusercontent.com/87971876/224617778-7c532060-7f94-4082-9c1c-e27bff0b30cd.png',52),(87,51106719,'20230404','https://user-images.githubusercontent.com/87971876/224617778-7c532060-7f94-4082-9c1c-e27bff0b30cd.png',132),(88,0,'20230302','https://user-images.githubusercontent.com/87971876/224617778-7c532060-7f94-4082-9c1c-e27bff0b30cd.png',133),(89,0,'20230402','https://user-images.githubusercontent.com/87971876/224617778-7c532060-7f94-4082-9c1c-e27bff0b30cd.png',133),(90,0,'20230403','https://user-images.githubusercontent.com/87971876/224617778-7c532060-7f94-4082-9c1c-e27bff0b30cd.png',133),(270,0,'20230405','https://user-images.githubusercontent.com/87971876/224617778-7c532060-7f94-4082-9c1c-e27bff0b30cd.png',133),(271,0,'20230405','https://user-images.githubusercontent.com/87971876/224617778-7c532060-7f94-4082-9c1c-e27bff0b30cd.png',133),(272,0,'20230405','https://user-images.githubusercontent.com/87971876/224617778-7c532060-7f94-4082-9c1c-e27bff0b30cd.png',133),(352,0,'20230406','https://user-images.githubusercontent.com/87971876/224617778-7c532060-7f94-4082-9c1c-e27bff0b30cd.png',133),(353,0,'20230406','https://user-images.githubusercontent.com/87971876/224617778-7c532060-7f94-4082-9c1c-e27bff0b30cd.png',133),(354,0,'20230406','https://user-images.githubusercontent.com/87971876/224617778-7c532060-7f94-4082-9c1c-e27bff0b30cd.png',133),(355,0,'20230406','https://user-images.githubusercontent.com/87971876/224617778-7c532060-7f94-4082-9c1c-e27bff0b30cd.png',133),(356,0,'20230406','https://cosmoss3.s3.ap-northeast-2.amazonaws.com/2a55ab81-c87b-44a2-8a5d-436d2ecda41d.jpg',324),(357,0,'20230406','https://cosmoss3.s3.ap-northeast-2.amazonaws.com/c940c6ba-4bc0-43b1-ac86-972698ab46bf.gif',324),(358,0,'20230406','https://cosmoss3.s3.ap-northeast-2.amazonaws.com/545071ae-7aac-4f30-ab98-3f4054763535.png',324);
/*!40000 ALTER TABLE `image` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-04-06 11:39:45
