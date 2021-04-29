package pda.server.DAO;

import java.util.List;

import org.apache.ibatis.annotations.*;

public interface PaymentMapper {
	//단체의 회비가격, 회비이름 저장하기 (메인 스키마, 그룹테이블)
	@Update("UPDATE main.group SET (pay, title) = (${pay}, ${title}) WHERE GID=${GID}")
	public void update_due_infos(@Param("GID") String GID, @Param("pay") int pay, @Param("title") String title);
	
	//단체의 회비가격, 회비이름 불러오기 (메인 스키마, 그룹 테이블)
	@Select("SELECT pay, title FROM main.group where GID=${GID}")
    public List select_due_infos(@Param("GID") String GID);
	
	
	//결제 요청 정보 저장하기 (그룹스키마, 결제 요청 테이블)
	@Insert("INSERT INTO ${GID}.paymentRequest VALUES (${user_id}, ${merchant_uid}, ${buyer_name}, ${payment_name}, ${payment_price})")
	public void insert_request_infos(@Param("GID") String GID, @Param("user_id") String user_id,
									 @Param("merchant_uid") String merchant_uid, @Param("buyer_name") String buyer_name,
									 @Param("payment_name") String payment_name, @Param("payment_price") int payment_price);		
	
	
	//결제 요청 정보 불러오기 (그룹스키마, 결제 요청 테이블)
	@Select("SELECT merchant_uid, buyer_name, payment_name, payment_price FROM ${GID}.paymentRequest WHERE user_id=${user_id}")
	public List select_request_infos(@Param("GID") String GID);
	
	//결제 요청 정보 삭제하기 (그룹 스키마, 결제 요청 테이블)
	@Delete("DELETE FROM paymentRequest WHERE user_id=${user_id}")
	public void delete_result_infos(@Param("user_id") String user_id);
	
	
	//결제 성공 시 결과 정보 저장하기 (그룹 스키마, 결제 결과 테이블)
	@Insert("INSERT INTO paymentResult VALUES (${user_id}, ${imp_uid}, ${merchant_uid}, ${time}")
	public void insert_result_infos(@Param("user_id") String user_id, @Param("imp_uid") String imp_uid, @Param("merchant_uid") String merchant_uid, @Param("time") String time);
	
	//결제 시간 정보 불러오기 (그룹 스키마, 결제 결과 테이블)
	@Select("SELECT time FROM paymentResult WHERE user_id = ${user_id}")
	public String select_result_time(@Param("user_id") String user_id);
	
	//해당 시간의 결제 결과 정보 불러오기 (그룹 스키마, 결제 결과 테이블)
	@Select("SELECT imp_uid, merchant_uid FROM paymentResult WHERE user_id=${user_id} AND time=${time}")
	public List select_result_infos(@Param("user_id") String user_id, @Param("time") String time);
	
	
	//단체 소개 관련 정보 저장하기 (메인 스키마, 그룹 테이블)
	@Update("UPDATE main.group SET (contents, image_src) = (${contents}, ${image_src}) WHERE GID=${GID}")
	public void insert_group_intros(@Param("contents") String contents, @Param("image_src") String image_src, @Param("GID") String GID);
	
	//단체 소개 관련 정보 불러오기 (메인 스키마, 그룹 테이블)
	@Select("SELECT group_name, contents, image_src FROM main.group WHERE GID=${GID}")
	public List select_group_intros(@Param("GID") String GID);
	
	//단체 소개 관련 정보 수정하기 (메인 스키마, 그룹 테이블)
	@Update("UPDATE main.group SET (contents, image_src) = (${contents}, ${image_src}) WHERE GID={GID}")
	public void update_group_intros(@Param("contents") String contents, @Param("image_src") String image_src, @Param("GID") String GID);
}
