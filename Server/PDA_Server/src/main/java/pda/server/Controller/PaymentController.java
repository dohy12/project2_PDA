package pda.server.Controller;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
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
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import pda.server.DAO.PaymentMapper;


@CrossOrigin(origins = "*") // 추가
@RestController
public class PaymentController {
	private PaymentMapper paymentMapper;
	
	public PaymentController(PaymentMapper paymentMapper) {
		this.paymentMapper = paymentMapper; 
	} 
	
	//단체 회비 정보 저장하기 (확인)
	@RequestMapping(path = "/payments/due-infos", method = RequestMethod.POST)
    public String insert_due_infos(@RequestBody Map<String, Object> param)
    {
		String GroupId = (String)param.get("GroupId");
		String start_date = (String)param.get("start_date");
		String end_date = (String)param.get("end_date");
		int pay = (int)param.get("pay");
		String title = (String)param.get("title");
		String contents = (String)param.get("contents");
		
        paymentMapper.insert_due_infos(GroupId, start_date, end_date, pay, title, contents);
        return "성공";
    }
	
	//전체 회비 정보 가져오기 (확인)
	@RequestMapping(path = "/payments/due-infos/{GroupId}", method = RequestMethod.GET)
	public List<Map<String, Object>> select_due_infos(@PathVariable String GroupId)
	{
		return paymentMapper.select_due_infos(GroupId);
	}
	
	//유저가 결제한 회비 정보 가져오기(확인)
	@RequestMapping(path = "/payments/user-due-infos/{GroupId}", method = RequestMethod.GET)
	public List<Map<String, Object>> select_user_due_infos(@PathVariable String GroupId, @RequestAttribute String U_ID)
	{
		return paymentMapper.select_user_due_infos(GroupId, Integer.parseInt(U_ID));
	}
		
	//결제 요청 정보 저장하기(확인)
	@RequestMapping(path = "/payments/request-infos", method = RequestMethod.POST)
	public String insert_request_infos(@RequestBody Map<String, Object> param, @RequestAttribute String U_ID)
	{
		String GroupId = (String) param.get("GroupId");
		int P_ID = (int) param.get("P_ID");
		String merchant_uid = (String) param.get("merchant_uid");
		String buyer_name = (String) param.get("buyer_name");
		paymentMapper.insert_request_infos(GroupId, Integer.parseInt(U_ID), P_ID, merchant_uid, buyer_name);
		return "성공";
	}
	

	//결제 창 설정을 위한 결제 요청 정보 가져오기(확인)
	@RequestMapping(path = "/payments/request-infos/{GroupId}/{P_ID}", method = RequestMethod.GET)
	public Map<String, Object> select_request_infos(@PathVariable String GroupId, @PathVariable int P_ID, @RequestAttribute String U_ID)
	{
		return paymentMapper.select_request_infos(GroupId, Integer.parseInt(U_ID), P_ID);
	}
	
	
	//결제 진행하다 취소되거나, 결제 실패한 경우 결제 요청 정보에서 삭제(확인)
	@RequestMapping(path = "/payments/request-infos/{GroupId}/{P_ID}", method = RequestMethod.DELETE)
	public String delete_request_infos(@PathVariable String GroupId, @PathVariable int P_ID, @RequestAttribute String U_ID)
	{
		paymentMapper.delete_request_infos(GroupId, Integer.parseInt(U_ID), P_ID);
		return "성공";
	}
	
