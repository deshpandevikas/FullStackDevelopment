-- MySQL dump 10.13  Distrib 5.7.20, for Linux (x86_64)
--
-- Host: localhost    Database: smartcare
-- ------------------------------------------------------
-- Server version	5.7.20-0ubuntu0.16.04.1

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
-- Table structure for table `Studies`
--

DROP TABLE IF EXISTS `Studies`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Studies` (
  `sid` int(11) NOT NULL AUTO_INCREMENT,
  `sname` varchar(255) NOT NULL,
  `sdescription` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`sid`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Studies`
--

LOCK TABLES `Studies` WRITE;
/*!40000 ALTER TABLE `Studies` DISABLE KEYS */;
INSERT INTO `Studies` VALUES (2,'Study1','Study1 Desc');
/*!40000 ALTER TABLE `Studies` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `StudiesUsers`
--

DROP TABLE IF EXISTS `StudiesUsers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `StudiesUsers` (
  `sid` int(11) NOT NULL,
  `uemail` varchar(50) NOT NULL,
  PRIMARY KEY (`sid`,`uemail`),
  KEY `suser_ibfk_1` (`sid`),
  KEY `suser_ibfk_2` (`uemail`),
  CONSTRAINT `suser_ibfk_1` FOREIGN KEY (`sid`) REFERENCES `Studies` (`sid`),
  CONSTRAINT `suser_ibfk_2` FOREIGN KEY (`uemail`) REFERENCES `USERS` (`uemail`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `StudiesUsers`
--

LOCK TABLES `StudiesUsers` WRITE;
/*!40000 ALTER TABLE `StudiesUsers` DISABLE KEYS */;
INSERT INTO `StudiesUsers` VALUES (2,'test2@gmail.com'),(2,'test@gmail.com');
/*!40000 ALTER TABLE `StudiesUsers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `StudyCoordinator`
--

DROP TABLE IF EXISTS `StudyCoordinator`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `StudyCoordinator` (
  `semail` varchar(50) NOT NULL,
  `fname` varchar(50) DEFAULT NULL,
  `lname` varchar(50) DEFAULT NULL,
  `pwd` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`semail`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `StudyCoordinator`
--

LOCK TABLES `StudyCoordinator` WRITE;
/*!40000 ALTER TABLE `StudyCoordinator` DISABLE KEYS */;
INSERT INTO `StudyCoordinator` VALUES ('sc1@gmail.com','sc1','sc1','sc1');
/*!40000 ALTER TABLE `StudyCoordinator` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Survey`
--

DROP TABLE IF EXISTS `Survey`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Survey` (
  `surveyid` int(11) NOT NULL AUTO_INCREMENT,
  `sid` int(11) NOT NULL,
  `surveyname` varchar(50) NOT NULL,
  `dtime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`surveyid`),
  KEY `Survey_ibfk_1` (`sid`),
  CONSTRAINT `Survey_ibfk_1` FOREIGN KEY (`sid`) REFERENCES `Studies` (`sid`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Survey`
--

LOCK TABLES `Survey` WRITE;
/*!40000 ALTER TABLE `Survey` DISABLE KEYS */;
INSERT INTO `Survey` VALUES (3,2,'survey23','2017-12-15 02:51:36'),(4,2,'Survey24','2017-12-15 03:51:24'),(5,2,'testSurvey5','2017-12-15 05:08:06'),(6,2,'testS','2017-12-15 05:10:28');
/*!40000 ALTER TABLE `Survey` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `USERS`
--

DROP TABLE IF EXISTS `USERS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `USERS` (
  `uemail` varchar(50) NOT NULL,
  `fname` varchar(50) DEFAULT NULL,
  `lname` varchar(50) DEFAULT NULL,
  `pwd` varchar(100) DEFAULT NULL,
  `age` int(10) DEFAULT NULL,
  PRIMARY KEY (`uemail`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `USERS`
--

LOCK TABLES `USERS` WRITE;
/*!40000 ALTER TABLE `USERS` DISABLE KEYS */;
INSERT INTO `USERS` VALUES ('test2@gmail.com','test2','test2','test2',22),('test@gmail.com','test','test','test',24);
/*!40000 ALTER TABLE `USERS` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `admin`
--

DROP TABLE IF EXISTS `admin`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `admin` (
  `username` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `admin`
--

LOCK TABLES `admin` WRITE;
/*!40000 ALTER TABLE `admin` DISABLE KEYS */;
INSERT INTO `admin` VALUES ('admin','admin');
/*!40000 ALTER TABLE `admin` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `gcmids`
--

DROP TABLE IF EXISTS `gcmids`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `gcmids` (
  `devid` varchar(500) NOT NULL,
  `uemail` varchar(50) NOT NULL,
  PRIMARY KEY (`uemail`,`devid`),
  CONSTRAINT `gcmids_ibfk_1` FOREIGN KEY (`uemail`) REFERENCES `USERS` (`uemail`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gcmids`
--

LOCK TABLES `gcmids` WRITE;
/*!40000 ALTER TABLE `gcmids` DISABLE KEYS */;
/*!40000 ALTER TABLE `gcmids` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `questions`
--

DROP TABLE IF EXISTS `questions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `questions` (
  `sid` int(11) NOT NULL,
  `qid` int(11) NOT NULL AUTO_INCREMENT,
  `question` varchar(1000) DEFAULT NULL,
  PRIMARY KEY (`qid`),
  KEY `qsid_1` (`sid`),
  CONSTRAINT `questions_ibfk_1` FOREIGN KEY (`sid`) REFERENCES `Studies` (`sid`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `questions`
--

LOCK TABLES `questions` WRITE;
/*!40000 ALTER TABLE `questions` DISABLE KEYS */;
INSERT INTO `questions` VALUES (2,4,'hellooooo..'),(2,5,'hey'),(2,6,'information12'),(2,7,'hikkkkkk'),(2,8,'hiiee'),(2,9,'heey23'),(2,10,'5445'),(2,11,'info12'),(2,12,'info22'),(2,13,'info23'),(2,14,'mcq ques'),(2,15,'test info 3'),(2,16,'test5 info'),(2,17,'info ttt'),(2,18,'mcq test9'),(2,19,'hello');
/*!40000 ALTER TABLE `questions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `questionsOptions`
--

DROP TABLE IF EXISTS `questionsOptions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `questionsOptions` (
  `qid` int(11) NOT NULL,
  `optionid` int(11) NOT NULL,
  `optionname` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`qid`,`optionid`),
  CONSTRAINT `questionsOptions_ibfk_1` FOREIGN KEY (`qid`) REFERENCES `questions` (`qid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `questionsOptions`
--

LOCK TABLES `questionsOptions` WRITE;
/*!40000 ALTER TABLE `questionsOptions` DISABLE KEYS */;
INSERT INTO `questionsOptions` VALUES (4,-1,'NA'),(5,1,'1'),(5,2,'0'),(5,3,'-1'),(6,-1,'NA'),(7,-1,'NA'),(8,1,'1'),(8,2,'2'),(9,1,'1'),(9,2,'2'),(10,-1,'NA'),(11,-1,'NA'),(12,-1,'NA'),(13,-1,'NA'),(14,1,'1'),(14,2,'2'),(15,-1,'NA'),(16,-1,'NA'),(17,-1,'NA'),(18,1,'yes'),(18,2,'no'),(19,-1,'NA');
/*!40000 ALTER TABLE `questionsOptions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `questionsResponse`
--

DROP TABLE IF EXISTS `questionsResponse`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `questionsResponse` (
  `qid` int(11) NOT NULL,
  `timeId` int(11) NOT NULL,
  `uemail` varchar(50) NOT NULL,
  `optionid` int(11) DEFAULT NULL,
  PRIMARY KEY (`qid`,`timeId`,`uemail`),
  KEY `timeId` (`timeId`),
  KEY `uemail` (`uemail`),
  CONSTRAINT `questionsResponse_ibfk_1` FOREIGN KEY (`qid`) REFERENCES `questions` (`qid`),
  CONSTRAINT `questionsResponse_ibfk_2` FOREIGN KEY (`timeId`) REFERENCES `questionsTimesPosted` (`timeId`),
  CONSTRAINT `questionsResponse_ibfk_3` FOREIGN KEY (`uemail`) REFERENCES `USERS` (`uemail`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `questionsResponse`
--

LOCK TABLES `questionsResponse` WRITE;
/*!40000 ALTER TABLE `questionsResponse` DISABLE KEYS */;
INSERT INTO `questionsResponse` VALUES (14,5,'test@gmail.com',2),(18,8,'test@gmail.com',2);
/*!40000 ALTER TABLE `questionsResponse` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `questionsTime`
--

DROP TABLE IF EXISTS `questionsTime`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `questionsTime` (
  `qid` int(11) NOT NULL,
  `qtimehh` int(11) NOT NULL,
  `qtimemm` int(11) NOT NULL,
  `isscheduled` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`qid`,`qtimehh`,`qtimemm`),
  CONSTRAINT `questionsTime_ibfk_1` FOREIGN KEY (`qid`) REFERENCES `questions` (`qid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `questionsTime`
--

LOCK TABLES `questionsTime` WRITE;
/*!40000 ALTER TABLE `questionsTime` DISABLE KEYS */;
INSERT INTO `questionsTime` VALUES (4,1,3,1),(5,1,4,1),(6,2,2,1),(7,2,10,1),(8,2,13,1),(9,2,0,1),(10,2,3,1),(11,2,23,1),(12,2,28,1),(13,2,32,1),(14,2,37,1),(15,3,0,1),(16,3,4,1),(17,3,45,1),(18,3,46,1),(19,5,19,1);
/*!40000 ALTER TABLE `questionsTime` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `questionsTimesPosted`
--

DROP TABLE IF EXISTS `questionsTimesPosted`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `questionsTimesPosted` (
  `qid` int(11) DEFAULT NULL,
  `timeId` int(11) NOT NULL AUTO_INCREMENT,
  `dtime` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`timeId`),
  KEY `qid` (`qid`),
  CONSTRAINT `questionsTimesPosted_ibfk_1` FOREIGN KEY (`qid`) REFERENCES `questions` (`qid`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `questionsTimesPosted`
--

LOCK TABLES `questionsTimesPosted` WRITE;
/*!40000 ALTER TABLE `questionsTimesPosted` DISABLE KEYS */;
INSERT INTO `questionsTimesPosted` VALUES (13,4,'2017-12-15 02:32:00'),(14,5,'2017-12-15 02:37:00'),(16,6,'2017-12-15 03:04:00'),(17,7,'2017-12-15 03:45:00'),(18,8,'2017-12-15 03:46:00'),(19,9,'2017-12-15 05:19:00');
/*!40000 ALTER TABLE `questionsTimesPosted` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `questionsUsers`
--

DROP TABLE IF EXISTS `questionsUsers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `questionsUsers` (
  `qid` int(11) NOT NULL,
  `uemail` varchar(50) NOT NULL,
  PRIMARY KEY (`qid`,`uemail`),
  KEY `uemail` (`uemail`),
  CONSTRAINT `questionsUsers_ibfk_1` FOREIGN KEY (`qid`) REFERENCES `questions` (`qid`),
  CONSTRAINT `questionsUsers_ibfk_2` FOREIGN KEY (`uemail`) REFERENCES `USERS` (`uemail`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `questionsUsers`
--

LOCK TABLES `questionsUsers` WRITE;
/*!40000 ALTER TABLE `questionsUsers` DISABLE KEYS */;
INSERT INTO `questionsUsers` VALUES (4,'test2@gmail.com'),(5,'test2@gmail.com'),(6,'test2@gmail.com'),(7,'test2@gmail.com'),(8,'test2@gmail.com'),(9,'test2@gmail.com'),(10,'test2@gmail.com'),(11,'test2@gmail.com'),(12,'test2@gmail.com'),(13,'test2@gmail.com'),(14,'test2@gmail.com'),(15,'test2@gmail.com'),(16,'test2@gmail.com'),(17,'test2@gmail.com'),(18,'test2@gmail.com'),(19,'test2@gmail.com'),(4,'test@gmail.com'),(5,'test@gmail.com'),(6,'test@gmail.com'),(7,'test@gmail.com'),(8,'test@gmail.com'),(9,'test@gmail.com'),(10,'test@gmail.com'),(11,'test@gmail.com'),(12,'test@gmail.com'),(13,'test@gmail.com'),(14,'test@gmail.com'),(15,'test@gmail.com'),(16,'test@gmail.com'),(17,'test@gmail.com'),(18,'test@gmail.com'),(19,'test@gmail.com');
/*!40000 ALTER TABLE `questionsUsers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `scheduler`
--

DROP TABLE IF EXISTS `scheduler`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `scheduler` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `hh` int(11) DEFAULT NULL,
  `mm` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `scheduler`
--

LOCK TABLES `scheduler` WRITE;
/*!40000 ALTER TABLE `scheduler` DISABLE KEYS */;
/*!40000 ALTER TABLE `scheduler` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `surveyquestions`
--

DROP TABLE IF EXISTS `surveyquestions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `surveyquestions` (
  `sid` int(11) NOT NULL,
  `surveyid` int(11) NOT NULL,
  `qid` int(11) NOT NULL AUTO_INCREMENT,
  `questionname` varchar(1000) DEFAULT NULL,
  `questiontype` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`qid`),
  KEY `survyeques_fk1` (`sid`),
  KEY `survyeques_fk2` (`surveyid`),
  CONSTRAINT `surveyquestions_ibfk_1` FOREIGN KEY (`sid`) REFERENCES `Studies` (`sid`),
  CONSTRAINT `surveyquestions_ibfk_2` FOREIGN KEY (`surveyid`) REFERENCES `Survey` (`surveyid`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `surveyquestions`
--

LOCK TABLES `surveyquestions` WRITE;
/*!40000 ALTER TABLE `surveyquestions` DISABLE KEYS */;
INSERT INTO `surveyquestions` VALUES (2,3,4,'question1Likert','mcq'),(2,3,5,'question2mc','mcq'),(2,3,6,'survey24','text'),(2,4,7,'question1','mcq'),(2,4,8,'Likert12','mcq'),(2,4,9,'Did you drink today','mcq'),(2,4,10,'how old are you?','text'),(2,5,11,'Rate your hungerness','mcq'),(2,5,12,'Had your breakfast','mcq'),(2,5,13,'at what time you had your dinner yesterday?','text'),(2,6,14,'tttt','mcq');
/*!40000 ALTER TABLE `surveyquestions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `surveyquestionsOptions`
--

DROP TABLE IF EXISTS `surveyquestionsOptions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `surveyquestionsOptions` (
  `qid` int(11) NOT NULL,
  `surveyid` int(11) NOT NULL,
  `optionid` int(11) NOT NULL,
  `optionname` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`qid`,`optionid`),
  KEY `surveyquestionsOptions_ibfk_2` (`surveyid`),
  CONSTRAINT `surveyquestionsOptions_ibfk_1` FOREIGN KEY (`qid`) REFERENCES `surveyquestions` (`qid`),
  CONSTRAINT `surveyquestionsOptions_ibfk_2` FOREIGN KEY (`surveyid`) REFERENCES `Survey` (`surveyid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `surveyquestionsOptions`
--

LOCK TABLES `surveyquestionsOptions` WRITE;
/*!40000 ALTER TABLE `surveyquestionsOptions` DISABLE KEYS */;
INSERT INTO `surveyquestionsOptions` VALUES (4,3,0,'1'),(4,3,1,'2'),(4,3,2,'3'),(4,3,3,'4'),(4,3,4,'5'),(4,3,5,'6'),(4,3,6,'7'),(4,3,7,'8'),(4,3,8,'9'),(4,3,9,'10'),(4,3,10,'11'),(5,3,1,'12'),(7,4,1,'12'),(7,4,2,'234'),(8,4,0,'1'),(8,4,1,'2'),(8,4,2,'3'),(8,4,3,'4'),(8,4,4,'5'),(8,4,5,'6'),(8,4,6,'7'),(8,4,7,'8'),(8,4,8,'9'),(8,4,9,'10'),(8,4,10,'11'),(9,4,1,'yes'),(9,4,2,'no'),(11,5,1,'0'),(11,5,2,'1'),(11,5,3,'2'),(11,5,4,'3'),(11,5,5,'4'),(11,5,6,'5'),(11,5,7,'6'),(11,5,8,'7'),(11,5,9,'8'),(11,5,10,'9'),(12,5,1,'yes'),(12,5,2,'no'),(14,6,1,'0'),(14,6,2,'1'),(14,6,3,'2'),(14,6,4,'3'),(14,6,5,'4'),(14,6,6,'5'),(14,6,7,'6'),(14,6,8,'7'),(14,6,9,'8'),(14,6,10,'9'),(14,6,11,'10');
/*!40000 ALTER TABLE `surveyquestionsOptions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `surveyquestionsResponse`
--

DROP TABLE IF EXISTS `surveyquestionsResponse`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `surveyquestionsResponse` (
  `qid` int(11) NOT NULL,
  `surveyid` int(11) NOT NULL,
  `responseText` varchar(100) DEFAULT NULL,
  `uemail` varchar(50) NOT NULL,
  `optionid` int(11) DEFAULT NULL,
  PRIMARY KEY (`qid`,`uemail`),
  KEY `uemail` (`uemail`),
  KEY `surveyquestionsResponse_ibfk_4` (`surveyid`),
  CONSTRAINT `surveyquestionsResponse_ibfk_1` FOREIGN KEY (`qid`) REFERENCES `surveyquestions` (`qid`),
  CONSTRAINT `surveyquestionsResponse_ibfk_3` FOREIGN KEY (`uemail`) REFERENCES `USERS` (`uemail`),
  CONSTRAINT `surveyquestionsResponse_ibfk_4` FOREIGN KEY (`surveyid`) REFERENCES `Survey` (`surveyid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `surveyquestionsResponse`
--

LOCK TABLES `surveyquestionsResponse` WRITE;
/*!40000 ALTER TABLE `surveyquestionsResponse` DISABLE KEYS */;
INSERT INTO `surveyquestionsResponse` VALUES (4,3,NULL,'test@gmail.com',7),(5,3,NULL,'test@gmail.com',4),(6,3,'ojg','test@gmail.com',NULL),(7,4,NULL,'test@gmail.com',1),(8,4,NULL,'test@gmail.com',9),(9,4,NULL,'test@gmail.com',2),(10,4,'44','test@gmail.com',NULL),(11,5,NULL,'test@gmail.com',10),(12,5,NULL,'test@gmail.com',1),(13,5,'1000','test@gmail.com',NULL),(14,6,NULL,'test@gmail.com',6);
/*!40000 ALTER TABLE `surveyquestionsResponse` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-12-15  5:25:56
