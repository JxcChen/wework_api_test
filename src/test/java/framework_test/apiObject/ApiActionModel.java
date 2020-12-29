package framework_test.apiObject;

import framework_test.global_data.GlobalVariables;
import io.restassured.response.Response;

import io.restassured.specification.RequestSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.PlaceholderUtils;


import java.util.ArrayList;
import java.util.HashMap;

import static io.restassured.RestAssured.given;


/**
 * todo:接口测试执行请求并返回响应
 * 1 声明所有相关属性 -- yaml对应
 *    声明run执行方法  传入实参  用于执行请求
 * 2 确认requestUrl 和 requestMethod
 * 3 将可能存在占位符的变量进行实参转换
 * 4 封装请求参数
 * 5 发送请求获取响应并返回
 */
public class ApiActionModel {

    Logger logger = LoggerFactory.getLogger(ApiActionModel.class);
    // 1 声明所有相关属性 -- yaml对应
    private String url ;
    private String method;
    private String contentType;
    private ArrayList<String> formalParam;
    private HashMap<String,String> headers;
    private String body;
    private String post;
    private HashMap<String,String> query;
    private String get;
    private HashMap<String,String> actionVariables = new HashMap<>();
    private Response response;



    public Response run(ArrayList<String> actualParamList){
        HashMap<String,String> runQuery = new HashMap<>() ;
        String requestUrl = this.url;
        String runBody = this.body;

        // 2 确认requestUrl 和 requestMethod
        if(get !=null ){
            requestUrl = get;
            method = "get";
        }else if (post !=null){
            requestUrl = post;
            method = "post";
        }

        // 3 将可能存在占位符的变量进行实参转换
        // 3.1 对url、body、query进行占位符 与全局变量的初步替换
        if (requestUrl != null){
            requestUrl = PlaceholderUtils.resolveString(requestUrl, GlobalVariables.getGlobalVariables());
        }
        if (runBody != null){
            runBody = PlaceholderUtils.resolveString(runBody,GlobalVariables.getGlobalVariables());
        }
        if (query != null){
            runQuery.putAll(PlaceholderUtils.resolveMap(query,GlobalVariables.getGlobalVariables()));
        }

        // 3.2 对url和body进行用例层面的参数替换
        // 先将形参与实参封装到集合中
        if (formalParam !=null&&formalParam.size()>0&&actualParamList!=null&&formalParam.size()==actualParamList.size()){
            for (int i = 0; i < formalParam.size(); i++) {
                // 将形参和实参储存到用例变量中
                actionVariables.put(formalParam.get(i),actualParamList.get(i));
                logger.info("action变量新增变量:"+formalParam.get(i)+" 值为："+ actualParamList.get(i));
            }

            // 进行替换
            if (runBody !=null){
                runBody = PlaceholderUtils.resolveString(runBody, actionVariables);
            }

            if (requestUrl !=null){
                requestUrl = PlaceholderUtils.resolveString(requestUrl, actionVariables);
            }
            if (query != null){
                runQuery.putAll(PlaceholderUtils.resolveMap(query, actionVariables));
            }
        }


        // 4 封装请求参数
        // 先获取 RequestSpecification对象
        RequestSpecification specification = given().log().all();

        // 封装请求参数
        if (contentType !=null){
            specification.contentType(contentType);
        }
        if (runQuery!=null&&runQuery.size()>0){
            specification.formParams(runQuery);
        }
        if (headers != null){
            specification.headers(headers);
        }
        if (runBody!=null){
            specification.body(runBody);
        }
        // 5 发送请求获取响应并返回
        response = specification.request(method,requestUrl).then().log().all().extract().response();
        return response;

    }



    public HashMap<String, String> getQuery() {
        return query;
    }

    public void setQuery(HashMap<String, String> query) {
        this.query = query;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public ArrayList<String> getFormalParam() {
        return formalParam;
    }

    public void setFormalParam(ArrayList<String> formalParam) {
        this.formalParam = formalParam;
    }

    public HashMap<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(HashMap<String, String> headers) {
        this.headers = headers;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getGet() {
        return get;
    }

    public void setGet(String get) {
        this.get = get;
    }
}
