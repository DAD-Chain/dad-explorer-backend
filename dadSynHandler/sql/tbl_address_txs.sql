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



DELIMITER $$
DROP PROCEDURE IF EXISTS insert_address_tx $$
CREATE PROCEDURE insert_address_tx (in max_block_height BIGINT)
BEGIN
    DECLARE done BOOLEAN DEFAULT 0;

    DECLARE from_address2 VARCHAR(255);
    DECLARE to_address2 VARCHAR(255);
    DECLARE tx_index2 BIGINT DEFAULT 0;
    DECLARE tx_hash2 VARCHAR(255);


    DEClARE addressTxsAll
        CURSOR FOR
            select `from_address`,`to_address`,`tx_index`,`tx_hash` from tbl_tx_eventlog
		where block_height < max_block_height and from_address is not null order by tx_index asc;

    DEClARE continue handler for not found set done=1;

    SET done = 0;

    OPEN addressTxsAll;

    REPEAT
        FETCH addressTxsAll INTO from_address2, to_address2, tx_index2, tx_hash2;
        IF (from_address2 IS NOT NULL and not done) THEN
            INSERT INTO tbl_address_txs (`address`,`tx_index`) VALUES (from_address2, tx_index2);
            IF STRCMP(from_address2, to_address2) != 0 THEN
               INSERT INTO tbl_address_txs (`address`,`tx_index`) VALUES (to_address2, tx_index2);
            END IF;
        END IF;

    UNTIL done END REPEAT;

    CLOSE addressTxsAll;

END $$
DELIMITER ;