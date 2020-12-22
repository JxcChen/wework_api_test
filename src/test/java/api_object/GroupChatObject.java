package api_object;

import io.restassured.response.Response;

import java.util.ArrayList;

import static io.restassured.RestAssured.given;

public class GroupChatObject {



    /**
     * 创建群聊
     * @param GroupName 群名称
     * @param ownerId 拥有者ID
     * @param chatId 群ID
     * @param userList 群成员列表
     * @param accessToken token
     * @return Response
     */
    public static Response createGroupChat(String GroupName, String ownerId, String chatId, ArrayList<String> userList, String accessToken){
        String userIdListStr = listToString(userList);
        if (userIdListStr!=null) {
            String body = "{\n" +
                    "    \"name\" : \"" + GroupName + "\",\n" +
                    "    \"owner\" : \"" + ownerId + "\",\n" +
                    "    \"userlist\" : "+userIdListStr+",\n" +
                    "    \"chatid\" : \"" + chatId + "\"\n" +
                    "}";
            return given()
                    .contentType("application/json")
                    .body(body)
                    .post("https://qyapi.weixin.qq.com/cgi-bin/appchat/create?access_token=" + accessToken)
                    .then()
                    .log()
                    .all()
                    .extract()
                    .response()
                    ;
        }
        return null;
    }

    /**
     * 创建测试数据
     * @param accessToken token
     * @param userList 用户列表
     * @return
     */
    public static String createGroupChat(String accessToken, ArrayList<String> userList){
        String userIdListStr = listToString(userList);
        if (userIdListStr!=null) {
            String body = "{\n" +
                    "    \"userlist\" : "+userIdListStr+"\n" +
                    "}";
            return given()
                    .contentType("application/json")
                    .body(body)
                    .post("https://qyapi.weixin.qq.com/cgi-bin/appchat/create?access_token=" + accessToken)
                    .then()
                    .log()
                    .all()
                    .extract()
                    .response()
                    .path("chatid")
                    ;
        }
        return null;
    }

    /**
     * 修改群聊信息
     * @param chatId
     * @param updateName
     * @param ownerId
     * @param accessToken
     * @return
     */
    public static Response updateGroupChat(String chatId, String updateName, String ownerId, String accessToken){
        String body = "{\n" +
                "    \"chatid\" : \""+chatId+"\",\n" +
                "    \"name\" : \""+updateName+"\",\n" +
                "    \"owner\" : \""+ownerId+"\"\n" +
                "}";
        return given()
                .contentType("application/json")
                .body(body)
                .post("https://qyapi.weixin.qq.com/cgi-bin/appchat/update?access_token="+accessToken)
                .then()
                .log()
                .all()
                .extract()
                .response()
                ;
    }

    /**
     * 获取群聊会话
     * @param accessToken
     * @param charId
     * @return
     */
    public static Response getChatSession(String accessToken,String charId){
        return given()
                .queryParams("access_token",accessToken,"chatid",charId)
                .get("https://qyapi.weixin.qq.com/cgi-bin/appchat/get")
                .then()
                .log()
                .all()
                .extract()
                .response()
                ;
    }

    /**
     * 推送消息
     * @param accessToken
     * @param chatId
     * @param content
     * @return
     */
    public static Response sendGroupMessage(String accessToken,String chatId,String content,String msgType){
        // todo：先判断消息类型 再做消息推送。。。。
        String body = "{\n" +
                "    \"chatid\": \""+chatId+"\",\n" +
                "    \"msgtype\":\""+msgType+"\",\n" +
                "    \"text\":{\n" +
                "        \"content\" : \""+content+"\"\n" +
                "    },\n" +
                "    \"safe\":0\n" +
                "}"
                ;
        return given()
                .contentType("application/json")
                .body(body)
                .post("https://qyapi.weixin.qq.com/cgi-bin/appchat/send?access_token="+accessToken)
                .then()
                .log()
                .all()
                .extract()
                .response()
                ;
    }

    /**
     * 将list装成可以写进请求body的字符串
     * @param list 需要转换的list
     * @return 转换后的list
     */
    public static String listToString(ArrayList<String> list){
        String listStr = null;
        if (list.size()>1) {
            listStr = "[";
            for (int i = 0; i < list.size(); i++) {
                if (i == list.size() - 1) {
                    listStr += "\"" + list.get(i) + "\"]";
                    break;
                }
                listStr += "\"" + list.get(i) + "\",";
            }
        }
        return listStr;
    }
}
