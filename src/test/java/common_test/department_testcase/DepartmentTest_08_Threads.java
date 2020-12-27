package common_test.department_testcase;

import common_test.api_object.DepartmentObject;
import common_test.api_object.TokenHelper;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.parallel.Execution;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.parallel.ExecutionMode.CONCURRENT;


/**
 * 多线程操作用例 查看需要进行唯一性排重时是否会出现脏读问题
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DepartmentTest_08_Threads {
    static String accessToken;
    static String departmentId;
    static String departmentName;
    static String departmentEnName;
    @BeforeAll
    public static void init(){
        // 企业ID ： wwbcc92e0afe51b09e  secret ： MmNdXbFeCNiPJEztv1Kd1TqW6e3Gy6BBgPVRWDJa9fI  agentId ： 3010084
        // 获取token
        accessToken = TokenHelper.getToken("wwbcc92e0afe51b09e","MmNdXbFeCNiPJEztv1Kd1TqW6e3Gy6BBgPVRWDJa9fI");
        // 清空根部门下的子部门
        // 获取跟部门下的子部门列表
        ArrayList<Integer> idList = DepartmentObject.getDepartmentList(accessToken,"1").path("department.id");
        // 逐个部门删除
        if (idList != null) {
            idList.forEach(id -> {
                if(id != 1) {
                    DepartmentObject.deleteDepartment(accessToken, id.toString());
                }
            });
        }
    }


    @DisplayName("创建部门")
    @Test
    @Execution(CONCURRENT)  //CONCURRENT表示支持多线程
    @RepeatedTest(100)
    void crateDepartmentTest(){
        // 创建部门
        departmentName = "name";
        departmentEnName = "enName";
        Response response = DepartmentObject.createDepartment(departmentName,departmentEnName,accessToken);
        assertAll("校验相应内容",
                ()-> assertEquals("0",response.path("errcode").toString())
        );

    }

    @DisplayName("修改部门")
    @Test
    @Order(2)
    void updateDepartmentTest(){
        // 修改部门
        Response response = DepartmentObject.updateDepartment(departmentId,"研究分院","ggg",accessToken);
        assertEquals("0",response.path("errcode").toString());
    }


    @DisplayName("搜索部门")
    @Test
    @Order(3)
    void searchDepartmentTest(){
        // 搜索部门
        Response response = DepartmentObject.getDepartmentList(accessToken,departmentId);
        System.out.println(response.body().toString());
        assertEquals("0",response.path("errcode").toString());
    }

    @DisplayName("删除部门")
    @Test
    @Order(4)
    void deleteDepartmentTest(){
        // 删除部门
        Response response = DepartmentObject.deleteDepartment(accessToken,departmentId);
        assertEquals("0",response.path("errcode").toString());
    }

}
