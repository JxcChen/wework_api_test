package department_testcase;

import io.restassured.response.Response;

import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 * 基础用例编写 ： 创建部门、修改部门、查询部门、删除部门 缺点只能全部用例一起执行，无法单个用例单独执行
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DepartmentTest {
    static String access_token;
    static String departmentId;
    @BeforeAll
    public static void getToken(){
        // 企业ID ： wwbcc92e0afe51b09e  secret ： MmNdXbFeCNiPJEztv1Kd1Ug5__a3RxS6sV3CaMg8s2g  agentId ： 3010084
        // 获取token
        access_token = given()
                .queryParams("corpid", "wwbcc92e0afe51b09e", "corpsecret", "MmNdXbFeCNiPJEztv1Kd1TqW6e3Gy6BBgPVRWDJa9fI")
                .get("https://qyapi.weixin.qq.com/cgi-bin/gettoken")
                .then()
                .extract()
                .path("access_token");
    }

    @Test
    @Order(1)
    void addDepartmentTest(){
        // 创建部门
        // 声明请求体
        String body = "{" +
                "   \"name\": \"广州研发分院\",\n" +
                "   \"name_en\": \"RDGZ\",\n" +
                "   \"parentid\": 1\n" +
                "}";
        Response response = given()
                .contentType("application/json")
                .body(body)
                .post("https://qyapi.weixin.qq.com/cgi-bin/department/create?access_token="+access_token)
                .then()
                .log()
                .all()
                .extract()
                .response();
        departmentId = response.path("id").toString();
        assertEquals("0",response.path("errcode").toString());
    }

    @Test
    @Order(2)
    void updateDepartmentTest(){
        // 修改部门
        // 声明请求体
        String body = "{" +
                "     \"id\": "+departmentId+",\n" +
                "   \"name\": \"深圳研发分院\",\n" +
                "   \"name_en\": \"RDGZ\",\n" +
                "   \"parentid\": 1,\n" +
                "   \"order\": 1" +
                "}";
        Response response = given()
                .contentType("application/json")
                .body(body)
                .post("https://qyapi.weixin.qq.com/cgi-bin/department/update?access_token="+access_token)
                .then()
                .log()
                .all()
                .extract()
                .response();
        assertEquals("0",response.path("errcode").toString());
    }

    @Test
    @Order(3)
    void searchDepartmentTest(){
        // 修改部门
        // 声明请求体

        Response response = given()
                .queryParams("access_token",access_token,"id",departmentId)
                .post("https://qyapi.weixin.qq.com/cgi-bin/department/list")
                .then()
                .log()
                .all()
                .extract()
                .response();
        System.out.println(response.body().toString());
        assertEquals("0",response.path("errcode").toString());
    }

    @Test
    @Order(4)
    void deleteDepartmentTest(){
        // 修改部门
        // 声明请求体

        Response response = given()
                .queryParams("access_token",access_token,"id",departmentId)
                .post("https://qyapi.weixin.qq.com/cgi-bin/department/delete")
                .then()
                .log()
                .all()
                .extract()
                .response();
        assertEquals("0",response.path("errcode").toString());
    }
}
