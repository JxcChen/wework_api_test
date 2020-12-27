package common_test.group_chat_testcase;

import common_test.api_object.GroupChatObject;
import common_test.api_object.TokenHelper;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import utils.FakeUtils;

import java.util.ArrayList;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;


@Epic("企业微信借口测试用例")
@Feature("群聊管理测试用例")
public class GroupChatTest {

    static ArrayList<String> userList = new ArrayList<>();
    static String chatId;
    static String accessToken;
    @BeforeAll
    public static void init(){
        accessToken = TokenHelper.getToken("wwbcc92e0afe51b09e", "r7S5zFDDm_OvmrGmGJR4IMNpCMCMjfa4wfrsGD7lO-g");
    }

    @BeforeEach
    void createTestGroup(){
        userList.add("ChenJinXuan");
        userList.add("XiaoChen");
        chatId = GroupChatObject.createGroupChat(accessToken, userList);
    }

    @DisplayName("创建群聊")
    @Description("创建群聊测试用例")
    @Story("创建群聊")
    @Test
    void createChatTest(){
        userList.add("ChenJinXuan");
        userList.add("XiaoChen");
        Response response = GroupChatObject.createGroupChat("群聊", "XiaoChen", FakeUtils.getTimeMillis(), userList, accessToken);
        assertEquals("0",response.path("errcode").toString());
    }


    @DisplayName("修改群聊")
    @Description("修改群聊测试用例")
    @Story("修改群聊")
    @Test
    void updateGroupChatTest(){
        Response response = GroupChatObject.updateGroupChat(chatId, "只是群聊", "ChenJinXuan", accessToken);
        assertEquals("0",response.path("errcode").toString());
    }

    @DisplayName("获取群聊会话")
    @Description("获取群聊会话测试用例")
    @Story("获取群聊会话")
    @Test
    void getChatSessionTest(){
        Response response = GroupChatObject.getChatSession(accessToken, chatId );
        assertEquals("0",response.path("errcode").toString());
    }

    @DisplayName("推送群聊信息")
    @Description("推送群聊信息测试用例")
    @Story("推送群聊信息")
    @Test
    void sendMessage(){
        Response response = GroupChatObject.sendGroupMessage(accessToken, chatId, "你好\\n你的快递到了请在前台查收","text");
        assertEquals("0",response.path("errcode").toString());
    }
}
