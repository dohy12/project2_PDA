package pda.server.DAO;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import pda.server.DTO.Survey;

import java.util.List;

@Mapper
public interface SurveyOperation {
    //설문을 읽어오는 부분
    //하나의 survey 객체는 하나의 result 값 마다 생성된다.
    @Select("select S.*, O.O_ID, O.contents, R.voted from ${GroupId}.board_survey S Left Join (select * from ${GroupId}.b_survey_option) O On S.S_ID = O.S_ID Left Join (select * from ${GroupID}.b_survey_result) R On O.O_ID = R.O_ID where B_ID = ${BID}")
    public List<Survey> surveyValue(@Param("GroupId") String GroupId, @Param("BID") int BID);


}
