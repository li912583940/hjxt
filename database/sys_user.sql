/*
Navicat MySQL Data Transfer

Source Server         : hj
Source Server Version : 50717
Source Host           : localhost:3306
Source Database       : telejy_hj

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2018-04-03 21:11:03
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `sys_user`
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `webid` int(11) NOT NULL AUTO_INCREMENT,
  `user_no` varchar(50) NOT NULL,
  `user_pwd` varchar(50) NOT NULL,
  `user_depart` varchar(100) DEFAULT NULL,
  `user_name` varchar(100) DEFAULT NULL,
  `group_no` varchar(50) DEFAULT NULL,
  `is_super` int(1) DEFAULT NULL,
  `is_sp` int(1) DEFAULT NULL,
  `is_bj` int(1) DEFAULT NULL,
  PRIMARY KEY (`webid`,`user_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
