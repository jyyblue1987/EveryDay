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

/*Table structure for table `comment` */

DROP TABLE IF EXISTS `comment`;

CREATE TABLE `comment` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userno` int(11) NOT NULL,
  `hno` int(11) NOT NULL,
  `content` text NOT NULL,
  `modifydate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

/*Data for the table `comment` */

/*Table structure for table `contacts` */

DROP TABLE IF EXISTS `contacts`;

CREATE TABLE `contacts` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userno` int(11) NOT NULL,
  `contactno` int(11) NOT NULL,
  `modifydate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=latin1;

/*Data for the table `contacts` */

insert  into `contacts`(`id`,`userno`,`contactno`,`modifydate`) values (12,18,22,'2016-02-18 17:44:14');

/*Table structure for table `favorite` */

DROP TABLE IF EXISTS `favorite`;

CREATE TABLE `favorite` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userno` int(11) NOT NULL,
  `hno` int(11) NOT NULL,
  `favorited` int(2) NOT NULL,
  `modifydate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `favorite` */

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

insert  into `hc_schedule`(`id`,`time`,`lastid`) values (12,'2016-02-19 21:29:36.051127',100),(13,'2016-02-19 21:29:38.563270',0),(14,'2016-02-19 21:29:45.484666',300);

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

/*Table structure for table `history` */

DROP TABLE IF EXISTS `history`;

CREATE TABLE `history` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userno` int(11) NOT NULL,
  `favonum` int(11) NOT NULL,
  `commentnum` int(11) NOT NULL,
  `modifydate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `history` */

/*Table structure for table `receivesupport` */

DROP TABLE IF EXISTS `receivesupport`;

CREATE TABLE `receivesupport` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userno` int(11) NOT NULL,
  `huserno` int(11) NOT NULL,
  `hno` int(11) NOT NULL,
  `amount` int(11) NOT NULL,
  `modifydate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `receivesupport` */

/*Table structure for table `sendsupport` */

DROP TABLE IF EXISTS `sendsupport`;

CREATE TABLE `sendsupport` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userno` int(11) NOT NULL,
  `huserno` int(11) NOT NULL,
  `hno` int(11) NOT NULL,
  `amount` int(11) NOT NULL,
  `modifydate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `sendsupport` */

/*Table structure for table `siteuser` */

DROP TABLE IF EXISTS `siteuser`;

CREATE TABLE `siteuser` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `email` char(30) COLLATE utf8_unicode_ci NOT NULL COMMENT 'email',
  `password` char(70) COLLATE utf8_unicode_ci NOT NULL COMMENT 'password',
  `name` char(50) COLLATE utf8_unicode_ci NOT NULL,
  `remember_token` char(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `siteuser` */

insert  into `siteuser`(`id`,`email`,`password`,`name`,`remember_token`) values (9,'admin@gmail.com','$2y$10$zEzZuUkfqRVYE.y.sBnGdew3wvPkf92COCNVSzGP2E9Rb7HAae8e.','/ggggg',NULL),(10,'admin@gmail.com','$2y$10$V4I3LJKYskD2sEXUaSFAQ.NmVemAktvZG6kFP1ZpJ7Vuw9hzSIR2O','',NULL);

/*Table structure for table `stage` */

DROP TABLE IF EXISTS `stage`;

CREATE TABLE `stage` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userno` int(11) NOT NULL,
  `hno` int(11) NOT NULL,
  `thumbnail` varchar(255) NOT NULL,
  `content` text NOT NULL,
  `modifydate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `stage` */

/*Table structure for table `temp_stage` */

DROP TABLE IF EXISTS `temp_stage`;

CREATE TABLE `temp_stage` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userno` int(11) NOT NULL,
  `hno` int(11) NOT NULL,
  `thumbnail` varchar(255) NOT NULL,
  `content` text NOT NULL,
  `modifydate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `temp_stage` */

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `fullname` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `username` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `password` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `email` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `phone` varchar(64) COLLATE utf8_unicode_ci NOT NULL,
  `thumbnail` varchar(250) COLLATE utf8_unicode_ci NOT NULL,
  `birthday` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `address` text COLLATE utf8_unicode_ci NOT NULL,
  `receivenum` int(11) NOT NULL,
  `sendnum` int(11) NOT NULL,
  `pointnum` int(11) NOT NULL,
  `friendnum` int(11) NOT NULL,
  `sortscore` float NOT NULL DEFAULT '0',
  `token` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `modifydate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `ofUser_cDate_idx` (`thumbnail`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `user` */

insert  into `user`(`id`,`fullname`,`username`,`password`,`email`,`phone`,`thumbnail`,`birthday`,`address`,`receivenum`,`sendnum`,`pointnum`,`friendnum`,`sortscore`,`token`,`modifydate`) values (18,'sinaya','sin','5ac0b315446fcbe4d488bf08c528f7dd1b8e4d9afec4def21b408eb30f1a03853','future12@gmail.com','123456','image.png','2014-2-12','qqq wewrwwrwrqweqweqeqwe',0,0,0,0,0,'TV1KrGd0xQDUofe1Jeus','2016-02-18 17:34:49'),(22,'jkh','jkh','c7fb24da6bb70c256c97b08ef134bcb93c0f0dae8b543a081e3601881daae4ac0','jkh@gmail.com','123456','image.png','2014-2-12','qqq wewrwwrwr',0,0,0,0,0,'aGvm6hsvCQ4xi4HTebdz','0000-00-00 00:00:00');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
