ALTER TABLE `invoicing`.`transactions` 
ADD COLUMN `proof_filename` VARCHAR(100) NULL AFTER `manual_validation`;