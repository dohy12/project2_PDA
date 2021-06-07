package pda.server.DAO;

import org.apache.ibatis.annotations.*;
import pda.server.DTO.JoinedSurvey;
import pda.server.DTO.Option;
import pda.server.DTO.Result;
import pda.server.DTO.Survey;

import java.util.List;

@Mapper
public interface SurveyOperation {
    //설문을 읽어오는 부분
    //하나의 survey 객체는 하나의 result 값 마다 생성된다.
    @Select("select S.S_ID, S.title, S.B_ID, O.O_ID, O.contents, R.voted from ${GroupId}.board_survey S Left Join (select * from ${GroupId}.b_survey_option) O On S.S_ID = O.S_ID Left Join (select * from ${GroupId}.b_survey_result) R On O.O_ID = R.O_ID where B_ID = ${BID}")
    public List<JoinedSurvey> surveyValue(@Param("GroupId") String GroupId, @Param("BID") int BID);

    //다음 auto_increment값 읽어오는 부분
    //새로운 설문 생성 시 사용
    @Select("select s_id from ${GroupId}.board_survey order by s_id desc limit 1")
    public int nextSID(@Param("GroupId") String GroupId);

    //이 게시글에 설문이 있는가??
    @Select("select count(*) as res from ${GroupId}.board_survey where b_id = ${BID}")
    public int isExist(@Param("GroupId") String GroupId, @Param("BID") int BID);

    //o_id의 다음 auto_increment값 읽어오는 부분
    //option과 result의 생성을 위해 필요
    @Select("select o_id from ${GroupId}.b_survey_option order by o_id desc limit 1")
    public int nextOID(@Param("GroupId") String GroupId);

    @Select("select last_insert_id()")
    public int next();

    //post 요청시 db에 survey 등록
    @Insert("insert into ${GroupId}.board_survey values(${Survey.S_ID}, '${Survey.title}', now(), now(), ${Survey.B_ID})")
    public void surveyPost(@Param("GroupId") String GroupId, @Param("Survey") Survey Survey);

    //option 등록
    @Insert("insert into ${GroupId}.b_survey_option values(${Option.O_ID}, '${Option.contents}', ${Option.S_ID})")
    public void optionPost(@Param("GroupId") String GroupId, @Param("Option") Option Option);

    //result 등록
    @Insert("insert into ${GroupId}.b_survey_result values(${Result.O_ID}, ${Result.voted})")
    public void resultPost(@Param("GroupId") String GroupId, @Param("Result") Result Result);

    //결과값 갱신
    @Update("update ${GroupId}.b_survey_result set voted = voted + 1 where o_id = ${OID}")
    public void updateResult(@Param("GroupId") String GroupId, @Param("OID") int OID);
}
