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
-- Table structure for table `review`
--

DROP TABLE IF EXISTS `review`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `review` (
  `review_id` bigint NOT NULL AUTO_INCREMENT,
  `contents` text,
  `contents_open` bit(1) DEFAULT NULL,
  `created_time` varchar(255) DEFAULT NULL,
  `image_open` bit(1) DEFAULT NULL,
  `nickname` varchar(255) DEFAULT NULL,
  `score` int DEFAULT NULL,
  `user_seq` bigint DEFAULT NULL,
  PRIMARY KEY (`review_id`),
  KEY `FKq7vee7vmlqrhvnflflsw6p1q7` (`user_seq`),
  CONSTRAINT `FKq7vee7vmlqrhvnflflsw6p1q7` FOREIGN KEY (`user_seq`) REFERENCES `user` (`user_seq`)
) ENGINE=InnoDB AUTO_INCREMENT=559 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `review`
--

LOCK TABLES `review` WRITE;
/*!40000 ALTER TABLE `review` DISABLE KEYS */;
INSERT INTO `review` VALUES (49,'리뷰테스트',_binary '','20230401',_binary '','노래하는 호랑이',5,1),(50,'리뷰테스트',_binary '','20230401',_binary '','춤을 추는 시츄',5,1),(51,'리뷰테스트',_binary '','20230402',_binary '','춤을 추는 호랑이',5,1),(52,'리뷰테스트',_binary '','20230402',_binary '','행복한 호랑이',5,1),(132,'리뷰테스트',_binary '','20230404',_binary '','춤을 추는 시츄',5,1),(133,'tnwjd123',_binary '','20230404',_binary '','노래하는 햄스터',5,1),(323,'asd',_binary '','20230405',_binary '\0','춤을 추는 호랑이',4,1),(324,'123123aaàààààààààà',_binary '','20230405',_binary '','춤을 추는 햄스터',5,1);
/*!40000 ALTER TABLE `review` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-04-06 11:39:43
