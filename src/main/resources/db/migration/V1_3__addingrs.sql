ALTER TABLE `invoicing`.`logins` 
ADD COLUMN `resetpassword` VARCHAR(3) DEFAULT 'NO' ;

ALTER TABLE `invoicing`.`prestations` 
ADD COLUMN `company` VARCHAR(45) NULL AFTER `date_paiement_attendue`,
ADD INDEX `company_idx` (`company` ASC) VISIBLE;
;
ALTER TABLE `invoicing`.`prestations` 
ADD CONSTRAINT `company`
  FOREIGN KEY (`company`)
  REFERENCES `invoicing`.`company` (`raison_sociale`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;
  ALTER TABLE `invoicing`.`clients` 
DROP COLUMN `rib`,
DROP INDEX `rib_UNIQUE` ;
;
ALTER TABLE `invoicing`.`clients` 
ADD COLUMN `numtva` VARCHAR(45) NULL AFTER `mail`;
ALTER TABLE `invoicing`.`company` 
ADD COLUMN `date_cloture_comptable` DATE NULL AFTER `email`;
UPDATE `invoicing`.`company` SET `date_cloture_comptable` = '2021-12-31' WHERE (`raison_sociale` = 'ZOHRATEC');
ALTER TABLE `invoicing`.`company` 
CHANGE COLUMN `date_cloture_comptable` `date_cloture_comptable` VARCHAR(10) NULL DEFAULT NULL ;