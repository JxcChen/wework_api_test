package framework_test.step;

import framework_test.apiObject.ApiLoader;
import framework_test.global_data.GlobalVariables;
import io.restassured.response.Response;

import org.junit.jupiter.api.function.Executable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.PlaceholderUtils;


import java.util.ArrayList;
import java.util.HashMap;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

/**
 * Project: wework_api_test
 * Created by JJJJ on 2020-12-27 15:09
 * Description: step映射类
 */
public class StepModel {

    Logger logger = LoggerFactory.getLogger(StepModel.class);


    private String api;
    private String action;
    private ArrayList<String> actualParameter;
    private HashMap<String, String> save;
    private HashMap<String, String> saveGlobal;
    private ArrayList<AssertModel> asserts;
    private StepResult result = new StepResult();
    private HashMap<String, String> stepVariable = new HashMap<>();
    private ArrayList<Executable> assertAllList = new ArrayList<>();

    public StepResult run(HashMap<String, String> testcaseVariable) {
//        1、需要定义AssertModel类，用来存储反序列化出来的断言对象
//        2、需要定义StepResult类，用来存储请求的相应信息和断言结果


        // 3、替换实参中的变量
        if (actualParameter != null) {
            actualParameter = PlaceholderUtils.resolveList(actualParameter, testcaseVariable);
        }

        // 4、根据case中的配置执行相应的action，当然要传入替换过变量的实参
        Response response = ApiLoader.run(api, action).run(actualParameter);

        // 5、根据case中的配置截取响应中的字段，并存入step变量Map中
        if (save != null) {
            save.forEach((variableName, path) -> {
                String value = response.path(path);
                stepVariable.put(variableName, value);
            });
        }

        // 6、根据case中的配置截取响应中的字段，并存入Global变量Map中
        if (saveGlobal != null) {
            saveGlobal.forEach((variableName, path) -> {
                String value = response.path(path);
                GlobalVariables.getGlobalVariables().put(variableName, value);
                logger.info("在全局变量中新增" + variableName + "变量  值为：" + value);
            });
        }

        // 7、根据case中的配置对返回结果进行软断言，但不会终结测试将断言结果存入断言结果列表中，在用例最后进行统一结果输出
        if (asserts != null && asserts.size() > 0) {
            asserts.forEach(assertModel -> {
                assertAllList.add(() -> {
                    assertThat(assertModel.getReason(), response.path(assertModel.getActual()).toString(), equalTo(assertModel.getExpect()));
                });
            });
        }
        // 8、将response和断言结果存储到stepResult对象中并返回
        result.setAssertList(assertAllList);
        result.setStepVariable(stepVariable);
        result.setResponse(response);
        return result;
    }


    public String getApi() {
        return api;
    }

    public void setApi(String api) {
        this.api = api;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public ArrayList<String> getActualParameter() {
        return actualParameter;
    }

    public void setActualParameter(ArrayList<String> actualParameter) {
        this.actualParameter = actualParameter;
    }

    public HashMap<String, String> getSave() {
        return save;
    }

    public void setSave(HashMap<String, String> save) {
        this.save = save;
    }

    public HashMap<String, String> getSaveGlobal() {
        return saveGlobal;
    }

    public void setSaveGlobal(HashMap<String, String> saveGlobal) {
        this.saveGlobal = saveGlobal;
    }

    public ArrayList<AssertModel> getAsserts() {
        return asserts;
    }

    public void setAsserts(ArrayList<AssertModel> asserts) {
        this.asserts = asserts;
    }

    public StepResult getResult() {
        return result;
    }

    public void setResult(StepResult result) {
        this.result = result;
    }
}
