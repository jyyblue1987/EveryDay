/*
SQLyog Ultimate v11.33 (64 bit)
MySQL - 10.1.9-MariaDB : Database - meitian
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`meitian` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `meitian`;

/*Table structure for table `advertisement` */

DROP TABLE IF EXISTS `advertisement`;

CREATE TABLE `advertisement` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(250) NOT NULL,
  `description` varchar(250) NOT NULL,
  `thumbpath` varchar(250) NOT NULL,
  `start` int(15) NOT NULL,
  `end` int(15) NOT NULL,
  `sequence` tinyint(3) NOT NULL,
  `published` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=78 DEFAULT CHARSET=latin1;

/*Data for the table `advertisement` */

insert  into `advertisement`(`id`,`title`,`description`,`thumbpath`,`start`,`end`,`sequence`,`published`) values (66,'1111','','ad_1454403086.jpg',1111,1111,127,'2016-02-02 16:51:28'),(67,'2222','','ad_1454403098.jpg',2222,2222,127,'2016-02-02 16:51:39'),(68,'3333','','ad_1454403107.jpg',3333,3333,127,'2016-02-02 16:51:48'),(69,'44444','','ad_1454403119.jpg',4444,444,127,'2016-02-02 16:52:00'),(70,'5555','','ad_1454403129.jpg',5555555,5555,127,'2016-02-02 16:52:10'),(71,'6666','','ad_1454403140.jpg',6666,6666,127,'2016-02-02 16:52:21'),(72,'7777','','ad_1454403147.jpg',7777,777,127,'2016-02-02 16:52:28'),(73,'8888','','ad_1454403156.jpg',8888,8888888,127,'2016-02-02 16:52:37'),(74,'999','','ad_1454403163.jpg',9999,9999,127,'2016-02-02 16:52:44'),(75,'aaaa','','ad_1454403171.jpg',0,0,0,'2016-02-02 16:52:52'),(76,'ssss','','ad_1454403178.jpg',0,0,0,'2016-02-02 16:52:59'),(77,'ddd','','ad_1454403185.jpg',0,0,0,'2016-02-02 16:53:06');

/*Table structure for table `hc_country` */

DROP TABLE IF EXISTS `hc_country`;

CREATE TABLE `hc_country` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(80) NOT NULL,
  `phone_prefix` varchar(5) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=231 DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC;

/*Data for the table `hc_country` */

