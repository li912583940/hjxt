/*
Navicat MySQL Data Transfer

Source Server         : hj
Source Server Version : 50717
Source Host           : localhost:3306
Source Database       : telejy_hj

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2018-04-13 21:43:03
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `hjdj_acd_info`
-- ----------------------------
DROP TABLE IF EXISTS `hjdj_acd_info`;
CREATE TABLE `hjdj_acd_info` (
  `ACDIndex` int(11) NOT NULL AUTO_INCREMENT,
  `ACDGetNo` int(11) NOT NULL,
  `ACDGetTime` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `ACDSetNo` int(11) NOT NULL,
  `ACDSetTime` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `CurDate` int(11) NOT NULL,
  `InitNo` int(11) NOT NULL,
  `UnitNo` int(11) NOT NULL,
  `Server_Name` varchar(100) NOT NULL,
  `ACDurl` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`ACDIndex`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of hjdj_acd_info
-- ----------------------------
