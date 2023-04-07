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
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `user_seq` bigint NOT NULL AUTO_INCREMENT,
  `age_range` varchar(255) DEFAULT NULL,
  `birthday` varchar(255) DEFAULT NULL,
  `couple_id` bigint DEFAULT NULL,
  `couple_yn` varchar(255) DEFAULT NULL,
  `create_time` datetime(6) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `phone_number` varchar(255) DEFAULT NULL,
  `profile_img_url` varchar(255) DEFAULT NULL,
  `role` varchar(255) DEFAULT NULL,
  `type1` varchar(255) DEFAULT NULL,
  `type2` varchar(255) DEFAULT NULL,
  `user_id` varchar(255) DEFAULT NULL,
  `user_name` varchar(255) DEFAULT NULL,
  `couple_profile_img_url` varchar(255) DEFAULT NULL,
  `couple_user_seq` bigint DEFAULT NULL,
  `couple_success_date` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`user_seq`)
) ENGINE=InnoDB AUTO_INCREMENT=695 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'20','1215',290393059,'Y','2023-03-30 09:02:07.798000','pos04118@naver.com',NULL,'http://k.kakaocdn.net/dn/cewp4U/btr6rxDYwCG/QQkuVN8tvF3QIZmjMNxtXK/img_640x640.jpg','USER','JAY','JAT','2649279844','혜지','http://k.kakaocdn.net/dn/L8yPy/btr5flEuROy/ddh2yZKoQNRewS1QI85hek/img_640x640.jpg',98,'2023-04-06'),(18,'30','0825',0,'N','2023-03-30 12:14:56.529000',NULL,NULL,'http://k.kakaocdn.net/dn/MFLhL/btr53EwXmwn/T7RCxpJ9VGn77PeBkNKnK0/img_640x640.jpg','USER','JOY','JOT','2679116017','이상찬',NULL,NULL,NULL),(19,'20','0306',0,'N','2023-03-30 12:16:17.163000','indl1670@naver.com',NULL,'http://k.kakaocdn.net/dn/bksoit/btr4ymR770p/kIfTijQizN2dCP6eMI7B0k/img_640x640.jpg','USER','EAY','EAT','2727879392','김정윤',NULL,NULL,NULL),(97,NULL,NULL,0,'N','2023-04-02 15:59:56.368000','ssookkookk@naver.com',NULL,'http://k.kakaocdn.net/dn/bWKIGs/btr2aIbMgQO/9mOb5ApOuxoY4Bn9KvJd2K/img_640x640.jpg','USER','EOT','EOY','2543509554','강수나',NULL,NULL,NULL),(98,NULL,NULL,0,'N','2023-04-02 16:03:15.970000','dbwls387@naver.com',NULL,'http://k.kakaocdn.net/dn/L8yPy/btr5flEuROy/ddh2yZKoQNRewS1QI85hek/img_640x640.jpg','USER','JAY','JAT','2725396388','반유진',NULL,NULL,NULL),(143,NULL,NULL,0,'N','2023-04-04 16:15:13.948000','kmj5052@gmail.com',NULL,'http://k.kakaocdn.net/dn/dpk9l1/btqmGhA2lKL/Oz0wDuJn1YV2DIn92f6DVK/img_640x640.jpg','USER',NULL,NULL,'2735013416','강민재',NULL,NULL,NULL),(227,NULL,NULL,0,'N','2023-04-05 09:58:50.214000','qq6988@pusan.ac.kr',NULL,'http://k.kakaocdn.net/dn/dpk9l1/btqmGhA2lKL/Oz0wDuJn1YV2DIn92f6DVK/img_640x640.jpg','USER',NULL,NULL,'2713925477','장시우',NULL,NULL,NULL),(438,NULL,NULL,0,'N','2023-04-05 14:22:03.220000','backji5@kakao.com',NULL,'http://k.kakaocdn.net/dn/uPmlc/btrUi1XNibN/voRgPfb7NN2fcquZdoNEeK/img_640x640.jpg','USER','JAY','JAT','2736271367','김효은',NULL,NULL,NULL),(439,NULL,NULL,0,'N','2023-04-05 16:00:43.618000','cheuora@gmail.com',NULL,'http://k.kakaocdn.net/dn/pgWzD/btrMNTmpXb3/YebexwcyqiNDTTo4WkYY51/img_640x640.jpg','USER','JAY','JAT','2736405678','성준킴',NULL,NULL,NULL),(440,NULL,NULL,0,'N','2023-04-05 16:41:35.833000','wndjf11@naver.com',NULL,'http://k.kakaocdn.net/dn/1YFpp/btr5Q2Zxs4I/VvthN8iVHKkrMSkGM9BOlK/img_640x640.jpg','USER','EOT','EOY','2736463011','김찬희',NULL,NULL,NULL);
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

-- Dump completed on 2023-04-06 11:39:59
