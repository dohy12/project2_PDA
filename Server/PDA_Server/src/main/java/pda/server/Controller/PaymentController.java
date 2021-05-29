package pda.server.Controller;

import org.springframework.util.MultiValueMap;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import pda.server.DAO.PaymentMapper;

@CrossOrigin(origins = "*") // 추가
@RestController
public class PaymentController {
	private PaymentMapper paymentMapper;
	
	public PaymentController(PaymentMapper paymentMapper) {
		this.paymentMapper = paymentMapper; 
	} 
	
	//1.단체의 회비 가격, 회비 이름 저장하기 (확인 완료)
	@RequestMapping(path = "/payments/due-infos", method = RequestMethod.PUT)
    public String select_due_infos(@RequestBody Map<String, Object> param)
    {
		String GroupId = (String)param.get("GroupId");
		int pay = (int)param.get("pay");
		String title = (String)param.get("title");
        paymentMapper.update_due_infos(GroupId, pay, title);
        return "성공";
    }
	
	//2.회비 가격, 회비 이름 불러오기 (확인 완료)
	@RequestMapping(path = "/payments/due-infos/{GroupId}", method = RequestMethod.GET)
	public Map<String, Object> select_due_infos(@PathVariable String GroupId)
	{
		return paymentMapper.select_due_infos(GroupId);
	}
	
	//3.결제 요청 정보 저장하기(확인 완료)
	@RequestMapping(path = "/payments/request-infos", method = RequestMethod.POST)
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
	

	//4.결제 창 설정을 위한 결제 요청 정보 가져오기 (확인 완료)
	@RequestMapping(path = "/payments/request-infos/{GroupId}", method = RequestMethod.GET)
	public Map<String, Object> select_request_infos(@PathVariable String GroupId, @RequestAttribute String U_ID)
	{
		return paymentMapper.select_request_infos(GroupId, Integer.parseInt(U_ID));
	}
	
	
	//5.결제 진행하다 취소되거나, 결제 실패한 경우 결제 요청 정보에서 삭제 (확인 완료)
	@RequestMapping(path = "/payments/request-infos/{GroupId}", method = RequestMethod.DELETE)
	public String delete_request_infos(@PathVariable String GroupId, @RequestAttribute String U_ID)
	{
		paymentMapper.delete_request_infos(GroupId, Integer.parseInt(U_ID));
		return "성공";
	}
	
	//6.결제 성공 시 결과 정보 저장하기 (확인 완료)
	@RequestMapping(path = "/payments/results", method = RequestMethod.POST)
    public String insert_result_infos(@RequestBody Map<String, Object> param, @RequestAttribute String U_ID)
    {
		String GroupId = (String)param.get("GroupId");
		String imp_uid = (String)param.get("imp_uid");
		String merchant_uid = (String)param.get("merchant_uid");
		String time = (String)param.get("time");
        paymentMapper.insert_result_infos(GroupId, Integer.parseInt(U_ID), imp_uid, merchant_uid, time);
        return "성공";
    }
	
	//7.결제 취소(환불)을 위한 결제 시간 정보 불러오기 (확인 완료)
	@RequestMapping(path = "/payments/results/{GroupId}", method = RequestMethod.GET)
	public List<String> select_result_time(@PathVariable String GroupId, @RequestAttribute String U_ID)
	{
		return paymentMapper.select_result_time(GroupId, Integer.parseInt(U_ID));
	}
	
	//8. 해당 시간의 결제 취소(환불)을 위한 결제 결과 정보 불러오기 (확인 완료)
	@RequestMapping(path = "/payments/results/{GroupId}/{time}", method = RequestMethod.GET)
	public Map<String, Object> select_result_infos(@PathVariable String GroupId, @PathVariable String time, @RequestAttribute String U_ID)
	{
		return paymentMapper.select_result_infos(GroupId, time, Integer.parseInt(U_ID));
	}

	
	//단체 소개 관련 정보 불러오기 (확인 완료)
	@RequestMapping(path = "/groups/intros/{GroupId}", method = RequestMethod.GET)
	public Map<String, Object> select_group_intros(@PathVariable String GroupId)
	{
		return paymentMapper.select_group_intros(GroupId);
	}
	
	//10.단체 소개 관련 정보 수정하기 (메인 스키마, 그룹 테이블) (확인 완료)
	@RequestMapping(path = "/groups/intros", method = RequestMethod.PUT)
	public String update_group_intros(@RequestBody Map<String, Object> param)
	{
		String GroupId = (String)param.get("GroupId");
		String contents = (String)param.get("contents");
		String image_src = (String)param.get("image_src");
	    paymentMapper.update_group_intros(GroupId, contents, image_src);
	    return "성공";
	}
	
	@RequestMapping(path = "/payment/success", method = RequestMethod.GET)
	public String payemnt_result_verification(@RequestParam(value="imp_uid")String imp_uid, @RequestParam(value="merchant_uid")String merchant_uid)
	{
		/*
		//결제 결과 검증 코드
		//post body
		Map<String, String> map = new HashMap<>();;
		map.put("imp_apikey", "9542528569364242"); //REST API 키
		map.put("imp_secret", "uMGHwm4vTuKsHy1kxlZ1xrfAmidjnymRk3zRpEmj86MQFOad99fGBugXNuhb3D2oGBf6fBqoIcfn1pAa"); //REST API Secret
		
		//post header
		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Type", "application/json");
		
		HttpEntity<Map<String, String>> request = new HttpEntity<>(map, headers);
		
		RestTemplate restTemplate = new RestTemplate();
		
		ResponseEntity<Map> responseEntity = restTemplate.postForObject("https://api.iamport.kr/users/getToken", map, Map.class);
		
		String getToken = */
	    return "성공" + "imp_uid: " + imp_uid + "merchant_uid: " + merchant_uid;
	} 
}
