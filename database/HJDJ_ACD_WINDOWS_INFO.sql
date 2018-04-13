/*
Navicat MySQL Data Transfer

Source Server         : hj
Source Server Version : 50717
Source Host           : localhost:3306
Source Database       : telejy_hj

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2018-04-13 21:43:19
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `hjdj_acd_windows_info`
-- ----------------------------
DROP TABLE IF EXISTS `hjdj_acd_windows_info`;
CREATE TABLE `hjdj_acd_windows_info` (
  `ACDIndex` int(11) NOT NULL AUTO_INCREMENT,
  `ACDName` varchar(100) NOT NULL,
  `ACDIP` varchar(50) NOT NULL,
  `ACDSetNo` int(11) NOT NULL,
  `ACDSetTime` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `Server_Name` varchar(100) NOT NULL,
  PRIMARY KEY (`ACDIndex`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of hjdj_acd_windows_info
-- ----------------------------
