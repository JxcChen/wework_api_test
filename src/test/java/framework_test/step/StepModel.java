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
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;


/**
 * Project: wework_api_test
 * Created by JJJJ on 2020-12-27 15:09
 * Description: step映射类
 *
 * todo:1、声明testcase_yaml_steps所有对应的变量 2、将StepModel根据actualParam组数进行裂变 3、执行具体测试步骤
 * 创建stepFission 用于进行用例裂变
 * stepFission具体实现：
 * 1、遍历actualParameter
 * 2、创建StepModel对象并将设置好变量值
 * 3、将遍历中的i 值赋给index
 * 4、将所有StepModel存入集合中并返回
 *
 * 创建run方法  传入参数：用例变量   用于执行具体测试步骤
 * run具体实现
 * 1、先将实参中的占位符进行替换 如${getTimeStamp}
 * 2、根据api 和 action 获取对应的action并执行action.run方法获取response
 * 3、将需要保存的变量进行保存
 * 4、获取断言列表
 * 5、声明stepResult对象 并将结果 用例变量 响应封装候进行返回
 */
public class StepModel {

    Logger logger = LoggerFactory.getLogger(StepModel.class);


    private String api;
    private String action;
    private ArrayList<ArrayList<String>> actualParameter;
    private int index = 0;
    private HashMap<String, String> save;
    private HashMap<String, String> saveGlobal;
    private HashMap<String,ArrayList<AssertModel>> asserts;
    private StepResult result = new StepResult();
    private HashMap<String, String> stepVariable = new HashMap<>();
    private ArrayList<Executable> assertAllList = new ArrayList<>();
    private ArrayList<String> finalActualParameter = new ArrayList<>();


    /**
     * 对测试用例 根据参数个数进行裂变
     * @return 裂变后的测试用例集合
     */
    public List<StepModel> stepFission(){
        ArrayList<StepModel> stepModelList  = new ArrayList<>();
        // 1、遍历actualParameter
        for (int i = 0; i < actualParameter.size(); i++) {
            // 2、创建StepModel对象并将设置好变量值
            StepModel model = new StepModel();
            // 3、将遍历中的i值赋给index
            model.index = i;
            model.api = api;
            model.action = action;
            model.actualParameter=actualParameter;
            model.asserts=asserts;
            model.save=save;
            model.saveGlobal=saveGlobal;
            // 4、将所有StepModel存入集合中并返回
            stepModelList.add(model);
        }
        return stepModelList;

    }


    public StepResult run(HashMap<String, String> testcaseVariable) {



        // 1、先将实参中的占位符进行替换 如${getTimeStamp}
        if (actualParameter != null && actualParameter.size()>=index+1&&actualParameter.get(index)!=null) {
            finalActualParameter.addAll(PlaceholderUtils.resolveList(actualParameter.get(index), testcaseVariable));
        }

        // 2、根据api 和 action 获取对应的action并执行action.run方法获取response
        Response response = ApiLoader.run(api, action).run(finalActualParameter);

        // 3、将需要保存的变量进行保存
        if (save != null) {
            save.forEach((variableName, path) -> {
                String value = response.path(path).toString();
                stepVariable.put(variableName, value);
            });
        }

        if (saveGlobal != null) {
            saveGlobal.forEach((variableName, path) -> {
                String value = response.path(path);
                GlobalVariables.getGlobalVariables().put(variableName, value);
                logger.info("在全局变量中新增" + variableName + "变量  值为：" + value);
            });
        }

        // 4、获取断言列表
        // 根据case中的配置对返回结果进行软断言，但不会终结测试将断言结果存入断言结果列表中，在用例最后进行统一结果输出

        if (asserts != null && asserts.size() >= index && asserts.get("assert"+index)!=null&&asserts.get("assert"+index).size()>0) {
            asserts.get("assert"+index).forEach(assertModel -> {
                assertAllList.add(() -> {
                    assertThat(assertModel.getReason(), response.path(assertModel.getActual()).toString(), equalTo(assertModel.getExpect()));
                });
            });
        }
        // 5、声明stepResult对象 并将结果 用例变量 响应进行封装候进行返回
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

    public ArrayList<ArrayList<String>> getActualParameter() {
        return actualParameter;
    }

    public void setActualParameter(ArrayList<ArrayList<String>> actualParameter) {
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

    public HashMap<String, ArrayList<AssertModel>> getAsserts() {
        return asserts;
    }

    public void setAsserts(HashMap<String, ArrayList<AssertModel>> asserts) {
        this.asserts = asserts;
    }

    public StepResult getResult() {
        return result;
    }

    public void setResult(StepResult result) {
        this.result = result;
    }
}
