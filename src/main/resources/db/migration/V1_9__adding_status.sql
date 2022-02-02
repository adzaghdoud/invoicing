ALTER TABLE `invoicing`.`company` 
ADD COLUMN `status_file_name` VARCHAR(100) NULL AFTER `kbis_file_name`;
UPDATE `invoicing`.`company` SET `status_file_name` = 'StatutsZohratec.pdf' WHERE (`raison_sociale` = 'ZOHRATEC');