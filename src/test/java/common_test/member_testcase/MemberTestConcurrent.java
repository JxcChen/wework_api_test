package common_test.member_testcase;

import common_test.api_object.MemberObject;
import common_test.api_object.TokenHelper;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.parallel.Execution;
import utils.FakeUtils;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.parallel.ExecutionMode.CONCURRENT;

public class MemberTestConcurrent {
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


    @DisplayName("创建用户")
    @RepeatedTest(10)
    @Execution(CONCURRENT)
    void addMemberTest() {
        String exName = Thread.currentThread().getId()+ FakeUtils.getTimeMillis();
        String userId = "sssid"+exName;
        String userName = "JJJJ";
        String mobile = "130000" + (int)((Math.random()*9+1)*100000);
        String errCode = "0";
        String errMsg = "created";
        Response response = MemberObject.addMember(userId, userName, mobile, "2", accessToken);
        assertAll(
                ()->assertEquals(errCode, response.path("errcode").toString()),
                ()->assertTrue(response.path("errmsg").toString().contains(errMsg))
        );
    }


    @DisplayName("修改用户")
    @RepeatedTest(10)
    @Execution(CONCURRENT)
    void updateMember(){
        String updateName = "小黄";
        Response response = MemberObject.updateMember(userId, updateName, accessToken);
        assertEquals("0",response.path("errcode").toString());
    }

}
