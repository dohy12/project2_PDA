package pda.server.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.relational.core.dialect.IdGeneration;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pda.server.DAO.GroupGeneration;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 *  예 18.206.18.154:8080/GroupIdGeneration/국제라이온스협회
 *  메소드 단체 이름 받고 --> 단체 이름 중복 검색 확인
 *  --->  UUID 생선  ---> UUID의'-' 기호를 삭제 --->UUID를 GroupID로 설정합니다
 *  ---> 단체 이름 랑 GroupID로 기록  --->  GroupID로 스키마를 만듭니다
 *
 *  반환 값 Code 의미 :  Code 200 : 조작 성공한다.   Code 301 : 단체 이름 중복.   Code 302 : SQL 구문 실행 시패
 */

@RestController
public class GroupIdGeneration
{
    @Autowired
    GroupGeneration groupGeneration;
    @RequestMapping("/GroupIdGeneration/{GroupName}")
    public Map<String,Object> IdGeneration(@PathVariable String GroupName)
    {
        Map<String,Object> map = new HashMap<>();
        if(groupGeneration.checkDuplicate(GroupName)!=0)  // 단체 이름 중복 검색
        {
            map.put("code", 301);
            map.put("msg", "단체 이름 중복");
            map.put("GroupName", GroupName);
            return map;
        }
        try
        {
            UUID uuid = UUID.randomUUID();
            String str = uuid.toString();
            String uuidStr = str.substring(0, 8) + str.substring(9, 13) + str.substring(14, 18) + str.substring(19, 23) + str.substring(24); //'-' 기호를 삭제
            groupGeneration.idGeneration(GroupName,uuidStr); // 단체 이름 랑 ID 기록
            groupGeneration.schemaGeneration(uuidStr); // 스키마 생선
            map.put("code", 200);
            map.put("msg", "단체 생성에 성공했다");
            map.put("GroupName", GroupName);
            map.put("GroupId", uuidStr);
            return map;
        }
        /**
         * 테스트 중 작은 부분에서 생성된 GroupID를 스키마로 만들 수 없습니다.
         * 해결 방법: 사용자에게 다시 인터페이스에 접근하여 새로운 GroupID를 생성할 것을 알려줍니다.
         * 예 CREATE DATABASE 6420e4f565ad4b2a85dc704f45f3c3ed  데이터베이스에서 실행할 수 없습니다
         */
        catch (BadSqlGrammarException e)
        {
            map.put("code", 302);
            map.put("msg", "단체 생성 에러. 다시 시도해주세요");
            map.put("GroupName", GroupName);
            return map;
        }

    }
}
