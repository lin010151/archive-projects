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
-- Temporary table structure for view `recruitinfo_view`
--

DROP TABLE IF EXISTS `recruitinfo_view`;
/*!50001 DROP VIEW IF EXISTS `recruitinfo_view`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE TABLE `recruitinfo_view` (
  `id` tinyint NOT NULL,
  `comname` tinyint NOT NULL,
  `releasetime` tinyint NOT NULL
) ENGINE=MyISAM */;
SET character_set_client = @saved_cs_client;

--
-- Temporary table structure for view `student_view`
--

DROP TABLE IF EXISTS `student_view`;
/*!50001 DROP VIEW IF EXISTS `student_view`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE TABLE `student_view` (
  `id` tinyint NOT NULL,
  `name` tinyint NOT NULL,
  `idcard` tinyint NOT NULL,
  `sex` tinyint NOT NULL,
  `major` tinyint NOT NULL,
  `address` tinyint NOT NULL,
  `political` tinyint NOT NULL,
  `email` tinyint NOT NULL
) ENGINE=MyISAM */;
SET character_set_client = @saved_cs_client;

--
-- Final view structure for view `recruitinfo_view`
--

/*!50001 DROP TABLE IF EXISTS `recruitinfo_view`*/;
/*!50001 DROP VIEW IF EXISTS `recruitinfo_view`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `recruitinfo_view` AS select `p`.`postID` AS `id`,`c`.`companyName` AS `comname`,`p`.`releaseTime` AS `releasetime` from (`company` `c` join `post` `p`) where (`c`.`companyID` = `p`.`companyID`) order by `p`.`releaseTime` desc */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `student_view`
--

/*!50001 DROP TABLE IF EXISTS `student_view`*/;
/*!50001 DROP VIEW IF EXISTS `student_view`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `student_view` AS select `s`.`stuID` AS `id`,`s`.`stuName` AS `name`,`s`.`stuIDCard` AS `idcard`,`s`.`stuSex` AS `sex`,`m`.`majorName` AS `major`,`a`.`addressName` AS `address`,`s`.`stuPolitical` AS `political`,`s`.`stuEmail` AS `email` from ((`student` `s` join `major` `m`) join `address` `a`) where ((`m`.`majorID` = `s`.`majorID`) and (`a`.`addressID` = `s`.`addressID`)) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2014-10-09 22:38:12
