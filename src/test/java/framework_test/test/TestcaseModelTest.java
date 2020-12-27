package framework_test.test;

import framework_test.apiObject.ApiLoader;
import framework_test.step.AssertModel;
import framework_test.step.StepModel;
import framework_test.step.StepResult;
import framework_test.testcase.TestcaseModel;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
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
public class TestcaseModelTest {




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

        TestcaseModel testcaseModel = new TestcaseModel();
        TestcaseModel model = testcaseModel.load("src\\test\\resources\\testcase\\gettoken.yaml");
        model.run();

        logger.info("test debug");
    }


}
