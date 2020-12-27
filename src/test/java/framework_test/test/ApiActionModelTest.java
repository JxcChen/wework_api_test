package framework_test.test;

import framework_test.apiObject.ApiActionModel;
import framework_test.global_data.GlobalVariables;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Project: wework_api_test
 * Created by JJJJ on 2020-12-27 9:40
 * Description: ApiActionModel单元测试
 */
public class ApiActionModelTest {

    @Test
    void test_01(){
        ApiActionModel apiActionModel = new ApiActionModel();
        apiActionModel.setGet("https://qyapi.weixin.qq.com/cgi-bin/${url}");

        HashMap<String,String> globalVariables = new HashMap<>();
        globalVariables.put("url","gettoken");
        GlobalVariables.setGlobalVariables(globalVariables);

        HashMap<String,String> query = new HashMap<>();
        query.put("corpid","${corpid}");
        query.put("corpsecret","${corpsecret}");
        apiActionModel.setQuery(query);

        ArrayList<String> formalParam = new ArrayList<>();
        formalParam.add("corpid");
        formalParam.add("corpsecret");
        apiActionModel.setFormalParam(formalParam);

        ArrayList<String> actParamList = new ArrayList<>();
        // 企业ID ： wwbcc92e0afe51b09e  secret ： MmNdXbFeCNiPJEztv1Kd1TqW6e3Gy6BBgPVRWDJa9fI  agentId ： 3010084
        actParamList.add("wwbcc92e0afe51b09e");
        actParamList.add("MmNdXbFeCNiPJEztv1Kd1TqW6e3Gy6BBgPVRWDJa9fI");

        apiActionModel.run(actParamList);
    }

}
