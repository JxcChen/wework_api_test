package api_object;

import static io.restassured.RestAssured.given;

/**
 * 获取管理部门的token
 */
public class TokenHelper {
    /**
     * 获取token
     * @param corpId 企业ID
     * @param corpSecret 秘钥
     */
    public static String getToken(String corpId,String corpSecret){
        return given()
                .queryParams("corpid", corpId, "corpsecret", corpSecret)
                .get("https://qyapi.weixin.qq.com/cgi-bin/gettoken")
                .then()
                .extract()
                .path("access_token");
    }


}
