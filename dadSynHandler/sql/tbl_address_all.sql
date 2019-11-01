/*
 Navicat Premium Data Transfer

 Source Server         : local-docker
 Source Server Type    : MySQL
 Source Server Version : 50716
 Source Host           : 127.0.0.1
 Source Database       : explorer

 Target Server Type    : MySQL
 Target Server Version : 50716
 File Encoding         : utf-8

 Date: 08/23/2019 16:02:18 PM
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `tbl_address_all`
-- ----------------------------
DROP TABLE IF EXISTS `tbl_address_all`;
CREATE TABLE `tbl_address_all` (
  `address` varchar(64) NOT NULL COMMENT '钱包地址',
  `first_time` int(11) DEFAULT NULL COMMENT '地址第一次看到的时间, 需要对其到小时',
  PRIMARY KEY (`address`),
  KEY `index_time` (`first_time`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

SET FOREIGN_KEY_CHECKS = 1;
