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
-- Table structure for table `auth_permission`
--

DROP TABLE IF EXISTS `auth_permission`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `auth_permission` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `content_type_id` int NOT NULL,
  `codename` varchar(100) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `auth_permission_content_type_id_codename_01ab375a_uniq` (`content_type_id`,`codename`),
  CONSTRAINT `auth_permission_content_type_id_2f476e4b_fk_django_co` FOREIGN KEY (`content_type_id`) REFERENCES `django_content_type` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=85 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `auth_permission`
--

LOCK TABLES `auth_permission` WRITE;
/*!40000 ALTER TABLE `auth_permission` DISABLE KEYS */;
INSERT INTO `auth_permission` VALUES (1,'Can add adjective',1,'add_adjective'),(2,'Can change adjective',1,'change_adjective'),(3,'Can delete adjective',1,'delete_adjective'),(4,'Can view adjective',1,'view_adjective'),(5,'Can add course',2,'add_course'),(6,'Can change course',2,'change_course'),(7,'Can delete course',2,'delete_course'),(8,'Can view course',2,'view_course'),(9,'Can add courseplace',3,'add_courseplace'),(10,'Can change courseplace',3,'change_courseplace'),(11,'Can delete courseplace',3,'delete_courseplace'),(12,'Can view courseplace',3,'view_courseplace'),(13,'Can add gugun code',4,'add_guguncode'),(14,'Can change gugun code',4,'change_guguncode'),(15,'Can delete gugun code',4,'delete_guguncode'),(16,'Can view gugun code',4,'view_guguncode'),(17,'Can add image',5,'add_image'),(18,'Can change image',5,'change_image'),(19,'Can delete image',5,'delete_image'),(20,'Can view image',5,'view_image'),(21,'Can add noun',6,'add_noun'),(22,'Can change noun',6,'change_noun'),(23,'Can delete noun',6,'delete_noun'),(24,'Can view noun',6,'view_noun'),(25,'Can add place',7,'add_place'),(26,'Can change place',7,'change_place'),(27,'Can delete place',7,'delete_place'),(28,'Can view place',7,'view_place'),(29,'Can add plan',8,'add_plan'),(30,'Can change plan',8,'change_plan'),(31,'Can delete plan',8,'delete_plan'),(32,'Can view plan',8,'view_plan'),(33,'Can add refresh token',9,'add_refreshtoken'),(34,'Can change refresh token',9,'change_refreshtoken'),(35,'Can delete refresh token',9,'delete_refreshtoken'),(36,'Can view refresh token',9,'view_refreshtoken'),(37,'Can add review',10,'add_review'),(38,'Can change review',10,'change_review'),(39,'Can delete review',10,'delete_review'),(40,'Can view review',10,'view_review'),(41,'Can add reviewcategory',11,'add_reviewcategory'),(42,'Can change reviewcategory',11,'change_reviewcategory'),(43,'Can delete reviewcategory',11,'delete_reviewcategory'),(44,'Can view reviewcategory',11,'view_reviewcategory'),(45,'Can add reviewplace',12,'add_reviewplace'),(46,'Can change reviewplace',12,'change_reviewplace'),(47,'Can delete reviewplace',12,'delete_reviewplace'),(48,'Can view reviewplace',12,'view_reviewplace'),(49,'Can add sido code',13,'add_sidocode'),(50,'Can change sido code',13,'change_sidocode'),(51,'Can delete sido code',13,'delete_sidocode'),(52,'Can view sido code',13,'view_sidocode'),(53,'Can add user',14,'add_user'),(54,'Can change user',14,'change_user'),(55,'Can delete user',14,'delete_user'),(56,'Can view user',14,'view_user'),(57,'Can add userplace',15,'add_userplace'),(58,'Can change userplace',15,'change_userplace'),(59,'Can delete userplace',15,'delete_userplace'),(60,'Can view userplace',15,'view_userplace'),(61,'Can add log entry',16,'add_logentry'),(62,'Can change log entry',16,'change_logentry'),(63,'Can delete log entry',16,'delete_logentry'),(64,'Can view log entry',16,'view_logentry'),(65,'Can add permission',17,'add_permission'),(66,'Can change permission',17,'change_permission'),(67,'Can delete permission',17,'delete_permission'),(68,'Can view permission',17,'view_permission'),(69,'Can add group',18,'add_group'),(70,'Can change group',18,'change_group'),(71,'Can delete group',18,'delete_group'),(72,'Can view group',18,'view_group'),(73,'Can add user',19,'add_user'),(74,'Can change user',19,'change_user'),(75,'Can delete user',19,'delete_user'),(76,'Can view user',19,'view_user'),(77,'Can add content type',20,'add_contenttype'),(78,'Can change content type',20,'change_contenttype'),(79,'Can delete content type',20,'delete_contenttype'),(80,'Can view content type',20,'view_contenttype'),(81,'Can add session',21,'add_session'),(82,'Can change session',21,'change_session'),(83,'Can delete session',21,'delete_session'),(84,'Can view session',21,'view_session');
/*!40000 ALTER TABLE `auth_permission` ENABLE KEYS */;
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
