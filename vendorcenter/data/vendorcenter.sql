/*
MySQL Data Transfer
Source Host: localhost
Source Database: ciims
Target Host: localhost
Target Database: ciims
Date: 2010-4-6 13:10:21
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for asset
-- ----------------------------
CREATE TABLE `asset` (
  `id` bigint(20) NOT NULL auto_increment,
  `name` varchar(200) character set gb2312 NOT NULL,
  `styleName` varchar(100) character set gb2312 NOT NULL,
  `parentId` bigint(20) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for assettype
-- ----------------------------
CREATE TABLE `assettype` (
  `ID` bigint(20) NOT NULL auto_increment,
  `name` varchar(255) character set gb2312 default NULL,
  `styleName` varchar(255) character set gb2312 default NULL,
  `parent_id` bigint(20) default NULL,
  PRIMARY KEY  (`ID`),
  KEY `FK4E65F64AAFAFDD6A` (`parent_id`),
  CONSTRAINT `FK4E65F64AAFAFDD6A` FOREIGN KEY (`parent_id`) REFERENCES `assettype` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for content
-- ----------------------------
CREATE TABLE `content` (
  `content_id` bigint(20) NOT NULL auto_increment,
  `name` varchar(255) character set gb2312 default NULL,
  `code` varchar(255) character set gb2312 default NULL,
  PRIMARY KEY  (`content_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for country
-- ----------------------------
CREATE TABLE `country` (
  `country_id` bigint(20) NOT NULL auto_increment,
  `name` varchar(255) character set gb2312 default NULL,
  `code` varchar(255) character set gb2312 default NULL,
  PRIMARY KEY  (`country_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for district
-- ----------------------------
CREATE TABLE `district` (
  `district_id` bigint(20) NOT NULL auto_increment,
  `name` varchar(255) character set gb2312 default NULL,
  `code` varchar(255) character set gb2312 default NULL,
  `province_id` bigint(20) default NULL,
  PRIMARY KEY  (`district_id`),
  KEY `FK1139338EDFC78DDD` (`province_id`),
  CONSTRAINT `FK1139338EDFC78DDD` FOREIGN KEY (`province_id`) REFERENCES `province` (`province_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for domain
-- ----------------------------
CREATE TABLE `domain` (
  `domain_id` bigint(20) NOT NULL auto_increment,
  `domain_name` varchar(255) character set gb2312 default NULL,
  PRIMARY KEY  (`domain_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for highschool
-- ----------------------------
CREATE TABLE `highschool` (
  `highschool_id` bigint(20) NOT NULL auto_increment,
  `name` varchar(255) character set gb2312 default NULL,
  `district_id` bigint(20) default NULL,
  PRIMARY KEY  (`highschool_id`),
  KEY `FK20E9F796EF75491D` (`district_id`),
  CONSTRAINT `FK20E9F796EF75491D` FOREIGN KEY (`district_id`) REFERENCES `district` (`district_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for province
-- ----------------------------
CREATE TABLE `province` (
  `province_id` bigint(20) NOT NULL auto_increment,
  `name` varchar(255) character set gb2312 default NULL,
  `code` varchar(255) character set gb2312 default NULL,
  `country_id` bigint(20) default NULL,
  PRIMARY KEY  (`province_id`),
  KEY `FKC5242B304B5EC777` (`country_id`),
  CONSTRAINT `FKC5242B304B5EC777` FOREIGN KEY (`country_id`) REFERENCES `country` (`country_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for resource
-- ----------------------------
CREATE TABLE `resource` (
  `resource_id` bigint(20) NOT NULL auto_increment,
  `name` varchar(255) character set gb2312 default NULL,
  `res_type` varchar(255) character set gb2312 default NULL,
  `res_string` varchar(255) character set gb2312 default NULL,
  `description` varchar(255) character set gb2312 default NULL,
  PRIMARY KEY  (`resource_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for resource_role
-- ----------------------------
CREATE TABLE `resource_role` (
  `resource_id` bigint(20) NOT NULL,
  `role_id` bigint(20) NOT NULL,
  KEY `FK3A62CE07FB83C99D` (`role_id`),
  KEY `FK3A62CE07C3E5791D` (`resource_id`),
  CONSTRAINT `FK3A62CE07C3E5791D` FOREIGN KEY (`resource_id`) REFERENCES `resource` (`resource_id`),
  CONSTRAINT `FK3A62CE07FB83C99D` FOREIGN KEY (`role_id`) REFERENCES `role` (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for role
-- ----------------------------
CREATE TABLE `role` (
  `role_id` bigint(20) NOT NULL auto_increment,
  `role_name` varchar(255) character set gb2312 default NULL,
  `descprition` varchar(255) character set gb2312 default NULL,
  PRIMARY KEY  (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tab
-- ----------------------------
CREATE TABLE `tab` (
  `tab_id` bigint(20) NOT NULL auto_increment,
  `tab_name` varchar(255) character set gb2312 default NULL,
  `sec_question` varchar(255) character set gb2312 default NULL,
  `sec_answer` varchar(255) character set gb2312 default NULL,
  `user_id` bigint(20) default NULL,
  PRIMARY KEY  (`tab_id`),
  KEY `FK1BF95A0AE8D7D` (`user_id`),
  CONSTRAINT `FK1BF95A0AE8D7D` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tab_widget
-- ----------------------------
CREATE TABLE `tab_widget` (
  `tab_id` bigint(20) NOT NULL,
  `widget_id` bigint(20) NOT NULL,
  PRIMARY KEY  (`tab_id`,`widget_id`),
  KEY `FK15DFD62E2007F390` (`tab_id`),
  KEY `FK15DFD62EF5FCA984` (`widget_id`),
  CONSTRAINT `FK15DFD62E2007F390` FOREIGN KEY (`tab_id`) REFERENCES `tab` (`tab_id`),
  CONSTRAINT `FK15DFD62EF5FCA984` FOREIGN KEY (`widget_id`) REFERENCES `widget` (`widget_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for university
-- ----------------------------
CREATE TABLE `university` (
  `university_id` bigint(20) NOT NULL auto_increment,
  `name` varchar(255) character set gb2312 default NULL,
  `province_id` bigint(20) default NULL,
  PRIMARY KEY  (`university_id`),
  KEY `FKB48EA8EDFC78DDD` (`province_id`),
  CONSTRAINT `FKB48EA8EDFC78DDD` FOREIGN KEY (`province_id`) REFERENCES `province` (`province_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for user
-- ----------------------------
CREATE TABLE `user` (
  `user_id` bigint(20) NOT NULL auto_increment,
  `email` varchar(255) character set gb2312 default NULL,
  `password` varchar(255) character set gb2312 default NULL,
  `status` int(11) default NULL,
  `real_name` varchar(255) character set gb2312 default NULL,
  `birthdate` datetime default NULL,
  `head_picture` varchar(255) default NULL,
  PRIMARY KEY  (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for user_contact_info
-- ----------------------------
CREATE TABLE `user_contact_info` (
  `contact_id` bigint(20) NOT NULL auto_increment,
  `workplace` varchar(255) character set gb2312 default NULL,
  `university` varchar(255) character set gb2312 default NULL,
  `high_school` varchar(255) character set gb2312 default NULL,
  `user_id` bigint(20) default NULL,
  PRIMARY KEY  (`contact_id`),
  KEY `FK943CD61A0AE8D7D` (`user_id`),
  CONSTRAINT `FK943CD61A0AE8D7D` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
CREATE TABLE `user_role` (
  `role_id` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  KEY `FK143BF46AFB83C99D` (`role_id`),
  KEY `FK143BF46AA0AE8D7D` (`user_id`),
  CONSTRAINT `FK143BF46AA0AE8D7D` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`),
  CONSTRAINT `FK143BF46AFB83C99D` FOREIGN KEY (`role_id`) REFERENCES `role` (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for widget
-- ----------------------------
CREATE TABLE `widget` (
  `widget_id` bigint(20) NOT NULL auto_increment,
  `widget_name` varchar(255) character set gb2312 default NULL,
  `author` varchar(255) character set gb2312 default NULL,
  `author_email` varchar(255) character set gb2312 default NULL,
  `descprition` varchar(255) character set gb2312 default NULL,
  `path` varchar(255) character set gb2312 default NULL,
  `widget_type` int(11) default NULL,
  `domain_id` bigint(20) default NULL,
  PRIMARY KEY  (`widget_id`),
  KEY `FKD1075A44162C9984` (`domain_id`),
  CONSTRAINT `FKD1075A44162C9984` FOREIGN KEY (`domain_id`) REFERENCES `domain` (`domain_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records 
-- ----------------------------
INSERT INTO `asset` VALUES ('1', '宇宙', 'folder', null);
INSERT INTO `asset` VALUES ('2', '银河系', 'folder', '1');
INSERT INTO `asset` VALUES ('3', '太阳系', 'folder', '2');
INSERT INTO `asset` VALUES ('4', '天马座', 'folder', '2');
INSERT INTO `asset` VALUES ('5', '木星', 'file', '3');
INSERT INTO `asset` VALUES ('6', '地球', 'file', '3');
INSERT INTO `assettype` VALUES ('1', '中国', 'folder', null);
INSERT INTO `assettype` VALUES ('2', '四川', 'folder', '1');
INSERT INTO `assettype` VALUES ('3', '广东', 'folder', '1');
INSERT INTO `assettype` VALUES ('4', '成都', 'folder', '2');
INSERT INTO `assettype` VALUES ('5', '深圳', 'folder', '3');
INSERT INTO `assettype` VALUES ('6', '武侯区', 'file', '4');
INSERT INTO `assettype` VALUES ('7', '宝安区', 'file', '5');
INSERT INTO `assettype` VALUES ('8', '高新区', 'file', '4');
INSERT INTO `content` VALUES ('1', '白龙', 'bailong');
INSERT INTO `content` VALUES ('2', 'bai', 'bai');
INSERT INTO `content` VALUES ('3', 'basd', 'basd');
INSERT INTO `content` VALUES ('4', 'abc', 'abc');
INSERT INTO `content` VALUES ('5', '邓杰', 'dengjie');
INSERT INTO `resource` VALUES ('4', 'login.jsp', 'url', '/login.jsp', 'login.jsp');
INSERT INTO `resource` VALUES ('5', '/user/login.do', 'url', '/user/login.do', '/user/login.do');
INSERT INTO `resource` VALUES ('6', '/associateSearch.do', 'url', '/associateSearch.do', '/associateSearch.do');
INSERT INTO `resource` VALUES ('8', 'index.jsp', 'url', '/index.jsp', 'index.jsp is not filter');
INSERT INTO `resource` VALUES ('9', 'admin.jsp', 'url', '/admin.jsp', 'admin.jsp granted admin');
INSERT INTO `resource` VALUES ('10', 'all url', 'url', '/**', 'all url granted user');
INSERT INTO `resource_role` VALUES ('4', '3');
INSERT INTO `resource_role` VALUES ('8', '4');
INSERT INTO `resource_role` VALUES ('9', '1');
INSERT INTO `resource_role` VALUES ('10', '3');
INSERT INTO `resource_role` VALUES ('5', '3');
INSERT INTO `resource_role` VALUES ('6', '1');
INSERT INTO `resource_role` VALUES ('6', '2');
INSERT INTO `role` VALUES ('1', 'ROLE_ADMIN', 'admin');
INSERT INTO `role` VALUES ('2', 'ROLE_USER', 'user');
INSERT INTO `role` VALUES ('3', 'IS_AUTHENTICATED_ANONYMOUSLY', 'IS_AUTHENTICATED_ANONYMOUSLY,用户不用登陆也能访问');
INSERT INTO `role` VALUES ('4', 'IS_AUTHENTICATED_FULLY', 'IS_AUTHENTICATED_FULLY,用户只要登陆就能访问，不管其权限是啥');
INSERT INTO `user` VALUES ('1', 'dengjie', 'e10adc3949ba59abbe56e057f20f883e', '1', 'sdf', '2010-02-22 10:21:27', null);
INSERT INTO `user` VALUES ('2', 'bailong', 'e10adc3949ba59abbe56e057f20f883e', '1', 'asdf', '2010-02-22 10:21:45', null);
INSERT INTO `user` VALUES ('3', 'dengjie200', '2f935deccd5ee6f5da8184f40ba7b39a', '1', 'sadf', null, null);
INSERT INTO `user` VALUES ('5', 'bailong200', '198702', '0', 'baibai', null, null);
INSERT INTO `user_role` VALUES ('1', '1');
INSERT INTO `user_role` VALUES ('2', '2');
