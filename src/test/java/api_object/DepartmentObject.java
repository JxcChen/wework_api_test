package api_object;

import io.restassured.response.Response;
import utils.FakeUtils;

import static io.restassured.RestAssured.given;

public class DepartmentObject {
    static String departmentName;
    static String departmentEnName;

    /**
     * 获取创建部门响应
     * @param name 部门名称
     * @param enName 部门英文名称
     * @param accessToken access_token
     * @return
     */
    public static  Response createDepartment(String name, String enName, String accessToken){
        // 创建部门
        // 声明请求体
        String body = "{" +
                "   \"name\": \""+name+"\",\n" +
                "   \"name_en\": \""+enName+"\",\n" +
                "   \"parentid\": 1\n" +
                "}";
        return given()
                .contentType("application/json")
                .body(body)
                .post("https://qyapi.weixin.qq.com/cgi-bin/department/create?access_token="+accessToken)
                .then()
                .log()
                .all()
                .extract()
                .response();
    }

    /**
     * 获取创建部门响应
     * @param accessToken access_token
     * @return response
     */
    public static  Response createDepartment(String accessToken){
        // 创建部门
        // 声明请求体
        departmentName = "name"+ FakeUtils.getTimeMillis();
        departmentEnName = "enName"+ FakeUtils.getTimeMillis();
        return createDepartment(departmentName,departmentEnName,accessToken);
    }


    /**
     * 获取查询响应
     * @param accessToken access_token
     * @param departmentId 部门ID
     * @return response
     */
    public static Response getDepartmentList(String accessToken, String departmentId){
        return given()
                .queryParams("access_token", accessToken,"id",departmentId)
                .get("https://qyapi.weixin.qq.com/cgi-bin/department/list")
                .then()
                .extract()
                .response();
    }

    /**
     * 获取删除部门响应
     * @param accessToken access_token
     * @param departmentId 部门ID
     * @return response
     */
    public static Response deleteDepartment(String accessToken, String departmentId){
        return given()
                .queryParams("access_token", accessToken,"id",departmentId)
                .get("https://qyapi.weixin.qq.com/cgi-bin/department/delete")
                .then()
                .log()
                .all()
                .extract()
                .response();
    }

    /**
     * 获取修改部门后的响应
     * @param departmentId 部门ID
     * @param name 修改后的部门名称
     * @param enName 修改后的部门英文名称
     * @param accessToken access_token
     * @return response
     */
    public static Response updateDepartment(String departmentId, String name, String enName, String accessToken){
        departmentName = name+ FakeUtils.getTimeMillis();
        departmentEnName = enName+ FakeUtils.getTimeMillis();
        String body = "{" +
                "     \"id\": "+departmentId+",\n" +
                "   \"name\": \""+departmentName+"\",\n" +
                "   \"name_en\": \""+departmentEnName+"\",\n" +
                "   \"parentid\": 1,\n" +
                "}";
        return  given()
                .contentType("application/json")
                .body(body)
                .post("https://qyapi.weixin.qq.com/cgi-bin/department/update?access_token="+ accessToken)
                .then()
                .log()
                .all()
                .extract()
                .response();
    }

}
