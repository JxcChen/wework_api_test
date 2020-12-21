package group_chat_testcase;

import api_object.TokenHelper;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class GroupChatTest {

    @Test
    void createChat(){
        String accessToken = TokenHelper.getToken("wwbcc92e0afe51b09e", "N-kbyGJh1FEeCrrf9KmQL_iExeGtpgaa1j12mwl8F9w");
        String body = "{\n" +
                "    \"name\" : \"群聊\",\n" +
                "    \"owner\" : \"ChenJinXuan\",\n" +
                "    \"userlist\" : [\"ChenJinXuan\", \"XiaoChen\"],\n" +
                "    \"chatid\" : \"001\"\n" +
                "}";
        given()
                .contentType("application/json")
                .body(body)
                .post("https://qyapi.weixin.qq.com/cgi-bin/appchat/create?access_token="+accessToken)
                .then()
                .log()
                .all()
                .extract()
                .response();
    }
}
