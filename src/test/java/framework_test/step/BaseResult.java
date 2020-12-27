package framework_test.step;

import io.restassured.response.Response;

/**
 * Project: wework_api_test
 * Created by JJJJ on 2020-12-27 15:05
 * Description: 储存response的基类
 */
public class BaseResult {
    public Response response;

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }
}
