package framework_test.test;

import framework_test.apiObject.ApiLoader;
import framework_test.step.AssertModel;
import framework_test.step.StepModel;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Project: wework_api_test
 * Created by JJJJ on 2020-12-27 16:19
 * Description: StepModel单元测试
 */
public class StepModelTest {



    Logger logger = LoggerFactory.getLogger(ApiObjectTest.class);
    static ArrayList<String> actParamList;
    static ApiLoader apiLoader;
    @BeforeAll
    public static void beforeAll(){
        actParamList = new ArrayList<>();
        // 企业ID ： wwbcc92e0afe51b09e  secret ： MmNdXbFeCNiPJEztv1Kd1TqW6e3Gy6BBgPVRWDJa9fI  agentId ： 3010084
        actParamList.add("wwbcc92e0afe51b09e");
        actParamList.add("MmNdXbFeCNiPJEztv1Kd1TqW6e3Gy6BBgPVRWDJa9fI");
        apiLoader.load("src/test/resources/api");
    }

    @Test
    void test_01() throws IOException {

        StepModel model = new StepModel();
        model.setApi("tokenhelper");
        model.setAction("getToken");
        model.setActualParameter(actParamList);

        HashMap<String,String> save = new HashMap<>();
        save.put("accesstoken","access_token");
        model.setSave(save);

        HashMap<String,String> saveGlobal = new HashMap<>();
        saveGlobal.put("accesstoken","access_token");
        model.setSaveGlobal(saveGlobal);

        AssertModel assertModel = new AssertModel();
        assertModel.setActual("errcode");
        assertModel.setMatcher("equalTo");
        assertModel.setExpect("1");
        assertModel.setReason("getToken错误码校验01");
        ArrayList<AssertModel> assertModelArrayList = new ArrayList<>();
        assertModelArrayList.add(assertModel);

        model.setAsserts(assertModelArrayList);
        HashMap<String,String> stepVariable = new HashMap<>();
        model.run(stepVariable);

        logger.info("test debug");
    }


}
