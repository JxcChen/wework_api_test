package demo;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

// XmlPath 断言
public class Test_02 {


    // 断言对应节点值
    @Test
    void test_01() {
        given()
                .get("http://localhost:8000/rest-assured.xml")
                .then()
                .body("shopping.category[0].item[1].name", equalTo("Coffee"));
    }

    // 断言对应节点的数量
    // 节点.size() 获取节点数量
    @Test
    void test_02() {
        given()
                .get("http://localhost:8000/rest-assured.xml")
                .then()
                .body("shopping.category.size()", equalTo(3));
    }


    // 根据属性获取值并进行断言
    // findAll{ it.@属性名 == 属性值 }
    // findAll{ it.子元素名 == 子元素值 }
    @Test
    void test_03() {
        given()
                .get("http://localhost:8000/rest-assured.xml")
                .then()
                .body("shopping.category.findAll{it.@type=='supplies'}.item[0].name", equalTo("Paper"))
                .body("shopping.category.item.findAll{it.price==10}.name", equalTo("Chocolate"))
                .body("shopping.category.item.findAll{it.name=='Coffee'}.price", equalTo("20"))
        ;
    }

    // **.findAll 忽略前节点 直接找对应属性值的所有目标节点
    @Test
    void test_04() {
        given()
                .get("http://localhost:8000/rest-assured.xml")
                .then()
                .body("**.findAll{it.@type=='supplies'}[0].item[0].name", equalTo("Paper"))

        ;
    }


    // **.find 忽略前节点 直接找对对应属性值的所有目标节点的第一个
    @Test
    void test_05() {
        given()
                .get("http://localhost:8000/rest-assured.xml")
                .then()
                .body("**.find{it.@type=='supplies'}.item[0].name", equalTo("Paper"))

        ;
    }
}

