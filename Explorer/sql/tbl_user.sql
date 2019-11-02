/*
 Navicat Premium Data Transfer

 Source Server         : 172.20.120.35
 Source Server Type    : MySQL
 Source Server Version : 50716
 Source Host           : 172.20.120.35
 Source Database       : explorer

 Target Server Type    : MySQL
 Target Server Version : 50716
 File Encoding         : utf-8

 Date: 11/07/2019 14:32:27 PM
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `tbl_user`
-- ----------------------------
DROP TABLE IF EXISTS `tbl_user`;
CREATE TABLE `tbl_user` (
  `address` char(64) NOT NULL,
  `last_faucet` bigint(20) NOT NULL DEFAULT '0' COMMENT '上次获取测试币的时间',
  PRIMARY KEY (`address`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

SET FOREIGN_KEY_CHECKS = 1;
