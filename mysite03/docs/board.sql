-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `webdb` DEFAULT CHARACTER SET utf8 ;
-- -----------------------------------------------------
-- Schema webdb
-- -----------------------------------------------------
USE `webdb` ;

-- -----------------------------------------------------
-- Table `webdb`.`site`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `webdb`.`site` ;

CREATE TABLE IF NOT EXISTS `webdb`.`site` (
  `title` VARCHAR(50) NOT NULL,
  `welcome` VARCHAR(200) NOT NULL,
  `profile` VARCHAR(200) NOT NULL,
  `description` TEXT NOT NULL,
  PRIMARY KEY (`title`))
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
