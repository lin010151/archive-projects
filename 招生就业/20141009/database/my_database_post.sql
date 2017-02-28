CREATE DATABASE  IF NOT EXISTS `my_database` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `my_database`;
-- MySQL dump 10.13  Distrib 5.6.17, for Win32 (x86)
--
-- Host: localhost    Database: my_database
-- ------------------------------------------------------
-- Server version	5.6.20

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `post`
--

DROP TABLE IF EXISTS `post`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `post` (
  `postID` varchar(32) NOT NULL,
  `companyID` varchar(32) NOT NULL,
  `postName` varchar(20) NOT NULL,
  `education` varchar(10) DEFAULT NULL,
  `releaseTime` datetime DEFAULT NULL,
  `jobsCategory` varchar(50) DEFAULT NULL,
  `jobsAddress` varchar(50) DEFAULT NULL,
  `postRequirements` varchar(500) DEFAULT NULL,
  `salary` int(11) DEFAULT NULL,
  `major` varchar(60) DEFAULT NULL,
  `relatefile` varchar(45) DEFAULT NULL,
  `note` varchar(255) DEFAULT NULL,
  `recruitNum` int(11) DEFAULT NULL,
  PRIMARY KEY (`postID`),
  KEY `postID` (`postID`),
  KEY `companyID_idx` (`companyID`),
  KEY `FK3498A0735D7F6` (`companyID`),
  CONSTRAINT `FK3498A0735D7F6` FOREIGN KEY (`companyID`) REFERENCES `company` (`companyID`),
  CONSTRAINT `companyID` FOREIGN KEY (`companyID`) REFERENCES `company` (`companyID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `post`
--

LOCK TABLES `post` WRITE;
/*!40000 ALTER TABLE `post` DISABLE KEYS */;
INSERT INTO `post` VALUES ('001','001','程序员','本科','2013-03-03 00:00:00','工程类',NULL,'有责任心',NULL,'','','',NULL),('3cf9a2049c3231237f17a37d3c015392','002','df','sdf',NULL,'的说法',NULL,'dsf',12344,'的说法','','',5);
/*!40000 ALTER TABLE `post` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2014-10-09 22:38:12
