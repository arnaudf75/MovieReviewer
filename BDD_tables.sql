-- MySQL Script generated by MySQL Workbench
-- 04/16/16 13:09:24
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema moviereviewer
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema moviereviewer
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `moviereviewer` DEFAULT CHARACTER SET utf8 ;
USE `moviereviewer` ;

-- -----------------------------------------------------
-- Table `moviereviewer`.`movie`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `moviereviewer`.`movie` (
  `idmovie` INT NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(45) NULL,
  `image_url` VARCHAR(100) NULL,
  `description` VARCHAR(1000) NULL,
  `date_release` DATE NULL,
  `genre` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`idmovie`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `moviereviewer`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `moviereviewer`.`user` (
  `iduser` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NULL,
  `first_name` VARCHAR(45) NULL,
  `pseudo` VARCHAR(45) NULL,
  `password` VARCHAR(45) NULL,
  `date_inscription` DATE NULL,
  PRIMARY KEY (`iduser`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `moviereviewer`.`comment`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `moviereviewer`.`comment` (
  `idcomment` INT NOT NULL AUTO_INCREMENT,
  `comment` VARCHAR(45) NULL,
  `iduser` INT NOT NULL,
  `idmovie` INT NOT NULL,
  PRIMARY KEY (`idcomment`),
  INDEX `fk_review_user1_idx` (`iduser` ASC),
  INDEX `fk_review_movie1_idx` (`idmovie` ASC),
  CONSTRAINT `fk_review_user1`
    FOREIGN KEY (`iduser`)
    REFERENCES `moviereviewer`.`user` (`iduser`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_review_movie1`
    FOREIGN KEY (`idmovie`)
    REFERENCES `moviereviewer`.`movie` (`idmovie`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `moviereviewer`.`person`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `moviereviewer`.`person` (
  `idperson` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NULL,
  `first_name` VARCHAR(45) NULL,
  `birthday` DATE NULL,
  PRIMARY KEY (`idperson`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `moviereviewer`.`casting`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `moviereviewer`.`casting` (
  `idmovie` INT NOT NULL,
  `idperson` INT NOT NULL,
  `role` VARCHAR(45) NULL,
  PRIMARY KEY (`idmovie`, `idperson`),
  INDEX `fk_movie_has_person_person1_idx` (`idperson` ASC),
  INDEX `fk_movie_has_person_movie1_idx` (`idmovie` ASC),
  CONSTRAINT `fk_movie_has_person_movie1`
    FOREIGN KEY (`idmovie`)
    REFERENCES `moviereviewer`.`movie` (`idmovie`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_movie_has_person_person1`
    FOREIGN KEY (`idperson`)
    REFERENCES `moviereviewer`.`person` (`idperson`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `moviereviewer`.`tags`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `moviereviewer`.`tags` (
  `idtag` INT NOT NULL AUTO_INCREMENT,
  `libelle` VARCHAR(45) NULL,
  PRIMARY KEY (`idtag`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `moviereviewer`.`movie_has_tags`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `moviereviewer`.`movie_has_tags` (
  `idmovie` INT NOT NULL,
  `idtag` INT NOT NULL,
  PRIMARY KEY (`idmovie`, `idtag`),
  INDEX `fk_movie_has_tags_tags1_idx` (`idtag` ASC),
  INDEX `fk_movie_has_tags_movie1_idx` (`idmovie` ASC),
  CONSTRAINT `fk_movie_has_tags_movie1`
    FOREIGN KEY (`idmovie`)
    REFERENCES `moviereviewer`.`movie` (`idmovie`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_movie_has_tags_tags1`
    FOREIGN KEY (`idtag`)
    REFERENCES `moviereviewer`.`tags` (`idtag`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `moviereviewer`.`review`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `moviereviewer`.`review` (
  `iduser` INT NOT NULL,
  `idmovie` INT NOT NULL,
  `rating` INT NULL,
  PRIMARY KEY (`iduser`, `idmovie`),
  INDEX `fk_user_has_movie_movie1_idx` (`idmovie` ASC),
  INDEX `fk_user_has_movie_user1_idx` (`iduser` ASC),
  CONSTRAINT `fk_user_has_movie_user1`
    FOREIGN KEY (`iduser`)
    REFERENCES `moviereviewer`.`user` (`iduser`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_user_has_movie_movie1`
    FOREIGN KEY (`idmovie`)
    REFERENCES `moviereviewer`.`movie` (`idmovie`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
