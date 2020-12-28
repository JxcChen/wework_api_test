package demo;

import common_test.api_object.MemberObject;
import common_test.api_object.TokenHelper;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;

public class Test_01 {
    static String accessToken;
    static String userId;

    @BeforeAll
    public static void init() {
        accessToken = TokenHelper.getToken("wwbcc92e0afe51b09e", "MmNdXbFeCNiPJEztv1Kd1TqW6e3Gy6BBgPVRWDJa9fI");
        // 删除测试部门中所有成员数据
        ArrayList<String> departmentUserIdList = MemberObject.getDepartmentUserIdList(accessToken, "2");
        // 批量删除成员
        Response response = MemberObject.batchDeleteMember(accessToken, departmentUserIdList);
    }

    @Test
    void test_01(){
        given()
                .queryParams("access_token", accessToken, "department_id", 1)
                .get("https://qyapi.weixin.qq.com/cgi-bin/user/list")
                .then()
                .log()
                .all()
                .body("userlist[0].userid",equalTo("ChenJinXuan")) // 是否等于ChenJinXuan
        ;

    }

    @Test
    void test_02(){
        given()
                .queryParams("access_token", accessToken, "department_id", 1)
                .get("https://qyapi.weixin.qq.com/cgi-bin/user/list")
                .then()
                .log()
                .all()
                .body("userlist[0].department",hasItem(1)) // 数组中是否包含1
        ;

    }

    @Test
    void test_03(){
        given()
                .queryParams("access_token", accessToken, "department_id", 1)
                .get("https://qyapi.weixin.qq.com/cgi-bin/user/list")
                .then()
                .log()
                .all()
                .body("userlist[0].department",hasItem(1))
                .body("userlist[0].userid",equalTo("ChenJinXuan"))  // 进行多组断言
        ;

    }

    @Test
    void test_04(){
        given()
                .queryParams("access_token", accessToken, "department_id", 1)
                .get("https://qyapi.weixin.qq.com/cgi-bin/user/list")
                .then()
                .log()
                .all()
                .body("userlist[-1].name",equalTo("李四"))
        ;

    }


    @Test
    void test_05(){
        given()
                .get("http://localhost:8000/lotto.json")
                .then()
                .body("userlist[-1].name",equalTo("李四"))
        ;

    }

}
