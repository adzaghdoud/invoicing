ALTER TABLE `invoicing`.`company` 
ADD COLUMN `email` VARCHAR(45) NULL AFTER `last_refresh_transaction`;
UPDATE `invoicing`.`company` SET `email` = 'adamzaghdoud1@gmail.com' WHERE (`raison_sociale` = 'ZOHRATEC');