insert  into `hc_country`(`id`,`name`,`phone_prefix`) values (12,'Bangladesh','880'),(13,'Belgium','32'),(14,'Burkina Faso','226'),(15,'Bulgaria','359'),(16,'Bosnia and Herzegovina','387'),(17,'Wallis and Futuna','681'),(18,'Saint Barthelemy','590'),(19,'Brunei','673'),(20,'Bolivia','591'),(21,'Bahrain','973'),(22,'Burundi','257'),(23,'Benin','229'),(24,'Bhutan','975'),(25,'Botswana','267'),(26,'Samoa','685'),(27,'Bonaire, Saint Eustatius and Saba ','599'),(28,'Brazil','55'),(29,'Belarus','375'),(30,'Belize','501'),(31,'Russia','7'),(32,'Rwanda','250'),(33,'Serbia','381'),(34,'East Timor','670'),(35,'Reunion','262'),(36,'Turkmenistan','993'),(37,'Tajikistan','992'),(38,'Romania','40'),(39,'Tokelau','690'),(40,'Guinea-Bissau','245'),(41,'Guatemala','502'),(42,'Greece','30'),(43,'Equatorial Guinea','240'),(44,'Guadeloupe','590'),(45,'Japan','81'),(46,'Guyana','592'),(47,'French Guiana','594'),(48,'Georgia','995'),(49,'United Kingdom','44'),(50,'Gabon','241'),(51,'El Salvador','503'),(52,'Guinea','224'),(53,'Gambia','220'),(54,'Greenland','299'),(55,'Gibraltar','350'),(56,'Ghana','233'),(57,'Oman','968'),(58,'Tunisia','216'),(59,'Jordan','962'),(60,'Croatia','385'),(61,'Haiti','509'),(62,'Hungary','36'),(63,'Hong Kong','852'),(64,'Honduras','504'),(65,'Heard Island and McDonald Islands',' '),(66,'Venezuela','58'),(67,'Palestinian Territory','970'),(68,'Palau','680'),(69,'Portugal','351'),(70,'Svalbard and Jan Mayen','47'),(71,'Paraguay','595'),(72,'Iraq','964'),(73,'Panama','507'),(74,'French Polynesia','689'),(75,'Papua New Guinea','675'),(76,'Peru','51'),(77,'Pakistan','92'),(78,'Philippines','63'),(79,'Pitcairn','870'),(80,'Poland','48'),(81,'Saint Pierre and Miquelon','508'),(82,'Zambia','260'),(83,'Western Sahara','212'),(84,'Estonia','372'),(85,'Egypt','20'),(86,'South Africa','27'),(87,'Ecuador','593'),(88,'Italy','39'),(89,'Vietnam','84'),(90,'Solomon Islands','677'),(91,'Ethiopia','251'),(92,'Somalia','252'),(93,'Zimbabwe','263'),(94,'Saudi Arabia','966'),(95,'Spain','34'),(96,'Eritrea','291'),(97,'Montenegro','382'),(98,'Moldova','373'),(99,'Madagascar','261'),(100,'Saint Martin','590'),(101,'Morocco','212'),(102,'Monaco','377'),(103,'Uzbekistan','998'),(104,'Myanmar','95'),(105,'Mali','223'),(106,'Macao','853'),(107,'Mongolia','976'),(108,'Marshall Islands','692'),(109,'Macedonia','389'),(110,'Mauritius','230'),(111,'Malta','356'),(112,'Malawi','265'),(113,'Maldives','960'),(114,'Martinique','596'),(115,'Mauritania','222'),(116,'Uganda','256'),(117,'Tanzania','255'),(118,'Malaysia','60'),(119,'Mexico','52'),(120,'Israel','972'),(121,'France','33'),(122,'British Indian Ocean Territory','246'),(123,'Saint Helena','290'),(124,'Finland','358'),(125,'Fiji','679'),(126,'Falkland Islands','500'),(127,'Micronesia','691'),(128,'Faroe Islands','298'),(129,'Nicaragua','505'),(130,'Netherlands','31'),(131,'Norway','47'),(132,'Namibia','264'),(133,'Vanuatu','678'),(134,'New Caledonia','687'),(135,'Niger','227'),(136,'Norfolk Island','672'),(137,'Nigeria','234'),(138,'New Zealand','64'),(139,'Nepal','977'),(140,'Nauru','674'),(141,'Niue','683'),(142,'Cook Islands','682'),(143,'Ivory Coast','225'),(144,'Switzerland','41'),(145,'Colombia','57'),(146,'China','86'),(147,'Cameroon','237'),(148,'Chile','56'),(149,'Cocos Islands','61'),(150,'Canada','1'),(151,'Republic of the Congo','242'),(152,'Central African Republic','236'),(153,'Democratic Republic of the Congo','243'),(154,'Czech Republic','420'),(155,'Cyprus','357'),(156,'Christmas Island','61'),(157,'Costa Rica','506'),(158,'Curacao','599'),(159,'Cape Verde','238'),(160,'Cuba','53'),(161,'Swaziland','268'),(162,'Syria','963'),(163,'Sint Maarten','599'),(164,'Kyrgyzstan','996'),(165,'Kenya','254'),(166,'South Sudan','211'),(167,'Suriname','597'),(168,'Kiribati','686'),(169,'Cambodia','855'),(170,'Comoros','269'),(171,'Sao Tome and Principe','239'),(172,'Slovakia','421'),(173,'South Korea','82'),(174,'Slovenia','386'),(175,'North Korea','850'),(176,'Kuwait','965'),(177,'Senegal','221'),(178,'San Marino','378'),(179,'Sierra Leone','232'),(180,'Seychelles','248'),(181,'Kazakhstan','7'),(182,'Singapore','65'),(183,'Sweden','46'),(184,'Sudan','249'),(185,'Djibouti','253'),(186,'Denmark','45'),(187,'Germany','49'),(188,'Yemen','967'),(189,'Algeria','213'),(190,'United States','1'),(191,'Uruguay','598'),(192,'Mayotte','262'),(193,'United States Minor Outlying Islands','1'),(194,'Lebanon','961'),(195,'Laos','856'),(196,'Tuvalu','688'),(197,'Taiwan','886'),(198,'Turkey','90'),(199,'Sri Lanka','94'),(200,'Liechtenstein','423'),(201,'Latvia','371'),(202,'Tonga','676'),(203,'Lithuania','370'),(204,'Luxembourg','352'),(205,'Liberia','231'),(206,'Lesotho','266'),(207,'Thailand','66'),(208,'Togo','228'),(209,'Chad','235'),(210,'Libya','218'),(211,'Vatican','379'),(212,'United Arab Emirates','971'),(213,'Andorra','376'),(214,'Afghanistan','93'),(215,'Iceland','354'),(216,'Iran','98'),(217,'Armenia','374'),(218,'Albania','355'),(219,'Angola','244'),(220,'Argentina','54'),(221,'Australia','61'),(222,'Austria','43'),(223,'Aruba','297'),(224,'India','91'),(225,'Azerbaijan','994'),(226,'Ireland','353'),(227,'Indonesia','62'),(228,'Ukraine','380'),(229,'Qatar','974'),(230,'Mozambique','258');

