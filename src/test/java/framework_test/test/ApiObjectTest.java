package framework_test.test;


import framework_test.apiObject.ApiObjectModel;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.io.IOException;
import java.util.ArrayList;


/**
 * Project: wework_api_test
 * Created by JJJJ on 2020-12-27 9:40
 * Description: ApiActionModel单元测试
 */
public class ApiObjectTest {

    Logger logger = LoggerFactory.getLogger(ApiObjectTest.class);
    static ArrayList<String> actParamList;
    @BeforeAll
    public static void beforeAll(){
        actParamList = new ArrayList<>();
        // 企业ID ： wwbcc92e0afe51b09e  secret ： MmNdXbFeCNiPJEztv1Kd1TqW6e3Gy6BBgPVRWDJa9fI  agentId ： 3010084
        actParamList.add("wwbcc92e0afe51b09e");
        actParamList.add("MmNdXbFeCNiPJEztv1Kd1TqW6e3Gy6BBgPVRWDJa9fI");
    }

    @Test
    void test_01() throws IOException {

        ApiObjectModel actions = ApiObjectModel.load("src\\test\\resources\\api\\tokenhelper.yaml");
        actions.getActions().get("getToken").run(actParamList);

        logger.info("test debug");
    }

}
