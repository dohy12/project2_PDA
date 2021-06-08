package pda.server.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pda.server.DAO.BoardOperation;
import pda.server.DAO.SurveyOperation;
import pda.server.DTO.JoinedSurvey;
import pda.server.DTO.Option;
import pda.server.DTO.Result;
import pda.server.DTO.Survey;

import java.util.List;
import java.util.Map;

@RestController
public class SurveyAPI {
    @Autowired
    SurveyOperation survey;

    @RequestMapping(value = "/Survey/{GroupId}/exist/{BID}", method = RequestMethod.GET)
    public int isExist(@PathVariable("GroupId") String GroupId, @PathVariable("BID") int BID){
        return survey.isExist(GroupId, BID);
    }

    @RequestMapping(value = "/Survey/{GroupId}/{BID}", method = RequestMethod.GET)
    public List<JoinedSurvey> surveyValue(@PathVariable("GroupId") String GroupId, @PathVariable("BID") int BID) {
        return survey.surveyValue(GroupId, BID);
    }

    @RequestMapping(value = "/Survey/{GroupId}", method = RequestMethod.GET)
    public int nextSID(@PathVariable("GroupId") String GroupId){
        int sid = survey.nextSID(GroupId);
        return sid;
    }

    @RequestMapping(value = "/Survey/{GroupId}/oid", method = RequestMethod.GET)
    public int nextOID(@PathVariable("GroupId") String GroupId){
        int oid = survey.nextOID(GroupId);
        return oid;
    }

    @RequestMapping(value = "/Survey/{GroupId}", method = RequestMethod.POST)
    public String surveyPost(@PathVariable("GroupId") String GroupId, @RequestBody Map<String, Object> params) {
        int sid = (int)params.get("SID");
        String title = (String)params.get("TITLE");
        int bid = (int)params.get("BID");

        Survey Survey = new Survey(sid, title, bid);

        survey.surveyPost(GroupId, Survey);

        return "등록";
    }

    @RequestMapping(value = "/Survey/{GroupId}/option", method = RequestMethod.POST)
    public String optionPost(@PathVariable("GroupId") String GroupId, @RequestBody Map<String, Object> params){
        int oid = (int)params.get("OID");
        String contents = (String)params.get("CONTENTS");
        int sid = (int)params.get("SID");

        Option Option = new Option(oid, contents, sid);

        survey.optionPost(GroupId, Option);

        return "등록";
    }

    @RequestMapping(value = "/Survey/{GroupId}/result", method = RequestMethod.POST)
    public String resultPost(@PathVariable("GroupId") String GroupId, @RequestBody Map<String, Object> params) {
        int oid = (int)params.get("OID");
        int voted = (int)params.get("VOTED");

        Result Result = new Result(oid, voted);

        survey.resultPost(GroupId, Result);

        return "등록";
    }

    @RequestMapping(value = "/Survey/{GroupId}/voted/{OID}", method = RequestMethod.GET)
    public String updateResult(@PathVariable("GroupId") String GroupId, @PathVariable("OID") int OID) {
        survey.updateResult(GroupId, OID);

        return "투표완료";
    }
}
