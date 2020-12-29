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
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;

/**
 * Project: wework_api_test
 * Created by JJJJ on 2020-12-27 17:16
 * Description: testcase映射类
 *
 *
 * todo: 1、对testcase_yaml文件进行反序列化 2、执行所有测试用例并在最后进行断言
 * 创建load方法 传入参数：yaml_path 用于对目标yaml文件进行反序列化
 * load具体实现：
 * 1、对目标文件反序列并返回
 *
 * 创建run方法 用于执行所有测试用例并在最后进行断言
 * run方法具体实现：
 * 1、设置时间戳具体值到用例变量中
 * 2、遍历所有step 并执行
 * 3、调用StepModel.stepFission 对step进行裂变并获取到裂变候的step集合
 * 4、遍历step集合执行step.run 并获取到result
 * 5、跟新用例变量和断言集合
 * 6、执行全局软断言
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
     * @param path yaml路径
     * @return TestcaseModel
     * @throws IOException
     */
    public static TestcaseModel load(String path) throws IOException {
        // 对目标文件反序列并返回
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        return mapper.readValue(new File(path),TestcaseModel.class);
    }

    /**
     * 用于执行所有测试用例并在最后进行断言
     */
    public void run (){
        // 1、设置时间戳具体值到用例变量中
        testCaseVariables.put("getTimeStamp", FakeUtils.getTimeMillis());
        logger.info("更新用例变量："+"getTimeStamp");

        // 2、遍历所有step 并执行
        steps.forEach(step -> {
            // 3、调用StepModel.stepFission 对step进行裂变并获取到裂变候的step集合
            List<StepModel> stepModels = step.stepFission();
            // 4、遍历step集合执行step.run 并获取到result
            stepModels.forEach(stepModel -> {
                StepResult stepResult = stepModel.run(testCaseVariables);

                // 5、跟新用例变量和断言集合
                if (stepResult.getStepVariable() != null){
                    testCaseVariables.putAll(stepResult.getStepVariable());
                    logger.info("更新用例变量");
                }
                // 4、处理assertList，到最后进行断言。
                if (stepResult.getAssertList() != null){
                    assertList.addAll(stepResult.getAssertList());
                }
            });
        });

        // 6、执行全局软断言
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
