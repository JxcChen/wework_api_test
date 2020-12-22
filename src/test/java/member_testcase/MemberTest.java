package member_testcase;

import api_object.MemberObject;
import api_object.TokenHelper;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.util.ArrayList;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;
@Epic("企业微信接口用例")
@Feature("成员管理测试用例")
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

    // name 1~64个utf8字符
    // userId 不区分大小写，长度为1~64个字节。只能由数字、字母和“_-@.”四种字符组成，且第一个字符必须是数字或字母。
    @Description("使用数据驱动执行添加成员")
    @Story("添加成员测试用例")
    @ParameterizedTest
    @DisplayName("创建用户")
    @CsvFileSource(resources = "/member_data.csv",numLinesToSkip = 1)
    void addMemberTest(String userId,String userName,String mobile,String errCode,String errMsg) {
        String departmentId = "2";
        Response response = MemberObject.addMember(userId, userName, mobile, departmentId, accessToken);
        assertAll(
                ()->assertEquals(errCode, response.path("errcode").toString()),
                ()->assertTrue(response.path("errmsg").toString().contains(errMsg))
        );
    }

    @Description("修改成员")
    @Story("修改成员测试用例")
    @Test
    void updateMember(){
        String updateName = "小黄";
        Response response = MemberObject.updateMember(userId, updateName, accessToken);
        assertEquals("0",response.path("errcode").toString());
    }

    @Description("读取单个成员")
    @Story("读取单个成员测试用例")
    @Test
    void getUserMsg() {
        Response response = MemberObject.getUserMsg(accessToken, userId);
        System.out.println(response.body().toString());
        assertEquals("0", response.path("errcode").toString());
    }

    @Description("删除单个成员")
    @Story("删除单个成员测试用例")
    @Test
    void deleteMember() {
        Response response = MemberObject.deleteMember(accessToken, userId);
        assertEquals("0", response.path("errcode").toString());
    }

    @Description("批量删除部门成员")
    @Story("批量删除成员测试用例")
    @Test
    void batchDeleteMemberTest() {
        // 删除测试部门中所有成员数据
        ArrayList<String> departmentUserIdList = MemberObject.getDepartmentUserIdList(accessToken, "2");
        // 批量删除成员
        Response response = MemberObject.batchDeleteMember(accessToken, departmentUserIdList);
    }

    @Test
    void getDepartmentUserMsg(){
        given()
                .queryParams("access_token", accessToken, "department_id", 1)
                .get("https://qyapi.weixin.qq.com/cgi-bin/user/list")
                .then()
                .body("userlist[0].userid",equalTo("ChenJinXuan"))
        ;

    }
}
