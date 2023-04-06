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
-- Table structure for table `indireviewcategory`
--

DROP TABLE IF EXISTS `indireviewcategory`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `indireviewcategory` (
  `indi_review_category_id` bigint NOT NULL AUTO_INCREMENT,
  `indi_review_category` varchar(255) DEFAULT NULL,
  `review_id` bigint DEFAULT NULL,
  PRIMARY KEY (`indi_review_category_id`),
  KEY `FKgctnghrntlf47h9xhpbari1qr` (`review_id`),
  CONSTRAINT `FKgctnghrntlf47h9xhpbari1qr` FOREIGN KEY (`review_id`) REFERENCES `review` (`review_id`)
) ENGINE=InnoDB AUTO_INCREMENT=265 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `indireviewcategory`
--

LOCK TABLES `indireviewcategory` WRITE;
/*!40000 ALTER TABLE `indireviewcategory` DISABLE KEYS */;
INSERT INTO `indireviewcategory` VALUES (1,'메뉴가 다양해요',49),(2,'메뉴가 다양해요',50),(3,'주차 지원이 가능해요',51),(7,'커피가 맛있어요',52),(12,'음식이 맛있어요',132),(129,'? 이용이 편리해요',323),(180,'활동시설이 다양해요',133),(182,'가성비가 좋아요',324);
/*!40000 ALTER TABLE `indireviewcategory` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-04-06 11:39:47
