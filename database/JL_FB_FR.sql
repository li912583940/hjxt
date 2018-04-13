/*
Navicat MySQL Data Transfer

Source Server         : hj
Source Server Version : 50717
Source Host           : localhost:3306
Source Database       : telejy_hj

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2018-04-13 22:58:41
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `jl_fb_fr`
-- ----------------------------
DROP TABLE IF EXISTS `jl_fb_fr`;
CREATE TABLE `jl_fb_fr` (
  `WebID` int(11) NOT NULL AUTO_INCREMENT,
  `FR_No` varchar(100) NOT NULL,
  `FR_Name` varchar(200) DEFAULT NULL,
  `FR_Card` varchar(100) DEFAULT NULL,
  `JY` varchar(100) NOT NULL,
  `JQ` varchar(100) NOT NULL,
  `JB_No` varchar(50) NOT NULL,
  `JB_SetTime` int(11) DEFAULT NULL,
  `JB_SetType` int(11) DEFAULT NULL,
  `QQ_JB` int(11) NOT NULL,
  `QQ_Use` int(11) NOT NULL,
  `QQ_Left` int(11) NOT NULL,
  `QQ_YE` int(11) DEFAULT NULL,
  `QQ_ZH` varchar(100) DEFAULT NULL,
  `QQ_MM` varchar(100) DEFAULT NULL,
  `HJ_JB` int(11) NOT NULL,
  `HJ_Use` int(11) NOT NULL,
  `HJ_Left` int(11) NOT NULL,
  `HJ_Last_Time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `Monitor_Flag` varchar(100) NOT NULL,
  `State` int(11) NOT NULL,
  `OutTime` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `SP_State` int(11) NOT NULL,
  `SP_UserNo` varchar(50) DEFAULT NULL,
  `SP_Time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `SP_Info` varchar(500) DEFAULT NULL,
  `SP_Mon` text,
  `Info_RJSJ` varchar(100) DEFAULT NULL,
  `Info_JG` varchar(100) DEFAULT NULL,
  `Info_ZM` varchar(100) DEFAULT NULL,
  `Info_XQ` varchar(100) DEFAULT NULL,
  `Info_DQ` varchar(100) DEFAULT NULL,
  `Info_ZDZF` varchar(100) DEFAULT NULL,
  `info_home` varchar(100) DEFAULT NULL,
  `ZP` blob,
  `Former_JQ_Name` varchar(100) DEFAULT NULL,
  `FR_DAH` varchar(100) DEFAULT NULL,
  `State_ZDZF` int(11) NOT NULL,
  `ZDZF_Type` varchar(100) DEFAULT NULL,
  `FR_GJ` varchar(100) DEFAULT NULL,
  `Info_CSRQ` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`WebID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of jl_fb_fr
-- ----------------------------