/*Table structure for table `hc_schedule` */

DROP TABLE IF EXISTS `hc_schedule`;

CREATE TABLE `hc_schedule` (
  `id` int(20) unsigned NOT NULL AUTO_INCREMENT,
  `time` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
  `lastid` int(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `hc_schedule` */

insert  into `hc_schedule`(`id`,`time`,`lastid`) values (2,'2016-01-15 10:54:12.204009',100),(3,'2016-01-15 10:52:18.821524',0),(4,'2016-01-15 10:54:15.706209',300);

/*Table structure for table `hc_user` */

DROP TABLE IF EXISTS `hc_user`;

CREATE TABLE `hc_user` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `username` varchar(20) NOT NULL,
  `vcode` varchar(6) NOT NULL,
  `expire` int(11) DEFAULT NULL COMMENT 'expire time',
  `active` int(1) DEFAULT '0' COMMENT '0: inactive, 1: active',
  `device` int(1) DEFAULT '1' COMMENT '1: android, 2 iOS',
  `pushkey` varchar(150) DEFAULT NULL,
  `token` varchar(30) DEFAULT NULL,
  `avastar` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=121 DEFAULT CHARSET=utf8;

/*Data for the table `hc_user` */

insert  into `hc_user`(`id`,`username`,`vcode`,`expire`,`active`,`device`,`pushkey`,`token`,`avastar`) values (92,'86_19265124','750400',1453093713,1,1,NULL,'UNWpeOPPdITVeWcV6umw','86_19265124_1453401898.jpg'),(93,'86_1234571','452598',1453093726,1,1,NULL,'LYJSNbpRJfoIgw9vCQFP','86_1234571_1453094332.jpg'),(99,'60_1913455851','554725',1453235884,0,1,NULL,NULL,NULL),(100,'86_1913210018','393084',1453236017,1,1,NULL,'zOGXbpXtKwVz89aYEJNS',NULL),(101,'86_18841568752','560693',1453252475,1,2,'f77f5b228c85621aceb295ff2de1fb9b13984887aeec7aa9c56d1adbef905eda','mpD1Wrri78zhIsBF6REH','86_18841568752_1453406344.jpg'),(102,'86_18841566891','494145',1453252565,1,2,'04061aab619cc7f81db2c53ac4c117bf951822da38fadf81cd78bc8fa9a2637e','snXcC9eZqooNI51lQFeP','86_18841566891_1453406260.jpg'),(110,'86_987654321','036057',1453313622,1,1,NULL,'gpc0lRt7v9chSxeq2HU3','86_987654321_1453313384.jpg'),(113,'86_18841570729','096514',1453315161,0,1,NULL,NULL,NULL),(116,'244_04515323','988182',1453316053,0,1,NULL,NULL,NULL),(117,'376_123123123','194476',1453316157,1,2,NULL,'kaEbVIoSfopjgNpPq0Bh',NULL),(118,'374_123123123','767957',1453316332,1,2,NULL,'Cho59oYiEcqEO1ehRIlK',NULL),(119,'297_123123123','562105',1453318981,1,2,NULL,'nQltGBvITVCEYppBoa5p',NULL),(120,'61_123','173463',1453323410,1,2,NULL,'cOsWkT6USbmDEFRR0NFL',NULL);

/*Table structure for table `ofuser` */

DROP TABLE IF EXISTS `ofuser`;

CREATE TABLE `ofuser` (
  `username` varchar(64) COLLATE utf8_unicode_ci NOT NULL,
  `plainPassword` varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL,
  `encryptedPassword` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `name` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `email` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `creationDate` char(15) COLLATE utf8_unicode_ci NOT NULL,
  `modificationDate` char(15) COLLATE utf8_unicode_ci NOT NULL,
  `storedKey` varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL,
  `serverKey` varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL,
  `salt` varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL,
  `iterations` int(11) DEFAULT NULL,
  PRIMARY KEY (`username`),
  KEY `ofUser_cDate_idx` (`creationDate`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `ofuser` */

insert  into `ofuser`(`username`,`plainPassword`,`encryptedPassword`,`name`,`email`,`creationDate`,`modificationDate`,`storedKey`,`serverKey`,`salt`,`iterations`) values ('297_123123123',NULL,'1b213c7eef1c8c00c4ba86686082e32ee4256567e708acfb52744af19e1788a7347574e8c83a35c6','psch','263@qq.com','001453318385057','001453318385057',NULL,NULL,NULL,NULL),('374_123123123',NULL,'411ffe33618791497c14757e4623976d3ee4188632cb34ab062bffbb5112d864574503ef7b5c0d8f','false','false','001453315735029','001453315735029',NULL,NULL,NULL,NULL),('61_123',NULL,'8e84e73a9d208cb0d697ad61fd711b1da9c69e3abcd66648','false','false','001453322814007','001453322814007',NULL,NULL,NULL,NULL),('86_1234571',NULL,'8c71e4ed53e879778050d09c94e305529220c65af0ab33daad114a939b99252f','test3','future.syg1118@gmail.com','001453093182498','001453093182498',NULL,NULL,NULL,NULL),('86_18841566891',NULL,'464f4893201e659714c483a8f6553b82785b6747905af8c62a894dfd377cf4f699905a3f5c18e06f','86_18841566891','kyn@email.com','001453622210685','001453622210685','ZRUTO5SGr/hn8EMtA4PSzYiUF2E=','A7ilgar2SVBW5OLVtHiSCbQ9pmg=','OarA0Tp/K8bJcvPFm4qWnqXvG514pISu',4096),('86_18841568752',NULL,'62ecee5fe02175e9b24e0f7d493814f30b911084ea924a314be4f9e483d7a13f210c8e1045145d2b','86_18841568752','ipad@Mann.com','001453251878919','001453251878919','TFSLWpUPj/BJ8uqZ+NJN+Hg8I/0=','E9TXD41yJa51CBfAO4AoEOhRsFk=','gZwh98g7plJaUAYIsTGn3ZT4zN0NcdUd',4096),('86_1913210018',NULL,'3e8603fe3c1eb986bd6507680377ae720690db66449541377051f55525a6043ad781f98608ece1c4','JKH','jkh@gmail.com','001453235420232','001453235420232','00TJ1XwrnECyMv2Rv3oLHQvDgdE=','4Nq+BhbcT0/Sf3tZpE+nHmsK5kI=','RHYWu43orXpfvVlPfW4rvhK7uRhMLTR5',4096),('86_19265124',NULL,'287afd8e4685881e759830debbf755803869547fa9cd15b20fc635dbf90fb1ec','test11','jkh@gmail.com','001453093118460','001453093118460','c4jpVOfO73yWIHg2LlqcIrCmzmg=','iW+98/jGN6pA65yJu69HCVgYZCM=','ZHIN9AuFOKUE2om2T9BDBTELbKukvDYx',4096),('86_987654321',NULL,'cf6553ffaf0ce53333d49ea9260506df5d5e4b25ab3fe33ea83163278edeaf22aec910d29444081e','final sin','future.syg1118@gmail.com','001453313034306','001453313034306','bNwF4hW45fYg1yiJSviPA8iBJec=','ThQCh0GmcIBu6tpc8TlhGoWxtC8=','IxgOg+RlQ64giY2pTbp4++q0pV7I+LYq',4096),('admin','admin',NULL,'Administrator','admin@example.com','0','0',NULL,NULL,NULL,NULL),('crazy',NULL,'65ea2700cf36ab943a73fa1a35fb8b3f','crazy','crazy@gmail.com','001453333226409','001453333226409',NULL,NULL,NULL,NULL),('jongkh',NULL,'df84f89823976fe8c427a9538df20330e310e718a2f1736f',NULL,NULL,'001453241389115','001453241389115',NULL,NULL,NULL,NULL),('sin',NULL,'a3537aa0e9a4f24d3a5468b41ba7cd4fa7fdb2ad75218ecb','sin','sin@gmail.com','001453340972462','001453340972462',NULL,NULL,NULL,NULL);

/*Table structure for table `siteuser` */

DROP TABLE IF EXISTS `siteuser`;

CREATE TABLE `siteuser` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `email` char(30) COLLATE utf8_unicode_ci NOT NULL COMMENT 'email',
  `password` char(70) COLLATE utf8_unicode_ci NOT NULL COMMENT 'password',
  `name` char(50) COLLATE utf8_unicode_ci NOT NULL,
  `remember_token` char(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `siteuser` */

insert  into `siteuser`(`id`,`email`,`password`,`name`,`remember_token`) values (9,'a@qq.com','$2y$10$P3EejnReH9hjtVXSqD1kzOEcI/xMbH5o3yWDstGD92KG58KRbc/LC','/ggggg',NULL);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
