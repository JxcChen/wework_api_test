package group_chat_testcase;

import api_object.GroupChatObject;
import api_object.TokenHelper;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

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

    @Test
    void createChatTest(){
        userList.add("ChenJinXuan");
        userList.add("XiaoChen");
        Response response = GroupChatObject.createGroupChat("第二个群聊", "XiaoChen", "002", userList, accessToken);
        assertEquals("0",response.path("errcode").toString());
    }


    @Test
    void updateGroupChatTest(){
        Response response = GroupChatObject.updateGroupChat(chatId, "只是群聊", "ChenJinXuan", accessToken);
        assertEquals("0",response.path("errcode").toString());
    }


    @Test
    void getChatSessionTest(){
        Response response = GroupChatObject.getChatSession(accessToken, chatId );
        assertEquals("0",response.path("errcode").toString());
    }

    @Test
    void sendMessage(){
        Response response = GroupChatObject.sendGroupMessage(accessToken, chatId, "你好\\n你的快递到了请在前台查收","text");
        assertEquals("0",response.path("errcode").toString());
    }
}
