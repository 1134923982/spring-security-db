/*
Navicat MySQL Data Transfer

Source Server         : 1
Source Server Version : 50719
Source Host           : localhost:3306
Source Database       : spring_security

Target Server Type    : MYSQL
Target Server Version : 50719
File Encoding         : 65001

Date: 2020-04-18 15:26:10
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL,
  `username` varchar(32) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `enabled` tinyint(1) NOT NULL,
  `locked` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', 'root', '123', '1', '0');
INSERT INTO `user` VALUES ('2', 'admin', '123', '1', '0');
INSERT INTO `user` VALUES ('3', 'jayden', '123', '1', '0');
