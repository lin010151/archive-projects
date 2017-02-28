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
-- Table structure for table `pushinfo`
--

DROP TABLE IF EXISTS `pushinfo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pushinfo` (
  `ID` varchar(32) NOT NULL,
  `postID` varchar(32) NOT NULL,
  `majorID` varchar(32) DEFAULT NULL,
  `addressID` varchar(32) DEFAULT NULL,
  `sex` int(1) DEFAULT NULL,
  `title` varchar(45) DEFAULT NULL,
  `message` varchar(45) DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `postID_idx` (`postID`),
  KEY `post_idx` (`postID`),
  KEY `major_idx` (`majorID`),
  KEY `address_idx` (`addressID`),
  KEY `FK69E4F6C8A2183FAE` (`majorID`),
  KEY `FK69E4F6C82E214DA` (`postID`),
  KEY `FK69E4F6C8127C9524` (`addressID`),
  CONSTRAINT `FK69E4F6C8127C9524` FOREIGN KEY (`addressID`) REFERENCES `address` (`addressID`),
  CONSTRAINT `FK69E4F6C82E214DA` FOREIGN KEY (`postID`) REFERENCES `post` (`postID`),
  CONSTRAINT `FK69E4F6C8A2183FAE` FOREIGN KEY (`majorID`) REFERENCES `major` (`majorID`),
  CONSTRAINT `address` FOREIGN KEY (`addressID`) REFERENCES `address` (`addressID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `major` FOREIGN KEY (`majorID`) REFERENCES `major` (`majorID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `post` FOREIGN KEY (`postID`) REFERENCES `post` (`postID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pushinfo`
--

LOCK TABLES `pushinfo` WRITE;
/*!40000 ALTER TABLE `pushinfo` DISABLE KEYS */;
/*!40000 ALTER TABLE `pushinfo` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2014-10-09 22:38:10
