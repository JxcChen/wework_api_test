package framework_test.step;

import org.junit.jupiter.api.function.Executable;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Project: wework_api_test
 * Created by JJJJ on 2020-12-27 15:06
 * Description: 储存测试断言、测试变量，请求响应的对象
 */
public class StepResult extends BaseResult {

    private ArrayList<Executable> assertList;
    private HashMap<String,String> stepVariable;

    public ArrayList<Executable> getAssertList() {
        return assertList;
    }

    public void setAssertList(ArrayList<Executable> assertList) {
        this.assertList = assertList;
    }

    public HashMap<String, String> getStepVariable() {
        return stepVariable;
    }

    public void setStepVariable(HashMap<String, String> stepVariable) {
        this.stepVariable = stepVariable;
    }
}
