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
	
	//PID에 해당하는 회비 가격 가져오기 (그룹 스키마, payments)
	@Select("SELECT pay FROM ${GroupId}.payments WHERE P_ID=${P_ID}")
	public int select_pay(String GroupId, int P_ID);
	
	//PID에 해당하는 회비 가격, 회비 이름 가져오기 (그룹 스키마, payments)
	@Select("SELECT pay, title FROM ${GroupId}.payments WHERE P_ID=${P_ID}")
	public Map<String, Object> select_pay_title(String GroupId, int P_ID);
	
	//유저가 결제한 회비 P_ID 가져오기 (그룹 스키마, 그룹 테이블) - 상세 목록 보기 페이지 
	@Select("SELECT P_ID FROM ${GroupId}.payments WHERE P_ID IN (SELECT P_ID FROM ${GroupId}.payment_result WHERE U_ID=#{U_ID})")
	public List<Integer> select_user_due_infos(@Param("GroupId") String GroupId, int U_ID);
	
	//결제 결과 테이블에 있는 회비 정보들 가져오기
	@Select("SELECT pay, title, buyer_name, DATE_FORMAT(time, '%y%m%d') as time FROM ${GroupId}.payments, ${GroupId}.payment_request, ${GroupId}.payment_result "
			+ "WHERE ${GroupId}.payments.P_ID=${GroupId}.payment_result.P_ID AND ${GroupId}.payment_result.U_ID=${GroupId}.payment_request.U_ID")
	public List<Map<String, Object>> select_pay_infos(@Param("GroupId") String GroupId);
	
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
	
	@Delete("DELETE FROM ${GroupId}.intro_img")
	public void delete_intro_img(@Param("GroupId") String GroupId);
	
	@Insert("INSERT INTO ${GroupId}.intro_img(image_src, intro_ID) VALUES ('${image_src}', 1)")
	public void insert_intro_img(@Param("GroupId") String GroupId, @Param("image_src") String image_src);
	
	@Select("SELECT image_src FROM ${GroupId}.intro_img")
	public List<String> select_intro_img(@Param("GroupId") String GroupId);
	
	@Delete("DELETE FROM ${GroupId}.introduction")
	public void delete_intro_contents(@Param("GroupId") String GroupId);
	
	@Insert("INSERT INTO ${GroupId}.introduction(contents) VALUES ('${contents}')")
	public void insert_intro_contents(@Param("GroupId") String GroupId, @Param("contents") String contents);
	
	@Select("SELECT contents FROM ${GroupId}.introduction")
	public String select_intro_contents(@Param("GroupId") String GroupId);
}
