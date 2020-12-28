package demo;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;

// XmlPath 断言
public class Test_03 {


    // 断言对应节点值
    @Test
    void test_01(){
        given()
                .get("http://localhost:8000/rest-assured.json")
                .then()
                .log().all()
                .body(matchesJsonSchemaInClasspath("json_schema.json"));
    }





}
