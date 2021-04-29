package pda.server.Controller;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import pda.server.DAO.PaymentMapper;

@RestController
public class PaymentController {
	private PaymentMapper paymentMapper;
	
	public PaymentController(PaymentMapper paymentMapper) {
		this.paymentMapper = paymentMapper; 
	} 
	
	//단체의 회비 가격, 회비 이름 저장하기 
	@RequestMapping(path = "/payment/due-infos/", method = RequestMethod.PUT)
    public String select_due_infos(@RequestBody Map<String, Object> param)
    {
		String GID = (String)param.get("GID");
		int pay = (int)param.get("pay");
		String title = (String)param.get("title");
        paymentMapper.update_due_infos(GID, pay, title);
        return "성공";
    }
	
	//회비 가격, 회비 이름 불러오기 
	@RequestMapping(path = "/payment/due-infos/{GID}", method = RequestMethod.GET)
	public List select_due_infos(@PathVariable String GID)
	{
		return paymentMapper.select_due_infos(GID);
	}
	
	//결제 요청 정보 저장하기
	@RequestMapping(path = "/payment/request-infos", method = RequestMethod.POST)
	public String insert_request_infos(@RequestBody Map<String, Object> param)
	{
		String GID = (String) param.get("GID");
		String user_id = (String) param.get("user_id");
		String merchant_uid = (String) param.get("merchant_uid");
		String buyer_name = (String) param.get("buyer_name");
		String payment_name = (String) param.get("payment_name");
		int payment_price = (int) param.get("payment_price");
		paymentMapper.insert_request_infos(GID, user_id, merchant_uid, buyer_name, payment_name, payment_price);
		return "성공";
	}
	
	//결제 창 설정을 위한 결제 요청 정보 가져오기
	@RequestMapping(path = "/payment/request-infos/{user_id}", method = RequestMethod.GET)
	public List select_request_infos(@PathVariable String user_id)
	{
		return paymentMapper.select_due_infos(user_id);
	}
	
	//결제 진행하다 취소되거나, 결제 실패한 경우 결제 요청 정보에서 삭제
	@RequestMapping(path = "/payment/request-infos/{user_id}", method = RequestMethod.DELETE)
	public String delete_request_infos(@PathVariable String user_id)
	{
		paymentMapper.delete_result_infos(user_id);
		return "성공";
	}
	
	//결제 성공 시 결과 정보 저장하기
	@RequestMapping(path = "/payment/results", method = RequestMethod.POST)
    public String insert_result_infos(@RequestBody Map<String, Object> param)
    {
		String user_id = (String)param.get("user_id");
		String imp_uid = (String)param.get("imp_uid");
		String merchant_uid = (String)param.get("merchant_uid");
		String time = (String)param.get("time");
        paymentMapper.insert_result_infos(user_id, imp_uid, merchant_uid, time);
        return "성공";
    }
	
	//결제 취소(환불)을 위한 결제 시간 정보 불러오기
	@RequestMapping(path = "/payment/results/{user_id}", method = RequestMethod.GET)
	public String select_result_time(@PathVariable String user_id)
	{
		return paymentMapper.select_result_time(user_id);
	}
	
	//해당 시간의 결제 취소(환불)을 위한 결제 결과 정보 불러오기
	@RequestMapping(path = "/payment/results/{user_id}/{time}", method = RequestMethod.GET)
	public List select_result_time(@PathVariable String user_id, @PathVariable String time)
	{
		return paymentMapper.select_result_infos(user_id, time);
	}
	
	//관리자가 초기에 단체 소개 관련 정보 저장하기
	@RequestMapping(path = "/groups/intros", method = RequestMethod.PUT)
    public String insert_group_intros(@RequestBody Map<String, Object> param)
    {
		String GID = (String)param.get("GID");
		String contents = (String)param.get("contents");
		String image_src = (String)param.get("image_src");
        paymentMapper.insert_group_intros(GID, contents, image_src);
        return "성공";
    }
	
	//단체 소개 관련 정보 불러오기 
	@RequestMapping(path = "/payment/groups/intros/{GID}", method = RequestMethod.GET)
	public List select_group_intros(@PathVariable String GID)
	{
		return paymentMapper.select_group_intros(GID);
	}
	
	//단체 소개 관련 정보 수정하기 (메인 스키마, 그룹 테이블)
	@RequestMapping(path = "/groups/intros", method = RequestMethod.PUT)
	public String update_group_intros(@RequestBody Map<String, Object> param)
	{
		String GID = (String)param.get("GID");
		String contents = (String)param.get("contents");
		String image_src = (String)param.get("image_src");
	    paymentMapper.update_group_intros(GID, contents, image_src);
	    return "성공";
	}
}
