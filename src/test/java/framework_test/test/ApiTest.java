package framework_test.test;

import framework_test.apiObject.ApiLoader;
import framework_test.testcase.TestcaseModel;

import io.restassured.specification.Argument;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;


/**
 * 定义apiTest测试方法和同名的参数方法，用来读取目录下所有的testcase文件并执行
 */
public class ApiTest {


    Logger logger = LoggerFactory.getLogger(ApiTest.class);

    @ParameterizedTest
    @MethodSource
    void apiTest(TestcaseModel testcaseModel,String apiName,String description){
        logger.info("测试："+apiName+"接口");
        logger.info(description);
        testcaseModel.run();
    }


    public static ArrayList<Arguments> apiTest(){
        // 遍历目录下所有的用例文件，并组装成参数列表
        final TestcaseModel[] testcaseModel = new TestcaseModel[1];
        ArrayList<Arguments> testcaseList = new ArrayList<>();
        // 1、先加载所有api相关yaml文件
        ApiLoader.load("src/test/resources/api");

        // 2、获取测试用例目录
        String testcaseDirPath = "src/test/resources/testcase";
        // 3、对目录进行遍历
        Arrays.stream(new File(testcaseDirPath).list()).forEach(testcaseFileName ->{
            String path = testcaseDirPath+ "/" + testcaseFileName;

            try {
                // 将所有测试用例文件封装到列表中
                testcaseModel[0] = TestcaseModel.load(path);
                testcaseList.add(Arguments.arguments(testcaseModel[0],testcaseModel[0].getName(),testcaseModel[0].getDescription()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        return testcaseList;
    }
}
