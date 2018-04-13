/*
Navicat MySQL Data Transfer

Source Server         : hj
Source Server Version : 50717
Source Host           : localhost:3306
Source Database       : telejy_hj

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2018-04-13 21:43:14
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `hjdj_acd_show`
-- ----------------------------
DROP TABLE IF EXISTS `hjdj_acd_show`;
CREATE TABLE `hjdj_acd_show` (
  `ShowID` bigint(20) NOT NULL AUTO_INCREMENT,
  `ShowWindowsIndex` int(11) NOT NULL,
  `ShowTime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP,
  `ShowFlag` int(11) NOT NULL,
  `ShowText` varchar(500) NOT NULL,
  `ShowVoc` varchar(500) NOT NULL,
  PRIMARY KEY (`ShowID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of hjdj_acd_show
-- ----------------------------
