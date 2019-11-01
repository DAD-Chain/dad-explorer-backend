alter table tbl_tx_eventlog add column tx_index bigint;
CREATE INDEX tx_index_index ON tbl_tx_eventlog (tbl_tx_eventlog);
update tbl_tx_eventlog set tx_index = block_height * 100000 + block_index;

DELIMITER $$
DROP PROCEDURE IF EXISTS update_tx_index $$
CREATE PROCEDURE update_tx_index ()
BEGIN
    DECLARE cur_index BIGINT DEFAULT 0;
    DECLARE curTxHash varchar(100);
    DECLARE done BOOLEAN DEFAULT 0;


    DEClARE txOrderByBlockIndex
        CURSOR FOR
            select tx_hash from tbl_tx_eventlog order by block_height * 100000 + block_index asc;
    declare continue handler for not found set done=1;

    set done = 0;
    OPEN txOrderByBlockIndex;

    REPEAT
        FETCH txOrderByBlockIndex INTO curTxHash;

	UPDATE tbl_tx_eventlog SET tx_index=cur_index WHERE tx_hash=curTxHash;
	SET cur_index = cur_index + 1;

    UNTIL done END REPEAT;

    CLOSE txOrderByBlockIndex;

END$$
DELIMITER ;