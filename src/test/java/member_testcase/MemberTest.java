package member_testcase;

import api_object.MemberObject;
import api_object.TokenHelper;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MemberTest {
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


    @BeforeEach
    void addTestUser() {
        userId = MemberObject.addUserAndGetUserId(accessToken, "2");
    }


    @Test
    void addMemberTest() {
        String userId = "xiaoming2";
        String userName = "小明";
        String mobile = "13010000000";
        String departmentId = "2";
        Response response = MemberObject.addMember(userId, userName, mobile, departmentId, accessToken);
        assertEquals("0", response.path("errcode").toString());
    }

    @Test
    void getMemberList() {
        Response response = given()
                .queryParams("access_token", accessToken, "department_id", "2")
                .get("https://qyapi.weixin.qq.com/cgi-bin/user/simplelist")
                .then()
                .log()
                .all()
                .extract()
                .response();
        ArrayList<String> userIdList = response.path("userlist.userid");
        for (String userId : userIdList
        ) {
            System.out.println(userId);
        }
        System.out.println(response.body().toString());
    }


    @Test
    void getUserMsg() {
        Response response = MemberObject.getUserMsg(accessToken, userId);
        System.out.println(response.body().toString());
        assertEquals("0", response.path("errcode"));
    }

    @Test
    void deleteMember() {
        Response response = MemberObject.deleteMember(accessToken, userId);
        assertEquals("0", response.path("errcode").toString());
    }

    @Test
    void batchDeleteMemberTest() {
        // 删除测试部门中所有成员数据
        ArrayList<String> departmentUserIdList = MemberObject.getDepartmentUserIdList(accessToken, "2");
        // 批量删除成员
        Response response = MemberObject.batchDeleteMember(accessToken, departmentUserIdList);
    }
}
