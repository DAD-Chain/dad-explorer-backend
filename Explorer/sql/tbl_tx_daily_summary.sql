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

 Date: 08/23/2019 15:56:30 PM
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `tbl_tx_daily_summary`
-- ----------------------------
DROP TABLE IF EXISTS `tbl_tx_daily_summary`;
CREATE TABLE `tbl_tx_daily_summary` (
  `time_day` int(11) NOT NULL COMMENT '统计的时间戳，对其到整天即凌晨: 00:00',
  `tx_count` int(11) DEFAULT '0' COMMENT '交易个数',
  PRIMARY KEY (`time_day`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

SET FOREIGN_KEY_CHECKS = 0;
