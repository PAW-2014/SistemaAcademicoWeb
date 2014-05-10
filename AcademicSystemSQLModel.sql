SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL';

CREATE SCHEMA IF NOT EXISTS `AcademicSystem` DEFAULT CHARACTER SET utf8;

-- -----------------------------------------------------
-- Table `AcademicSystem`.`endereco`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `AcademicSystem`.`endereco` (
  `idEndereco` INT(11) NOT NULL AUTO_INCREMENT ,
  `logradouro` VARCHAR(45) NULL DEFAULT NULL ,
  `numero` INT(11) NULL DEFAULT NULL ,
  `bairro` VARCHAR(45) NULL DEFAULT NULL ,
  `cidade` VARCHAR(45) NULL DEFAULT NULL ,
  `uf` VARCHAR(2) NULL DEFAULT NULL ,
  `cep` VARCHAR(9) NULL DEFAULT NULL ,
  PRIMARY KEY (`idEndereco`) )
ENGINE = InnoDB
AUTO_INCREMENT = 17
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `AcademicSystem`.`professor`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `AcademicSystem`.`professor` (
  `idProfessor` INT(11) NOT NULL AUTO_INCREMENT ,
  `nome` VARCHAR(45) NULL DEFAULT NULL ,
  `senha` VARCHAR(20) NULL DEFAULT NULL ,
  `login` VARCHAR(15) NULL DEFAULT NULL ,
  `telefone` VARCHAR(14) NULL DEFAULT NULL ,
  `email` VARCHAR(45) NULL DEFAULT NULL ,
  `foto` BLOB NULL DEFAULT NULL ,
  `cpf` VARCHAR(15) NULL DEFAULT NULL ,
  `dataNascimento` DATE NULL DEFAULT NULL ,
  `rg` VARCHAR(15) NULL DEFAULT NULL ,
  `outrasInformacoes` TEXT NULL DEFAULT NULL ,
  `status` INT(1) NULL DEFAULT NULL ,
  `idEndereco` INT(11) NULL DEFAULT NULL ,
  `tipo` INT(1) NULL DEFAULT NULL ,
  PRIMARY KEY (`idProfessor`) ,
  INDEX `fk_Professor_Endereco1_idx` (`idEndereco` ASC) ,
  CONSTRAINT `fk_Professor_Endereco1`
    FOREIGN KEY (`idEndereco` )
    REFERENCES `AcademicSystem`.`endereco` (`idEndereco` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 18
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `AcademicSystem`.`coordenador`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `AcademicSystem`.`coordenador` (
  `idCoordenador` INT(11) NOT NULL ,
  PRIMARY KEY (`idCoordenador`) ,
  CONSTRAINT `fk_Coordenador_Professor`
    FOREIGN KEY (`idCoordenador` )
    REFERENCES `AcademicSystem`.`professor` (`idProfessor` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `AcademicSystem`.`curso`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `AcademicSystem`.`curso` (
  `idcurso` INT(11) NOT NULL AUTO_INCREMENT ,
  `nome` VARCHAR(45) NULL DEFAULT NULL ,
  PRIMARY KEY (`idcurso`) )
ENGINE = InnoDB
AUTO_INCREMENT = 5
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `AcademicSystem`.`diretor`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `AcademicSystem`.`diretor` (
  `idDiretor` INT(11) NOT NULL ,
  PRIMARY KEY (`idDiretor`) ,
  CONSTRAINT `fk_Diretor_Professor1`
    FOREIGN KEY (`idDiretor` )
    REFERENCES `AcademicSystem`.`professor` (`idProfessor` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `AcademicSystem`.`disciplina`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `AcademicSystem`.`disciplina` (
  `idDisciplina` INT(11) NOT NULL AUTO_INCREMENT ,
  `nome` VARCHAR(45) NOT NULL ,
  PRIMARY KEY (`idDisciplina`) )
ENGINE = InnoDB
AUTO_INCREMENT = 41
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `AcademicSystem`.`disciplinapreferencial`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `AcademicSystem`.`disciplinapreferencial` (
  `idDisciplinaPreferencial` INT(11) NOT NULL AUTO_INCREMENT ,
  `idDisciplina` INT(11) NOT NULL ,
  `idProfessor` INT(11) NOT NULL ,
  PRIMARY KEY (`idDisciplinaPreferencial`) ,
  INDEX `fk_DisciplinaPreferencial_Disciplina1_idx` (`idDisciplina` ASC) ,
  INDEX `fk_DisciplinaPreferencial_Professor1_idx` (`idProfessor` ASC) ,
  CONSTRAINT `fk_DisciplinaPreferencial_Disciplina1`
    FOREIGN KEY (`idDisciplina` )
    REFERENCES `AcademicSystem`.`disciplina` (`idDisciplina` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_DisciplinaPreferencial_Professor1`
    FOREIGN KEY (`idProfessor` )
    REFERENCES `AcademicSystem`.`professor` (`idProfessor` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 9
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `AcademicSystem`.`experienciaprofissional`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `AcademicSystem`.`experienciaprofissional` (
  `idExperienciaProfissional` INT(11) NOT NULL AUTO_INCREMENT ,
  `empresa` VARCHAR(45) NULL DEFAULT NULL ,
  `dataInicio` DATE NULL DEFAULT NULL ,
  `dataFim` DATE NULL DEFAULT NULL ,
  `funcao` VARCHAR(45) NULL DEFAULT NULL ,
  `idProfessor` INT(11) NOT NULL ,
  PRIMARY KEY (`idExperienciaProfissional`) ,
  INDEX `fk_ExperienciaProfissional_Professor1_idx` (`idProfessor` ASC) ,
  CONSTRAINT `fk_ExperienciaProfissional_Professor1`
    FOREIGN KEY (`idProfessor` )
    REFERENCES `AcademicSystem`.`professor` (`idProfessor` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 5
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `AcademicSystem`.`formacaoacademica`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `AcademicSystem`.`formacaoacademica` (
  `idFormacaoAcademica` INT(11) NOT NULL AUTO_INCREMENT ,
  `dataInicio` DATE NULL DEFAULT NULL ,
  `dataFim` DATE NULL DEFAULT NULL ,
  `nomeCurso` VARCHAR(45) NULL DEFAULT NULL ,
  `instituicao` VARCHAR(45) NULL DEFAULT NULL ,
  `idProfessor` INT(11) NOT NULL ,
  PRIMARY KEY (`idFormacaoAcademica`) ,
  INDEX `fk_FormacaoAcademica_Professor1_idx` (`idProfessor` ASC) ,
  CONSTRAINT `fk_FormacaoAcademica_Professor1`
    FOREIGN KEY (`idProfessor` )
    REFERENCES `AcademicSystem`.`professor` (`idProfessor` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 8
DEFAULT CHARACTER SET = utf8;



SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
