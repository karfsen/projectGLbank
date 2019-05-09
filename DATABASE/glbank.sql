-- MySQL dump 10.13  Distrib 5.7.9, for Win64 (x86_64)
--
-- Host: localhost    Database: glbank
-- ------------------------------------------------------
-- Server version	5.7.9

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
-- Table structure for table `account`
--

DROP TABLE IF EXISTS `account`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `account` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `idc` int(11) NOT NULL,
  `AccNum` varchar(10) NOT NULL,
  `amount` double NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `idc` (`idc`)
) ENGINE=MyISAM AUTO_INCREMENT=17 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `account`
--

LOCK TABLES `account` WRITE;
/*!40000 ALTER TABLE `account` DISABLE KEYS */;
INSERT INTO `account` VALUES (14,14,'5686989801',0),(15,13,'6431252592',0),(16,15,'1916916540',15.09);
/*!40000 ALTER TABLE `account` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `card`
--

DROP TABLE IF EXISTS `card`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `card` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ida` int(11) NOT NULL,
  `PIN` varchar(4) NOT NULL,
  `expirem` int(2) NOT NULL,
  `expirey` int(2) NOT NULL,
  `Active` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `ida` (`ida`)
) ENGINE=MyISAM AUTO_INCREMENT=13 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `card`
--

LOCK TABLES `card` WRITE;
/*!40000 ALTER TABLE `card` DISABLE KEYS */;
INSERT INTO `card` VALUES (7,15,'9300',4,22,1),(8,15,'6713',4,22,1),(9,15,'4704',4,22,1),(10,14,'0000',4,22,1),(11,14,'1247',4,22,1),(12,16,'0749',4,22,1);
/*!40000 ALTER TABLE `card` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cardtrans`
--

DROP TABLE IF EXISTS `cardtrans`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cardtrans` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `idCard` int(11) NOT NULL,
  `TransAmount` double NOT NULL,
  `TransDate` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `idCard` (`idCard`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cardtrans`
--

LOCK TABLES `cardtrans` WRITE;
/*!40000 ALTER TABLE `cardtrans` DISABLE KEYS */;
/*!40000 ALTER TABLE `cardtrans` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `client`
--

DROP TABLE IF EXISTS `client`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `client` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `fname` varchar(15) NOT NULL,
  `lname` varchar(15) NOT NULL,
  `email` varchar(30) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=20 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `client`
--

LOCK TABLES `client` WRITE;
/*!40000 ALTER TABLE `client` DISABLE KEYS */;
INSERT INTO `client` VALUES (13,'lol','lol','kol'),(14,'Martin','Krendželák','martin.krendzelakk@gmail.com'),(15,'Tomaš','Pavlik','jozko@gmail.com'),(16,'','',''),(17,'testf','test','test@test.com'),(18,'','',''),(19,'','','');
/*!40000 ALTER TABLE `client` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `employee`
--

DROP TABLE IF EXISTS `employee`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `employee` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `fname` varchar(15) NOT NULL,
  `lname` varchar(15) NOT NULL,
  `position` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `position` (`position`)
) ENGINE=MyISAM AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `employee`
--

LOCK TABLES `employee` WRITE;
/*!40000 ALTER TABLE `employee` DISABLE KEYS */;
INSERT INTO `employee` VALUES (1,'Martin','Krendželák',2),(2,'Tomaš','Pavlik',1),(3,'Pavuk','xxx',1);
/*!40000 ALTER TABLE `employee` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `failcardlog`
--

DROP TABLE IF EXISTS `failcardlog`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `failcardlog` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `idCard` int(11) NOT NULL,
  `FailDate` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `idCard` (`idCard`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `failcardlog`
--

LOCK TABLES `failcardlog` WRITE;
/*!40000 ALTER TABLE `failcardlog` DISABLE KEYS */;
/*!40000 ALTER TABLE `failcardlog` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `loginclient`
--

DROP TABLE IF EXISTS `loginclient`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `loginclient` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `idc` int(11) NOT NULL,
  `login` varchar(15) NOT NULL,
  `password` varchar(32) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `idc` (`idc`)
) ENGINE=MyISAM AUTO_INCREMENT=11 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `loginclient`
--

LOCK TABLES `loginclient` WRITE;
/*!40000 ALTER TABLE `loginclient` DISABLE KEYS */;
INSERT INTO `loginclient` VALUES (4,13,'NVu3yf679F','XcPsta5H'),(5,14,'gQmNVW8cuk','a45ea4129ee58f3b49d7133731297e5a'),(6,15,'pYlkW7eSoo','36ba89294be5db7570b32035ae749c29'),(7,16,'4UOncN1tk2','03c769e6702b15af160f4a11b643e968'),(8,17,'fOmZ7','jkoDTHjP'),(9,18,'cgc6ezxa4G','50594f30b56ae575c33fa32b4474eb43'),(10,19,'z7gvEUDv7d','39497b94fa2c470490bc22ca8b1f7178');
/*!40000 ALTER TABLE `loginclient` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `loginemp`
--

DROP TABLE IF EXISTS `loginemp`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `loginemp` (
  `ide` varchar(15) NOT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `login` varchar(15) NOT NULL,
  `password` varchar(15) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `login` (`login`),
  UNIQUE KEY `ide` (`ide`),
  UNIQUE KEY `ide_2` (`ide`)
) ENGINE=MyISAM AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `loginemp`
--

LOCK TABLES `loginemp` WRITE;
/*!40000 ALTER TABLE `loginemp` DISABLE KEYS */;
INSERT INTO `loginemp` VALUES ('1',1,'karfaz','lul'),('15',2,'karfa','lul'),('2',3,'pavlikt','boss'),('3',4,'pavukXXX','chovampavuky123');
/*!40000 ALTER TABLE `loginemp` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `loginhistory`
--

DROP TABLE IF EXISTS `loginhistory`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `loginhistory` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `idl` int(11) NOT NULL,
  `LogDate` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `Success` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idl` (`idl`)
) ENGINE=MyISAM AUTO_INCREMENT=39 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `loginhistory`
--

LOCK TABLES `loginhistory` WRITE;
/*!40000 ALTER TABLE `loginhistory` DISABLE KEYS */;
INSERT INTO `loginhistory` VALUES (9,1,'0000-00-00 00:00:00',1),(10,1,'2019-04-27 23:23:28',1),(11,5,'2019-04-27 23:48:19',NULL),(12,5,'2019-04-27 23:48:21',1),(13,5,'2019-04-27 23:48:25',NULL),(14,5,'2019-04-27 23:48:29',1),(15,5,'2019-04-28 12:30:33',NULL),(16,5,'2019-04-28 12:30:53',1),(17,5,'2019-04-28 12:31:17',NULL),(18,5,'2019-04-28 12:31:19',1),(19,5,'2019-04-28 12:31:31',NULL),(20,5,'2019-04-28 12:31:35',1),(21,5,'2019-04-28 12:31:38',NULL),(22,5,'2019-04-28 12:31:43',1),(23,5,'2019-04-28 21:44:47',NULL),(24,5,'2019-04-28 21:46:19',1),(25,5,'2019-04-29 08:04:37',NULL),(26,5,'2019-04-29 08:04:45',1),(27,4,'2019-04-29 09:29:13',NULL),(28,4,'2019-04-29 09:29:14',1),(29,4,'2019-04-29 09:32:28',NULL),(30,4,'2019-04-29 09:32:30',1),(31,13,'2019-04-29 12:42:32',NULL),(32,13,'2019-04-29 12:42:33',1),(33,6,'2019-04-29 13:18:02',NULL),(34,6,'2019-04-29 13:18:03',1),(35,14,'2019-04-29 13:20:15',NULL),(36,14,'2019-04-29 13:20:16',1),(37,5,'2019-05-01 13:35:52',NULL),(38,5,'2019-05-01 13:35:55',1);
/*!40000 ALTER TABLE `loginhistory` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `positions`
--

DROP TABLE IF EXISTS `positions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `positions` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(15) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `positions`
--

LOCK TABLES `positions` WRITE;
/*!40000 ALTER TABLE `positions` DISABLE KEYS */;
INSERT INTO `positions` VALUES (1,'common'),(2,'boss');
/*!40000 ALTER TABLE `positions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `transaction`
--

DROP TABLE IF EXISTS `transaction`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `transaction` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `idAcc` varchar(10) NOT NULL,
  `RecAccount` varchar(10) NOT NULL,
  `idEmployee` int(11) DEFAULT NULL,
  `TransDate` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `TransAmount` double NOT NULL,
  PRIMARY KEY (`id`),
  KEY `idAcc` (`idAcc`),
  KEY `idEmployee` (`idEmployee`)
) ENGINE=MyISAM AUTO_INCREMENT=60 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `transaction`
--

LOCK TABLES `transaction` WRITE;
/*!40000 ALTER TABLE `transaction` DISABLE KEYS */;
INSERT INTO `transaction` VALUES (1,'4','4',1,'0000-00-00 00:00:00',0),(2,'4','4',1,'0000-00-00 00:00:00',0),(3,'1','1',1,'0000-00-00 00:00:00',0),(4,'1','1',1,'0000-00-00 00:00:00',0),(5,'2','2',1,'0000-00-00 00:00:00',0),(6,'2','2',1,'0000-00-00 00:00:00',0),(7,'1','1',1,'0000-00-00 00:00:00',0),(8,'10','10',1,'0000-00-00 00:00:00',0),(9,'10','10',1,'0000-00-00 00:00:00',0),(10,'10','10',1,'0000-00-00 00:00:00',0),(11,'10','10',1,'0000-00-00 00:00:00',0),(12,'13','13',1,'0000-00-00 00:00:00',0),(13,'14','14',1,'2019-04-29 08:04:01',5),(14,'15','15',1,'2019-04-29 09:33:36',15.78),(15,'15','15',1,'2019-04-29 09:33:42',15.79),(16,'15','15',1,'2019-04-29 09:35:52',-158),(17,'15','15',1,'2019-04-29 09:36:09',180),(18,'16','16',1,'2019-04-29 13:17:42',15.56),(19,'16','16',1,'2019-04-29 13:17:50',-0.47),(20,'14','14',1,'2019-05-01 13:34:09',1587445479),(21,'14','14',1,'2019-05-01 13:34:28',-1587445479),(22,'14','14',1,'2019-05-01 13:34:35',-15874454),(23,'14','14',1,'2019-05-01 13:34:43',1587445479),(24,'14','14',1,'2019-05-01 13:34:54',158744547),(25,'14','14',1,'2019-05-01 13:35:03',-158744549),(26,'14','14',1,'2019-05-01 13:35:03',-158744549),(27,'14','14',1,'2019-05-01 13:35:03',-158744549),(28,'14','14',1,'2019-05-01 13:35:03',-158744549),(29,'14','14',1,'2019-05-01 13:35:04',-158744549),(30,'14','14',1,'2019-05-01 13:35:04',-158744549),(31,'14','14',1,'2019-05-01 13:35:04',-158744549),(32,'14','14',1,'2019-05-01 13:35:14',-10000000),(33,'14','14',1,'2019-05-01 13:35:20',-100000000),(34,'14','14',1,'2019-05-01 13:35:27',-1000000000),(35,'14','14',1,'2019-05-01 13:35:28',-1000000000),(36,'14','14',1,'2019-05-01 13:35:29',-1000000000),(37,'14','14',1,'2019-05-01 13:35:34',158744547),(38,'14','14',1,'2019-05-01 13:35:35',158744547),(39,'14','14',1,'2019-05-01 13:35:40',158744547),(40,'14','14',1,'2019-05-01 13:35:40',158744547),(41,'14','14',1,'2019-05-01 13:35:40',158744547),(42,'14','14',1,'2019-05-01 13:35:40',158744547),(43,'14','14',1,'2019-05-01 13:35:40',158744547),(44,'14','14',1,'2019-05-01 13:35:44',158744547),(45,'14','14',1,'2019-05-01 13:35:45',158744547),(46,'14','14',1,'2019-05-03 23:04:09',-15),(47,'14','14',1,'2019-05-03 23:07:02',-15),(48,'14','14',1,'2019-05-03 23:28:57',-15),(49,'14','14',1,'2019-05-03 23:30:10',-45),(50,'14','14',1,'2019-05-05 12:52:14',15),(51,'14','14',1,'2019-05-05 12:53:19',15),(52,'14','14',1,'2019-05-05 12:55:02',15),(53,'14','14',1,'2019-05-05 12:55:10',15),(54,'14','14',1,'2019-05-05 12:58:11',15),(55,'14','14',1,'2019-05-05 13:03:40',15),(56,'14','14',1,'2019-05-05 13:04:07',-60),(57,'14','14',1,'2019-05-05 13:04:14',-60),(58,'14','14',1,'2019-05-05 13:04:35',60),(59,'14','14',1,'2019-05-05 13:04:40',60);
/*!40000 ALTER TABLE `transaction` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-05-09 11:10:34
