/*
Navicat MySQL Data Transfer


Target Server Type    : MYSQL
Target Server Version : 50639
File Encoding         : 65001

Date: 2019-04-12 11:01:33
*/

SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for tbl_ont_txn_detail
-- ----------------------------
DROP TABLE IF EXISTS `tbl_address_txs`;
CREATE TABLE `tbl_address_txs`
(
    `address`              varchar(255)    NOT NULL DEFAULT '' COMMENT '交易关联的地址：from to',
    `tx_index`             bigint DEFAULT 0 COMMENT '交易排序',
    PRIMARY KEY (`address`, `tx_index`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;
