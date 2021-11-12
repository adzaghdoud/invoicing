ALTER TABLE `invoicing`.`suivi_import` 
ADD COLUMN `old_balance` DOUBLE NULL AFTER `total_debit`;
---------------------end-----------------------------