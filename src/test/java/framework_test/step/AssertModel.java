package framework_test.step;

/**
 * Project: wework_api_test
 * Created by JJJJ on 2020-12-27 14:55
 * Description: yaml文件中assert的映射类
 */
public class AssertModel {

    private String actual;
    private String matcher;
    private String expect;
    private String reason;


    public String getActual() {
        return actual;
    }

    public void setActual(String actual) {
        this.actual = actual;
    }

    public String getMatcher() {
        return matcher;
    }

    public void setMatcher(String matcher) {
        this.matcher = matcher;
    }

    public String getExpect() {
        return expect;
    }

    public void setExpect(String expect) {
        this.expect = expect;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
