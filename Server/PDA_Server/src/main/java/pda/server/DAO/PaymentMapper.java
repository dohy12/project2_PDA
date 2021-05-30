package pda.server.DAO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.*;

@Mapper
public interface PaymentMapper {
	//단체의 회비 유효 기간, 회비가격, 회비이름, 내용 저장하기 (그룹 스키마, payments)
	@Update("INSERT INTO ${GroupId}.payments(start_date, end_date, pay, title, contents) VALUES ('${start_date}','${end_date}', ${pay}, '${title}', '${contents}')")
	public void insert_due_infos(@Param("GroupId") String GroupId, @Param("start_date") String start_date, @Param("end_date") String end_date, @Param("pay") int pay, @Param("title") String title, @Param("contents") String contents);
	
	//전체 회비 정보 가져오기 (그룹 스키마, payments)
	@Select("SELECT * FROM ${GroupId}.payments")
    public List<Map<String, Object>> select_due_infos(@Param("GroupId") String GroupId);
	
	//유저가 결제한 회비 정보 가져오기 (그룹 스키마, 그룹 테이블)
	@Select("SELECT * FROM ${GroupId}.payments WHERE P_ID IN (SELECT P_ID FROM ${GroupId}.payment_result WHERE U_ID=#{U_ID})")
	public List<Map<String, Object>> select_user_due_infos(@Param("GroupId") String GroupId, int U_ID);
	
	//결제 요청 정보 저장하기 (그룹스키마, 결제 요청 테이블)
	@Insert("INSERT INTO ${GroupId}.payment_request VALUES (#{U_ID}, ${P_ID}, '${merchant_uid}', '${buyer_name}')")
	public void insert_request_infos(@Param("GroupId") String GroupId, int U_ID, @Param("P_ID") int P_ID,
									 @Param("merchant_uid") String merchant_uid, @Param("buyer_name") String buyer_name);		
	
	
	//결제 요청 정보 불러오기 (그룹스키마, 결제 요청 테이블)
	@Select("SELECT * FROM ${GroupId}.payment_request WHERE U_ID=#{U_ID} AND P_ID=${P_ID}")
	public Map<String, Object> select_request_infos(@Param("GroupId") String GroupId, int U_ID, @Param("P_ID") int P_ID);
	
	//결제 요청 정보 삭제하기 (그룹 스키마, 결제 요청 테이블)
	@Delete("DELETE FROM ${GroupId}.payment_request WHERE U_ID=#{U_ID} AND P_ID=${P_ID}")
	public void delete_request_infos(@Param("GroupId") String GroupId, int U_ID, int P_ID);
	
	
	//결제 성공 시 결과 정보 저장하기 (그룹 스키마, 결제 결과 테이블)
	@Insert("INSERT INTO ${GroupId}.payment_result VALUES (${P_ID}, #{U_ID}, '${imp_uid}', '${merchant_uid}', '${time}')")
	public void insert_result_infos(@Param("GroupId") String GroupId, @Param("P_ID") int P_ID, int U_ID, @Param("imp_uid") String imp_uid, @Param("merchant_uid") String merchant_uid, @Param("time") String time);
		
	//결제 결과 정보 불러오기 (그룹 스키마, 결제 결과 테이블)
	@Select("SELECT * FROM ${GroupId}.payment_result WHERE P_ID=${P_ID} AND U_ID=#{U_ID}")
	public Map<String, Object> select_result_infos(@Param("GroupId") String GroupId, @Param("P_ID") int P_ID, int U_ID);
	
	//단체 소개 관련 정보 불러오기 (메인 스키마, 그룹 테이블)
	@Select("SELECT contents, image_src FROM main.groupinfo WHERE GroupId='${GroupId}'")
	public Map<String, Object> select_group_intros(@Param("GroupId") String GroupId);
	
	//단체 소개 관련 정보 수정하기 (메인 스키마, 그룹 테이블)
	@Update("UPDATE main.groupinfo SET contents='${contents}', image_src='${image_src}' WHERE GroupId='${GroupId}'")
	public void update_group_intros(@Param("GroupId") String GroupId, @Param("contents") String contents, @Param("image_src") String image_src);
}
