package framework_test.testcase;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import framework_test.step.StepModel;
import framework_test.step.StepResult;
import org.junit.jupiter.api.function.Executable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FakeUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertAll;

/**
 * Project: wework_api_test
 * Created by JJJJ on 2020-12-27 17:16
 * Description: testcase映射类
 */
public class TestcaseModel {

    Logger logger = LoggerFactory.getLogger(TestcaseModel.class);

    private String name;
    private String description;
    private ArrayList<StepModel> steps;
    private ArrayList<Executable> assertList =  new ArrayList<>();
    private HashMap<String,String> testCaseVariables = new HashMap<>();

    /**
     * 对测试用例yaml文件进行反序列化
     * @param path yaml路劲
     * @return TestcaseModel
     * @throws IOException
     */
    public static TestcaseModel load(String path) throws IOException {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        return mapper.readValue(new File(path),TestcaseModel.class);
    }

    public void run (){
        // 1、加载用例层关键字变量
        testCaseVariables.put("getTimeStamp", FakeUtils.getTimeMillis());
        logger.info("更新用例变量");

        // 2、遍历执行所有step
        steps.forEach(step -> {
            StepResult stepResult = step.run(testCaseVariables);
            if (stepResult.getStepVariable() != null){
                testCaseVariables.putAll(stepResult.getStepVariable());
                logger.info("更新用例变量");
            }
            // 4、处理assertList，到最后进行断言。
            if (stepResult.getAssertList() != null){
                assertList.addAll(stepResult.getAssertList());
            }
        });

        // 5、进行assertAll断言
        assertAll("全局断言",assertList.stream());
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<StepModel> getSteps() {
        return steps;
    }

    public void setSteps(ArrayList<StepModel> steps) {
        this.steps = steps;
    }
}
