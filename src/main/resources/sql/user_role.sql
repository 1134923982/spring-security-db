/*
Navicat MySQL Data Transfer

Source Server         : 1
Source Server Version : 50719
Source Host           : localhost:3306
Source Database       : spring_security

Target Server Type    : MYSQL
Target Server Version : 50719
File Encoding         : 65001

Date: 2020-04-18 15:26:19
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role` (
  `id` int(11) NOT NULL,
  `uid` int(11) DEFAULT NULL,
  `rid` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user_role
-- ----------------------------
INSERT INTO `user_role` VALUES ('1', '1', '1');
INSERT INTO `user_role` VALUES ('2', '1', '2');
INSERT INTO `user_role` VALUES ('3', '2', '2');
INSERT INTO `user_role` VALUES ('4', '3', '3');
