package pda.server.Controller;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestAttribute;
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
	@RequestMapping(path = "/payment/due-infos", method = RequestMethod.PUT)
    public String select_due_infos(@RequestBody Map<String, Object> param)
    {
		String GroupId = (String)param.get("GroupId");
		int pay = (int)param.get("pay");
		String title = (String)param.get("title");
        paymentMapper.update_due_infos(GroupId, pay, title);
        return "성공";
    }
	
	//회비 가격, 회비 이름 불러오기 
	@RequestMapping(path = "/payment/due-infos/{GroupId}", method = RequestMethod.GET)
	public Map<String, Object> select_due_infos(@PathVariable String GroupId)
	{
		return paymentMapper.select_due_infos(GroupId);
	}
	
	//결제 요청 정보 저장하기
	@RequestMapping(path = "/payment/request-infos", method = RequestMethod.POST)
	public String insert_request_infos(@RequestBody Map<String, Object> param, @RequestAttribute String U_ID)
	{
		String GroupId = (String) param.get("GroupId");
		String merchant_uid = (String) param.get("merchant_uid");
		String buyer_name = (String) param.get("buyer_name");
		String payment_name = (String) param.get("payment_name");
		int payment_price = (int) param.get("payment_price");
		paymentMapper.insert_request_infos(GroupId, Integer.parseInt(U_ID), merchant_uid, buyer_name, payment_name, payment_price);
		return "성공";
	}
	
	//결제 창 설정을 위한 결제 요청 정보 가져오기
	@RequestMapping(path = "/payment/request-infos/{GroupId}", method = RequestMethod.GET)
	public Map<String, Object> select_request_infos(@PathVariable String GroupId, @RequestAttribute String U_ID)
	{
		return paymentMapper.select_request_infos(GroupId, Integer.parseInt(U_ID));
	}
	
	//결제 진행하다 취소되거나, 결제 실패한 경우 결제 요청 정보에서 삭제
	@RequestMapping(path = "/payment/request-infos", method = RequestMethod.DELETE)
	public String delete_request_infos(@RequestAttribute String U_ID)
	{
		paymentMapper.delete_result_infos(Integer.parseInt(U_ID));
		return "성공";
	}
	
	//결제 성공 시 결과 정보 저장하기
	@RequestMapping(path = "/payment/results", method = RequestMethod.POST)
    public String insert_result_infos(@RequestBody Map<String, Object> param, @RequestAttribute String U_ID)
    {
		String imp_uid = (String)param.get("imp_uid");
		String merchant_uid = (String)param.get("merchant_uid");
		String time = (String)param.get("time");
        paymentMapper.insert_result_infos(Integer.parseInt(U_ID), imp_uid, merchant_uid, time);
        return "성공";
    }
	
	//결제 취소(환불)을 위한 결제 시간 정보 불러오기
	@RequestMapping(path = "/payment/results", method = RequestMethod.GET)
	public String select_result_time(@RequestAttribute String U_ID)
	{
		return paymentMapper.select_result_time(Integer.parseInt(U_ID));
	}
	
	//해당 시간의 결제 취소(환불)을 위한 결제 결과 정보 불러오기
	@RequestMapping(path = "/payment/results/{time}", method = RequestMethod.GET)
	public Map<String, Object> select_result_time(@RequestAttribute String U_ID, @PathVariable String time)
	{
		return paymentMapper.select_result_infos(Integer.parseInt(U_ID), time);
	}

	
	//단체 소개 관련 정보 불러오기 
	@RequestMapping(path = "/payment/groups/intros/{GroupId}", method = RequestMethod.GET)
	public Map<String, Object> select_group_intros(@PathVariable String GroupId)
	{
		return paymentMapper.select_group_intros(GroupId);
	}
	
	//단체 소개 관련 정보 수정하기 (메인 스키마, 그룹 테이블)
	@RequestMapping(path = "/groups/intros", method = RequestMethod.PUT)
	public String update_group_intros(@RequestBody Map<String, Object> param)
	{
		String GroupId = (String)param.get("GroupId");
		String contents = (String)param.get("contents");
		String image_src = (String)param.get("image_src");
	    paymentMapper.update_group_intros(GroupId, contents, image_src);
	    return "성공";
	}
}
