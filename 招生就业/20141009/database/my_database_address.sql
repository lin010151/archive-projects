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
-- Table structure for table `address`
--

DROP TABLE IF EXISTS `address`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `address` (
  `addressID` varchar(32) NOT NULL,
  `addressName` varchar(40) NOT NULL,
  PRIMARY KEY (`addressID`),
  KEY `address_idx` (`addressID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `address`
--

LOCK TABLES `address` WRITE;
/*!40000 ALTER TABLE `address` DISABLE KEYS */;
INSERT INTO `address` VALUES ('457543291041907604b42ec192572706','广东省河源市龙川县'),('494edd4514bba3792666ad92312a1c32','江西省九江市'),('4c0d9e06ba20865544012f1a1ef2dd9d','广东省肇庆市端州区'),('5214b21c514b3407933175188a3ad87b','广东省惠州市博罗县'),('542bc7b5a755330db57a8ef47d76e485','广东省兴宁市'),('5e1b39236002c12c97fb4626d226c63b','广东省中山市'),('5efa8d63fd5aaf3cf645e728e9bb9e80','广东省阳江市江城区'),('751ff7ec2b61d3bcfe215b4e72657bbd','广东省惠州市惠城区'),('7c18479ebb591b69f3fb3bded5cc3ebe','广东省广州市白云区'),('a156b47ef31b5f0ca241b13ee463c2a5','广东省阳江市阳西县'),('a697d1ef9d75f52d797349871c50af92','广东省清远市清新区'),('bf5018ecff228e404c043436546ac745','广东省梅州市五华县'),('c09f4579c43e33c2dbe8fdea3bc9aa33','广东省广州市越秀区'),('c789d07d9d3d2c69a4a022c06d37cc86','广东省潮州市潮安县'),('d378f2598ab2bec403d1b03c562b8e24','广东省普宁市'),('e270e46720a8f7608eae5cf5e5d5e37b','广东省汕头市潮阳区'),('e4ef837bfbe2b3bd5e6db14e033e3204','广东省汕尾市海丰县'),('f6c1338fd9dde3fa2d0eaaca8abb042b','广东省河源市紫金县');
/*!40000 ALTER TABLE `address` ENABLE KEYS */;
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
