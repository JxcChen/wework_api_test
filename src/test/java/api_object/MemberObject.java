package api_object;

;

import io.restassured.response.Response;
import utils.FakeUtils;

import java.util.ArrayList;
import java.util.Random;

import static io.restassured.RestAssured.given;

public class MemberObject {


    /**
     * 添加成员
     * @param userId 用户ID 唯一
     * @param userName 用户名
     * @param mobile 手机 唯一
     * @param departmentId 所属部门ID
     * @param accessToken token
     * @return Response
     */
    public static Response addMember(String userId, String userName, String mobile, String departmentId, String accessToken){
        String body =
                "{\n" +
                        "    \"userid\": \""+userId+"\",\n" +
                        "    \"name\": \""+userName+"\",\n" +
                        "    \"mobile\": \"+86 "+mobile+"\",\n" +
                        "    \"department\": ["+departmentId+"],\n" +
                        "}" ;

        return given()
                .contentType("application/json")
                .body(body)
                .post("https://qyapi.weixin.qq.com/cgi-bin/user/create?access_token="+accessToken)
                .then()
                .log()
                .all()
                .extract()
                .response()
        ;
    }

    /**
     * 添加成员 并获取到成员ID 获取测试数据使用
     * @param accessToken token
     * @param departmentId 所属部门ID
     * @return userId
     */
    public static String addUserAndGetUserId(String accessToken,String departmentId){
        // 构造参数 确保唯一性
        String userId = "userId" + FakeUtils.getTimeMillis();
        String userName = "userName" + FakeUtils.getTimeMillis();
        Random random = new Random();
        String mobile = "130000" + (int)((Math.random()*9+1)*100000);
        System.out.println(mobile);
        addMember(userId, userName, mobile, departmentId, accessToken);
        return userId;
    }

    /**
     * 删除成员
     * @param accessToken token
     * @param userID 用户ID
     * @return Response
     */
    public static Response deleteMember(String accessToken,String userID){
        return given()
                .queryParams("access_token",accessToken,"userid",userID)
                .get("https://qyapi.weixin.qq.com/cgi-bin/user/delete")
                .then()
                .log()
                .all()
                .extract()
                .response();
    }


    /**
     * 读取单个用户信息
     * @param accessToken token
     * @param userId 用户ID
     * @return Response
     */
    public static Response getUserMsg(String accessToken,String userId){
        return given()
                .queryParams("access_token", accessToken, "userid", userId)
                .get("https://qyapi.weixin.qq.com/cgi-bin/user/get")
                .then()
                .log()
                .all()
                .extract()
                .response();
    }

    /**
     * 获取部门成员列表
     * @param accessToken token
     * @param department_id 所属部门ID
     * @return Response
     */
    public static Response getMemberList(String accessToken, String department_id){
        return given()
                .queryParams("access_token", accessToken, "department_id", department_id)
                .get("https://qyapi.weixin.qq.com/cgi-bin/user/simplelist")
                .then()
                .log()
                .all()
                .extract()
                .response();
    }

    /**
     * 获取部门下的所有成员ID列表
     * @param accessToken token
     * @param department_id 所属部门ID
     * @return ArrayList<String>
     */
    public static ArrayList<String> getDepartmentUserIdList(String accessToken, String department_id){
        return getMemberList(accessToken, department_id).path("userlist.userid");
    }

    /**
     * 批量删除成员
     * @param accessToken token
     * @param userIdList 成员ID 列表
     * @return response
      */
    public static Response batchDeleteMember(String accessToken,ArrayList<String> userIdList){
        // 封装需要删除的userId
        String userIdListStr = "[";
        for (int i = 0; i < userIdList.size(); i++) {
            if (i == userIdList.size()-1 ){
                userIdListStr += "\""+userIdList.get(i)+"\"]";
                break;
            }
            userIdListStr += "\""+userIdList.get(i)+"\",";
        }
        String body = "{\n" +
                "   \"useridlist\": "+userIdListStr+"\n" +
                "}";
        return given()
                .contentType("application/json")
                .body(body)
                .post("https://qyapi.weixin.qq.com/cgi-bin/user/batchdelete?access_token="+accessToken)
                .then()
                .log()
                .all()
                .extract()
                .response();


    }


}
