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
-- Table structure for table `notification`
--

DROP TABLE IF EXISTS `notification`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `notification` (
  `notification_id` bigint NOT NULL AUTO_INCREMENT,
  `content` text,
  `is_read` bigint NOT NULL,
  `user_seq` bigint DEFAULT NULL,
  `event` varchar(255) DEFAULT NULL,
  `is_clicked` bigint NOT NULL,
  PRIMARY KEY (`notification_id`),
  KEY `FK66shf0xa1n4y7pm30m0rvg4ks` (`user_seq`),
  CONSTRAINT `FK66shf0xa1n4y7pm30m0rvg4ks` FOREIGN KEY (`user_seq`) REFERENCES `user` (`user_seq`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=170 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `notification`
--

LOCK TABLES `notification` WRITE;
/*!40000 ALTER TABLE `notification` DISABLE KEYS */;
INSERT INTO `notification` VALUES (1,'커플 요청이 수락되었습니다.',1,1,NULL,0),(2,'커플 요청이 수락되었습니다.',1,1,NULL,0),(3,'커플 요청이 수락되었습니다.',1,1,NULL,0),(4,'커플 요청이 수락되었습니다.',1,1,NULL,0),(5,'커플 요청이 수락되었습니다.',1,1,NULL,0),(6,'커플 요청이 수락되었습니다.',1,1,NULL,0),(7,'커플 요청이 수락되었습니다.',1,1,NULL,0),(8,'커플 요청이 수락되었습니다.',1,1,NULL,0),(9,'커플 요청이 수락되었습니다.',1,1,NULL,0),(11,'커플 요청이 수락되었습니다.',1,97,NULL,0),(12,'커플 요청이 수락되었습니다.',1,1,NULL,0),(13,'커플 요청이 수락되었습니다.',1,1,NULL,0),(14,'커플 요청이 수락되었습니다.',1,1,NULL,0),(15,'커플 요청이 수락되었습니다.',1,1,NULL,0),(16,'커플 요청이 수락되었습니다.',1,1,NULL,0),(17,'커플 요청이 수락되었습니다.',1,1,NULL,0),(26,'커플 요청이 수락되었습니다.',1,98,NULL,0),(27,'커플 요청이 수락되었습니다.',1,97,NULL,0),(67,'커플 요청이 수락되었습니다.',1,1,NULL,0),(92,'why....',1,98,NULL,0),(99,'안녕하삼',1,98,NULL,0),(110,'wow',1,98,'test',0),(111,'커플이 매칭되었습니다.',1,98,'makeCouple',0),(112,'커플이 매칭되었습니다.',1,97,'makeCouple',0),(113,'커플 연결이 끊어졌습니다.',1,97,'removeCouple',0),(116,'커플이 매칭되었습니다.',1,97,'makeCouple',0),(117,'커플 연결이 끊어졌습니다.',1,97,'removeCouple',0),(118,'커플 연결이 끊어졌습니다.',1,98,'removeCouple',0),(120,'커플 연결이 끊어졌습니다.',1,97,'removeCouple',0),(121,'커플 연결이 끊어졌습니다.',1,1,'removeCouple',0),(122,'커플 연결이 끊어졌습니다.',1,19,'removeCouple',0),(132,'커플이 매칭되었습니다.',1,98,'makeCouple',0),(133,'커플이 매칭되었습니다.',1,1,'makeCouple',0),(145,'일정이 등록되었습니다. ',1,98,'makePlan',0),(159,'일정이 등록되었습니다. ',1,98,'makePlan',0),(160,'일정이 등록되었습니다. ',1,98,'makePlan',0),(161,'일정이 등록되었습니다. ',1,98,'makePlan',0),(162,'일정이 등록되었습니다. ',1,98,'makePlan',0),(163,'일정이 등록되었습니다. ',1,98,'makePlan',0),(164,'일정이 등록되었습니다. ',1,98,'makePlan',0),(165,'일정이 등록되었습니다. ',1,98,'makePlan',0),(166,'일정이 등록되었습니다. ',1,98,'makePlan',0),(167,'일정이 등록되었습니다. ',1,98,'makePlan',0),(168,'일정이 등록되었습니다. ',1,98,'makePlan',0),(169,'일정이 등록되었습니다. ',1,98,'makePlan',0);
/*!40000 ALTER TABLE `notification` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-04-06 11:39:59
