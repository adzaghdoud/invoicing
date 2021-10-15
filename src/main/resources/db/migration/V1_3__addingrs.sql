ALTER TABLE `invoicing`.`articles` 
ADD COLUMN `rs` VARCHAR(45) NULL AFTER `bywho`,
ADD INDEX `rs_idx` (`rs` ASC) VISIBLE;
;
ALTER TABLE `invoicing`.`articles` 
ADD CONSTRAINT `rs`
  FOREIGN KEY (`rs`)
  REFERENCES `invoicing`.`company` (`raison_sociale`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;
  ALTER TABLE `invoicing`.`clients` 
ADD COLUMN `ownedcompany` VARCHAR(45) NULL AFTER `rib`,
ADD INDEX `ownedcompany_idx` (`ownedcompany` ASC) VISIBLE;
;
ALTER TABLE `invoicing`.`clients` 
ADD CONSTRAINT `ownedcompany`
  FOREIGN KEY (`ownedcompany`)
  REFERENCES `invoicing`.`company` (`raison_sociale`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;
  
ALTER TABLE `invoicing`.`logins` 
ADD COLUMN `resetpassword` VARCHAR(3) NULL AFTER `avatar` DEFAULT 'NO' ;

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