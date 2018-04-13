/*
Navicat MySQL Data Transfer

Source Server         : hj
Source Server Version : 50717
Source Host           : localhost:3306
Source Database       : telejy_hj

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2018-04-13 21:43:25
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `hjdj_face_info`
-- ----------------------------
DROP TABLE IF EXISTS `hjdj_face_info`;
CREATE TABLE `hjdj_face_info` (
  `FaceIndex` int(11) NOT NULL AUTO_INCREMENT,
  `EnrollFace_PC_IP` varchar(100) DEFAULT NULL,
  `EnrollFace_ID` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`FaceIndex`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of hjdj_face_info
-- ----------------------------
