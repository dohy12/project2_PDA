package pda.server.DAO;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import javax.xml.validation.Schema;

@Mapper
public interface GroupGeneration
{
    @Insert("insert into main.GroupNameMapping(Name, GroupId) VALUES (#{GroupName} , #{GroupId})")
    public void idGeneration(@Param("GroupName") String GroupName, @Param("GroupId") String GroupId);

    @Select("select COUNT(*) from main.GroupNameMapping where Name = #{GroupName}")
    public int checkDuplicate(@Param("GroupName") String GroupName);

    @Select("CREATE DATABASE ${GroupId}; " +
            "USE ${GroupId};" +
            "CREATE TABLE IF NOT EXISTS ${GroupId}.`user` (" +
             "  `U_ID` INT NOT NULL AUTO_INCREMENT," +
             "  `ID` VARCHAR(45) NOT NULL," +
             "  `isAdmin` TINYINT NULL DEFAULT NULL," +
             "   JoinTime timestamp default null," +
             "   UserTableNum TINYINT NOT NULL," +
             "  `introduction` VARCHAR(2048) NULL DEFAULT NULL," +
             "  PRIMARY KEY (`U_ID`))" +
            "\n" +
             "ENGINE = InnoDB" +
            "\n" +
             "DEFAULT CHARACTER SET = utf8mb4" +
            "\n" +
             "COLLATE = utf8mb4_0900_ai_ci;"+
            "CREATE TABLE IF NOT EXISTS ${GroupId}.`board` (" +
            "\n" + "  `B_ID` INT NOT NULL AUTO_INCREMENT," +
            "\n" + "  `isNotice` TINYINT NULL DEFAULT NULL," +
            "\n" + "  `title` VARCHAR(45) NOT NULL," +
            "\n" + "  `date` DATETIME NULL DEFAULT NULL," +
            "\n" + "  `contents` LONGTEXT NULL DEFAULT NULL," +
            "\n" + "  `U_ID` INT NOT NULL," +
            "\n" + "  PRIMARY KEY (`B_ID`)," +
            "\n" + "  INDEX `fk_board_user1_idx` (`U_ID` ASC) VISIBLE," +
            "\n" + "  CONSTRAINT `fk_board_user1`" +
            "\n" + "    FOREIGN KEY (`U_ID`)" +
            "\n" + "    REFERENCES ${GroupId}.`user` (`U_ID`)" +
            "\n" + "    ON DELETE NO ACTION" +
            "\n" + "    ON UPDATE NO ACTION)" +
            "\n" + "ENGINE = InnoDB" +
            "\n" + "DEFAULT CHARACTER SET = utf8mb4" +
            "\n" + "COLLATE = utf8mb4_0900_ai_ci;" +
            "\n"+"CREATE TABLE IF NOT EXISTS ${GroupId}.`board_survey` (" +
            "\n" + "  `S_ID` INT NOT NULL AUTO_INCREMENT," +
            "\n" + "  `title` VARCHAR(45) NOT NULL," +
            "\n" + "  `date_start` DATETIME NOT NULL," +
            "\n" + "  `date_end` DATETIME NOT NULL," +
            "\n" + "  `B_ID` INT NOT NULL," +
            "\n" + "  PRIMARY KEY (`S_ID`)," +
            "\n" + "  INDEX `fk_board_survey_board1_idx` (`B_ID` ASC) VISIBLE," +
            "\n" + "  CONSTRAINT `fk_board_survey_board1`" +
            "\n" + "    FOREIGN KEY (`B_ID`)" +
            "\n" + "    REFERENCES ${GroupId}.`board` (`B_ID`)" +
            "\n" + "    ON DELETE NO ACTION" +
            "\n" + "    ON UPDATE NO ACTION)" +
            "\n" + "ENGINE = InnoDB" +
            "\n" + "DEFAULT CHARACTER SET = utf8mb4" +
            "\n" + "COLLATE = utf8mb4_0900_ai_ci;"+"CREATE TABLE IF NOT EXISTS ${GroupId}.`b_survey_option` (" +
            "\n" + "  `O_ID` INT NOT NULL AUTO_INCREMENT," +
            "\n" + "  `contents` VARCHAR(45) NOT NULL," +
            "\n" + "  `S_ID` INT NOT NULL," +
            "\n" + "  PRIMARY KEY (`O_ID`)," +
            "\n" + "  INDEX `fk_b_survey_option_board_survey_idx` (`S_ID` ASC) VISIBLE," +
            "\n" + "  CONSTRAINT `fk_b_survey_option_board_survey`" +
            "\n" + "    FOREIGN KEY (`S_ID`)" +
            "\n" + "    REFERENCES ${GroupId}.`board_survey` (`S_ID`)" +
            "\n" + "    ON DELETE NO ACTION" +
            "\n" + "    ON UPDATE NO ACTION)" +
            "\n" + "ENGINE = InnoDB" +
            "\n" + "DEFAULT CHARACTER SET = utf8mb4" +
            "\n" + "COLLATE = utf8mb4_0900_ai_ci;"+"CREATE TABLE IF NOT EXISTS ${GroupId}.`b_survey_result` (" +
            "\n" + "  `O_ID` INT NOT NULL AUTO_INCREMENT," +
            "\n" + "  `voted` INT NULL DEFAULT '0'," +
            "\n" + "  INDEX `fk_b_survey_result_b_survey_option1_idx` (`O_ID` ASC) VISIBLE," +
            "\n" + "  PRIMARY KEY (`O_ID`)," +
            "\n" + "  CONSTRAINT `fk_b_survey_result_b_survey_option1`" +
            "\n" + "    FOREIGN KEY (`O_ID`)" +
            "\n" + "    REFERENCES ${GroupId}.`b_survey_option` (`O_ID`)" +
            "\n" + "    ON DELETE NO ACTION" +
            "\n" + "    ON UPDATE NO ACTION)" +
            "\n" + "ENGINE = InnoDB" +
            "\n" + "DEFAULT CHARACTER SET = utf8mb4" +
            "\n" + "COLLATE = utf8mb4_0900_ai_ci;"+"CREATE TABLE IF NOT EXISTS ${GroupId}.`board_comment` (" +
            "\n" + "  `C_ID` INT NOT NULL AUTO_INCREMENT," +
            "\n" + "  `Date` DATETIME NOT NULL," +
            "\n" + "  `contents` VARCHAR(2048) NOT NULL," +
            "\n" + "  `B_ID` INT NOT NULL," +
            "\n" + "  `U_ID` INT NOT NULL," +
            "\n" + "  `R_CID` INT NULL," +
            "\n" + "  PRIMARY KEY (`C_ID`)," +
            "\n" + "  INDEX `fk_board_comment_board1_idx` (`B_ID` ASC) VISIBLE," +
            "\n" + "  INDEX `fk_board_comment_user1_idx` (`U_ID` ASC) VISIBLE," +
            "\n" + "  INDEX `fk_board_comment_board_comment1_idx` (`R_CID` ASC) VISIBLE," +
            "\n" + "  CONSTRAINT `fk_board_comment_board1`" +
            "\n" + "    FOREIGN KEY (`B_ID`)" +
            "\n" + "    REFERENCES ${GroupId}.`board` (`B_ID`)" +
            "\n" + "    ON DELETE NO ACTION" +
            "\n" + "    ON UPDATE NO ACTION," +
            "\n" + "  CONSTRAINT `fk_board_comment_user1`" +
            "\n" + "    FOREIGN KEY (`U_ID`)" +
            "\n" + "    REFERENCES ${GroupId}.`user` (`U_ID`)" +
            "\n" + "    ON DELETE NO ACTION" +
            "\n" + "    ON UPDATE NO ACTION," +
            "\n" + "  CONSTRAINT `fk_board_comment_board_comment1`" +
            "\n" + "    FOREIGN KEY (`R_CID`)" +
            "\n" + "    REFERENCES ${GroupId}.`board_comment` (`C_ID`)" +
            "\n" + "    ON DELETE NO ACTION" +
            "\n" + "    ON UPDATE NO ACTION)" +
            "\n" + "ENGINE = InnoDB" +
            "\n" + "DEFAULT CHARACTER SET = utf8mb4" +
            "\n" + "COLLATE = utf8mb4_0900_ai_ci;"+"CREATE TABLE IF NOT EXISTS ${GroupId}.`schedule` (" +
            "\n" + "  `S_ID` INT NOT NULL AUTO_INCREMENT," +
            "\n" + "  `type` INT NOT NULL," +
            "\n" + "  `title` VARCHAR(128) NOT NULL," +
            "\n" + "  `content` VARCHAR(128) NULL DEFAULT NULL," +
            "\n" + "  `date` DATETIME NULL DEFAULT NULL," +
            "\n" + "  `U_ID` INT NOT NULL," +
            "\n" + "  PRIMARY KEY (`S_ID`)," +
            "\n" + "  INDEX `fk_calender_user1_idx` (`U_ID` ASC) VISIBLE," +
            "\n" + "  CONSTRAINT `fk_calender_user1`" +
            "\n" + "    FOREIGN KEY (`U_ID`)" +
            "\n" + "    REFERENCES ${GroupId}.`user` (`U_ID`)" +
            "\n" + "    ON DELETE NO ACTION" +
            "\n" + "    ON UPDATE NO ACTION)" +
            "\n" + "ENGINE = InnoDB" +
            "\n" + "DEFAULT CHARACTER SET = utf8mb4" +
            "\n" + "COLLATE = utf8mb4_0900_ai_ci;" +
            "\n"+"CREATE TABLE IF NOT EXISTS ${GroupId}.`message` (" +
            "\n" + "  `M_ID` INT NOT NULL AUTO_INCREMENT," +
            "\n" + "  `receive_UID` INT NOT NULL," +
            "\n" + "  `send_Date` DATETIME NOT NULL," +
            "\n" + "  `read_Date` DATETIME NULL DEFAULT NULL," +
            "\n" + "  `title` VARCHAR(128) NOT NULL," +
            "\n" + "  `contents` VARCHAR(2048) NULL DEFAULT NULL," +
            "\n" + "  `send_UID` INT NOT NULL," +
            "\n" + "  PRIMARY KEY (`M_ID`)," +
            "\n" + "  INDEX `fk_message_user1_idx` (`send_UID` ASC) VISIBLE," +
            "\n" + "  CONSTRAINT `fk_message_user1`" +
            "\n" + "    FOREIGN KEY (`send_UID`)" +
            "\n" + "    REFERENCES ${GroupId}.`user` (`U_ID`)" +
            "\n" + "    ON DELETE NO ACTION" +
            "\n" + "    ON UPDATE NO ACTION)" +
            "\n" + "ENGINE = InnoDB" +
            "\n" + "DEFAULT CHARACTER SET = utf8mb4" +
            "\n" + "COLLATE = utf8mb4_0900_ai_ci;"+"CREATE TABLE IF NOT EXISTS ${GroupId}.`payments` (" +
            "\n" + "  `P_ID` INT NOT NULL AUTO_INCREMENT," +
            "\n" + "  `start_date` DATETIME NOT NULL," +
            "\n" + "  `end_date` DATETIME NOT NULL," +
            "\n" + "  `pay` INT NOT NULL," +
            "\n" + "  `title` VARCHAR(45) NOT NULL," +
            "\n" + "  `contents` VARCHAR(128) NOT NULL," +
            "\n" + "  PRIMARY KEY (`P_ID`))" +
            "\n" + "ENGINE = InnoDB" +
            "\n" + "DEFAULT CHARACTER SET = utf8mb4" +
            "\n" + "COLLATE = utf8mb4_0900_ai_ci;"+"CREATE TABLE IF NOT EXISTS ${GroupId}.`payments_check` (" +
            "\n" + "  `P_ID` INT NOT NULL," +
            "\n" + "  `U_ID` INT NOT NULL," +
            "\n" + "  `payment` INT NULL DEFAULT NULL," +
            "\n" + "  INDEX `fk_payments_check_payments1_idx` (`P_ID` ASC) VISIBLE," +
            "\n" + "  INDEX `fk_payments_check_user1_idx` (`U_ID` ASC) VISIBLE," +
            "\n" + "  CONSTRAINT `fk_payments_check_payments1`" +
            "\n" + "    FOREIGN KEY (`P_ID`)" +
            "\n" + "    REFERENCES ${GroupId}.`payments` (`P_ID`)" +
            "\n" + "    ON DELETE NO ACTION" +
            "\n" + "    ON UPDATE NO ACTION," +
            "\n" + "  CONSTRAINT `fk_payments_check_user1`" +
            "\n" + "    FOREIGN KEY (`U_ID`)" +
            "\n" + "    REFERENCES ${GroupId}.`user` (`U_ID`)" +
            "\n" + "    ON DELETE NO ACTION" +
            "\n" + "    ON UPDATE NO ACTION)" +
            "\n" + "ENGINE = InnoDB" +
            "\n" + "DEFAULT CHARACTER SET = utf8mb4" +
            "\n" + "COLLATE = utf8mb4_0900_ai_ci;"+"CREATE TABLE IF NOT EXISTS ${GroupId}.`picture_list` (" +
            "\n" + "  `P_ID` INT NOT NULL AUTO_INCREMENT," +
            "\n" + "  `date` DATETIME NULL DEFAULT NULL," +
            "\n" + "  `location` VARCHAR(128) NULL DEFAULT NULL," +
            "\n" + "  `title` VARCHAR(128) NULL DEFAULT NULL," +
            "\n" + "  `participants` VARCHAR(128) NULL DEFAULT NULL," +
            "\n" + "  `U_ID` INT NOT NULL," +
            "\n" + "  PRIMARY KEY (`P_ID`)," +
            "\n" + "  INDEX `fk_picture_list_user1_idx` (`U_ID` ASC) VISIBLE," +
            "\n" + "  CONSTRAINT `fk_picture_list_user1`" +
            "\n" + "    FOREIGN KEY (`U_ID`)" +
            "\n" + "    REFERENCES ${GroupId}.`user` (`U_ID`)" +
            "\n" + "    ON DELETE NO ACTION" +
            "\n" + "    ON UPDATE NO ACTION)" +
            "\n" + "ENGINE = InnoDB" +
            "\n" + "DEFAULT CHARACTER SET = utf8mb4" +
            "\n" + "COLLATE = utf8mb4_0900_ai_ci;"+"CREATE TABLE IF NOT EXISTS ${GroupId}.`pictures` (" +
            "\n" + "  `I_ID` INT NOT NULL," +
            "\n" + "  `image_src` VARCHAR(45) NOT NULL," +
            "\n" + "  `P_ID` INT NOT NULL," +
            "\n" + "  PRIMARY KEY (`I_ID`)," +
            "\n" + "  INDEX `fk_pictures_picture_list1_idx` (`P_ID` ASC) VISIBLE," +
            "\n" + "  CONSTRAINT `fk_pictures_picture_list1`" +
            "\n" + "    FOREIGN KEY (`P_ID`)" +
            "\n" + "    REFERENCES ${GroupId}.`picture_list` (`P_ID`)" +
            "\n" + "    ON DELETE NO ACTION" +
            "\n" + "    ON UPDATE NO ACTION)" +
            "\n" + "ENGINE = InnoDB" +
            "\n" + "DEFAULT CHARACTER SET = utf8mb4" +
            "\n" + "COLLATE = utf8mb4_0900_ai_ci;" +
            "\n"+"CREATE TABLE IF NOT EXISTS ${GroupId}.`picture_comment` (" +
            "\n" + "  `C_ID` INT NOT NULL AUTO_INCREMENT," +
            "\n" + "  `date` DATETIME NOT NULL," +
            "\n" + "  `comment` VARCHAR(2048) NOT NULL," +
            "\n" + "  `pictures_P_ID` INT NOT NULL," +
            "\n" + "  `I_ID` INT NOT NULL," +
            "\n" + "  `U_ID` INT NOT NULL," +
            "\n" + "  PRIMARY KEY (`C_ID`)," +
            "\n" + "  INDEX `fk_picture_comment_pictures1_idx` (`I_ID` ASC) VISIBLE," +
            "\n" + "  INDEX `fk_picture_comment_user1_idx` (`U_ID` ASC) VISIBLE," +
            "\n" + "  CONSTRAINT `fk_picture_comment_pictures1`" +
            "\n" + "    FOREIGN KEY (`I_ID`)" +
            "\n" + "    REFERENCES ${GroupId}.`pictures` (`I_ID`)" +
            "\n" + "    ON DELETE NO ACTION" +
            "\n" + "    ON UPDATE NO ACTION," +
            "\n" + "  CONSTRAINT `fk_picture_comment_user1`" +
            "\n" + "    FOREIGN KEY (`U_ID`)" +
            "\n" + "    REFERENCES ${GroupId}.`user` (`U_ID`)" +
            "\n" + "    ON DELETE NO ACTION" +
            "\n" + "    ON UPDATE NO ACTION)" +
            "\n" + "ENGINE = InnoDB" +
            "\n" + "DEFAULT CHARACTER SET = utf8mb4" +
            "\n" + "COLLATE = utf8mb4_0900_ai_ci;" +
            "\n"+"CREATE TABLE IF NOT EXISTS ${GroupId}.`board_img` (" +
            "\n" + "  `I_ID` INT NOT NULL AUTO_INCREMENT," +
            "\n" + "  `image_src` VARCHAR(128) NOT NULL," +
            "\n" + "  `B_ID` INT NOT NULL," +
            "\n" + "  PRIMARY KEY (`I_ID`)," +
            "\n" + "  INDEX `fk_board_img_board_idx` (`B_ID` ASC) VISIBLE," +
            "\n" + "  CONSTRAINT `fk_board_img_board`" +
            "\n" + "    FOREIGN KEY (`B_ID`)" +
            "\n" + "    REFERENCES ${GroupId}.`board` (`B_ID`)" +
            "\n" + "    ON DELETE NO ACTION" +
            "\n" + "    ON UPDATE NO ACTION)" +
            "\n" + "ENGINE = InnoDB;" +"CREATE TABLE IF NOT EXISTS ${GroupId}.`intro_img` (" +
            "\n" + "  `I_ID` INT NOT NULL AUTO_INCREMENT," +
            "\n" + "  `image_src` VARCHAR(128) NOT NULL," +
            "\n" + "  `Intro_ID` INT NOT NULL," +
            "\n" + "  PRIMARY KEY (`I_ID`))" +
            "\n" + "ENGINE = InnoDB;"+"CREATE TABLE IF NOT EXISTS ${GroupId}.`introduction` (" +
            "\n" + "  `Intro_ID` INT NOT NULL AUTO_INCREMENT," +
            "\n" + "  `contents` LONGTEXT NOT NULL," +
            "\n" + "  PRIMARY KEY (`Intro_ID`))" +
            "\n" + "ENGINE = InnoDB;" +
            "USE main;"

    )
    public void schemaGeneration(@Param("GroupId") String GroupId);

    /***
     * 테스트 중 실행할 수 없는 구문을 발견했습니다.
     * 이 구문은 호출되지 않아야 합니다
     * 기록으로만 남아요
     */
    @Select("CREATE DATABASE 6420e4f565ad4b2a85dc704f45f3c3ed")
        public void errorSchemaGeneration();
}
