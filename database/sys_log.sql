/*
Navicat MySQL Data Transfer

Source Server         : hj
Source Server Version : 50717
Source Host           : localhost:3306
Source Database       : telejy_hj

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2018-04-07 15:00:09
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `sys_log`
-- ----------------------------
DROP TABLE IF EXISTS `sys_log`;
CREATE TABLE `sys_log` (
  `log_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `type` varchar(4) DEFAULT NULL,
  `model` varchar(50) DEFAULT NULL,
  `op` varchar(200) DEFAULT NULL,
  `info` text,
  `user_no` varchar(50) DEFAULT NULL,
  `user_name` varchar(100) DEFAULT NULL,
  `user_ip` varchar(50) DEFAULT NULL,
  `log_time` varchar(19) DEFAULT NULL,
  PRIMARY KEY (`log_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_log
-- ----------------------------
