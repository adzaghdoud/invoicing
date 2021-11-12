CREATE TABLE `invoicing`.`suivi_import` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `date` DATETIME NOT NULL,
  `company` VARCHAR(45) NOT NULL,
  `nb_transaction_imported` INT NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


ALTER TABLE `invoicing`.`suivi_import` 
ADD COLUMN `state` VARCHAR(45) NULL AFTER `nb_transaction_imported`,
ADD COLUMN `comment` VARCHAR(45) NULL AFTER `state`;

ALTER TABLE `invoicing`.`suivi_import` 
CHANGE COLUMN `date` `date` VARCHAR(45) NOT NULL ;
ALTER TABLE `invoicing`.`suivi_import` 
ADD COLUMN `nb_credit` INT NULL AFTER `company`,
ADD COLUMN `nb_debit` INT NULL AFTER `nb_credit`;
ALTER TABLE `invoicing`.`suivi_import` 
CHANGE COLUMN `nb_credit` `nb_credit` INT NULL DEFAULT NULL AFTER `nb_transaction_imported`,
CHANGE COLUMN `nb_debit` `nb_debit` INT NULL DEFAULT NULL AFTER `nb_credit`;
ALTER TABLE `invoicing`.`suivi_import` 
ADD COLUMN `old_balance` DOUBLE NULL AFTER `total_debit`;
ALTER TABLE `invoicing`.`suivi_import` 
ADD COLUMN `old_balance` DOUBLE NULL AFTER `total_debit`;