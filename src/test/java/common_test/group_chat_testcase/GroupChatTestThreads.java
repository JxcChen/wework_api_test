package common_test.group_chat_testcase;

import common_test.api_object.GroupChatObject;
import common_test.api_object.TokenHelper;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.parallel.Execution;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.parallel.ExecutionMode.CONCURRENT;

public class GroupChatTestThreads {

    static ArrayList<String> userList = new ArrayList<>();
    static String chatId;
    static String accessToken;
    @BeforeAll
    public static void init(){
        accessToken = TokenHelper.getToken("wwbcc92e0afe51b09e", "r7S5zFDDm_OvmrGmGJR4IMNpCMCMjfa4wfrsGD7lO-g");
    }

    @DisplayName("创建群聊")
    @RepeatedTest(100)
    @Execution(CONCURRENT)
    void createChatTest(){
        userList.add("ChenJinXuan");
        userList.add("XiaoChen");
        Response response = GroupChatObject.createGroupChat("群聊", "XiaoChen", "003", userList, accessToken);
        assertEquals("0",response.path("errcode").toString());
    }


    @DisplayName("修改群聊")
    @RepeatedTest(100)
    @Execution(CONCURRENT)
    void updateGroupChatTest(){
        Response response = GroupChatObject.updateGroupChat("003", "只是群聊", "ChenJinXuan", accessToken);
        assertEquals("0",response.path("errcode").toString());
    }


    @DisplayName("获取群聊会话")
    @RepeatedTest(100)
    @Execution(CONCURRENT)
    void getChatSessionTest(){
        Response response = GroupChatObject.getChatSession(accessToken, "003" );
        assertEquals("0",response.path("errcode").toString());
    }

    @DisplayName("推送群聊消息")
    @RepeatedTest(100)
    @Execution(CONCURRENT)
    void sendMessage(){
        Response response = GroupChatObject.sendGroupMessage(accessToken, "003", "你好\\n你的快递到了请在前台查收","text");
        assertEquals("0",response.path("errcode").toString());
    }
}