	//해당 시간의 결제 취소(환불)을 위한 결제 결과 정보 불러오기 (확인)
	@RequestMapping(path = "/payments/results/{GroupId}/{P_ID}", method = RequestMethod.GET)
	public Map<String, Object> select_result_infos(@PathVariable String GroupId, @PathVariable int P_ID, @RequestAttribute String U_ID)
	{
		return paymentMapper.select_result_infos(GroupId, P_ID, Integer.parseInt(U_ID));
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
	
	/*
	//결제 성공 시 결과 정보 저장하기 (확인) - 아래 있는 것으로 대체 예정
	@RequestMapping(path = "/payments/results", method = RequestMethod.POST)
	public String insert_result_infos(@RequestBody Map<String, Object> param, @RequestAttribute String U_ID)
	{
		String GroupId = (String)param.get("GroupId");
		int P_ID = (int)param.get("P_ID");
		String imp_uid = (String)param.get("imp_uid");
		String merchant_uid = (String)param.get("merchant_uid");
		String time = (String)param.get("time");
	    paymentMapper.insert_result_infos(GroupId, P_ID, Integer.parseInt(U_ID), imp_uid, merchant_uid, time);
	    return "성공";
	}*/
	
	@RequestMapping(path = "/payment/success", method = RequestMethod.GET)
	public String payment_result_verification(@RequestParam(value="imp_uid")String imp_uid, @RequestParam(value="merchant_uid")String merchant_uid) throws RestClientException, URISyntaxException, ParseException
	{
		//아직 확인 안함, 코드만 대강 작성
		
		/*아임 포트 서버에서 액세스 토큰 발급받기 - POST*/
		//post header
		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Type", "application/json");
		
		//post parameter
		MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
		parameters.add("imp_apikey", "9542528569364242"); //REST API 키
		parameters.add("imp_secret", "uMGHwm4vTuKsHy1kxlZ1xrfAmidjnymRk3zRpEmj86MQFOad99fGBugXNuhb3D2oGBf6fBqoIcfn1pAa"); //REST API Secret
		
		//post request
		HttpEntity<MultiValueMap<String, String>> post_request = new HttpEntity<>(parameters, headers);
		
		RestTemplate rest = new RestTemplate();
		
		//post response result - String(JSONObject) 형태로 받음
		String post_result;
		post_result = rest.postForObject(new URI("https://api.iamport.kr/users/getToken"), post_request, String.class);

		//post 결과 중 access token parsing
		JSONObject post_fb;
		post_fb = (JSONObject) new JSONParser().parse(post_result);
		String access_token = (String) post_fb.get("access_token");
		
		
		/*imp_uid로 아임포트 서버에서 결제 정보 조회 - GET*/
		//get header - access token 추가
		headers.set("Authorization", access_token);
		
		HttpEntity<MultiValueMap<String, String>> get_request = new HttpEntity<>(null, headers);
		
		ResponseEntity get_result = rest.exchange("https://api.iamport.kr/payments/" + imp_uid, HttpMethod.GET, get_request, String.class);
		JSONObject get_fb;
		get_fb = (JSONObject) new JSONParser().parse(get_result.getBody().toString());
		HashMap<String, Object> response = (HashMap<String, Object>) get_fb.get("response"); //response 객체 얻기
		
		int amount = (int)response.get("amount"); //아임포트 서버측 결제 값 얻기
		
		HashMap<String, Object>custom_data = (HashMap<String, Object>) response.get("custom_data"); //custom 객체 얻기
		String Group_ID = (String)custom_data.get("Group_ID"); //P_ID 얻기
		int U_ID = (int)custom_data.get("U_ID"); //U_ID 얻기
		int P_ID = (int)custom_data.get("P_ID"); //P_ID 얻기
		
		int amountToBePaid = paymentMapper.select_pay(Group_ID, P_ID);
		
		//결제 위변조 검증 - 아임포트 서버 측 결제 값과 실제 결제 되어야 하는 값 비교
		if(amountToBePaid == amount) { //결제 금액 일치 - DB 결제 정보 저장
			//현재 시간 얻기
			SimpleDateFormat format1 = new SimpleDateFormat ( "yyyy-MM-dd HH:mm:ss");
			Date time = new Date();
			String current_time = format1.format(time);
			//db 저장
			paymentMapper.insert_result_infos(Group_ID, P_ID, U_ID, imp_uid, merchant_uid, current_time);
		}
		else { //결제 값 위변조
			//throw error 
		}
		return "성공";
	} 
}
