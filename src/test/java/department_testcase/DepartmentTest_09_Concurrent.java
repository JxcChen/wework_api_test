package department_testcase;

import api_object.DepartmentObject;
import api_object.TokenHelper;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.parallel.Execution;
import utils.FakeUtils;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.parallel.ExecutionMode.CONCURRENT;


/**
 * 分布式多线程操作用例
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DepartmentTest_09_Concurrent {
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
    @RepeatedTest(20)
    void crateDepartmentTest(){
        // 创建部门
        departmentName = "name" + FakeUtils.getTimeMillis()+Thread.currentThread().getId();
        departmentEnName = "enName" + FakeUtils.getTimeMillis()+Thread.currentThread().getId();
        Response response = DepartmentObject.createDepartment(departmentName,departmentEnName,accessToken);
        assertAll("校验相应内容",
                ()-> assertEquals("0",response.path("errcode").toString())
        );

    }

    @DisplayName("修改部门")
    @Test
    @Execution(CONCURRENT)  //CONCURRENT表示支持多线程
    @RepeatedTest(20)
    void updateDepartmentTest(){
        String exName = Thread.currentThread().getId()+"";
        departmentName = "name" + exName;
        departmentEnName = "enName" + exName;
        departmentId = DepartmentObject.createDepartment(departmentName,departmentEnName,accessToken).path("id").toString();
        // 修改部门
        String updateDepartmentName = "Name" + exName;
        String updateDepartmentEnName = "EnName" + exName;
        Response response = DepartmentObject.updateDepartment(departmentId,updateDepartmentName,updateDepartmentEnName,accessToken);
        assertEquals("0",response.path("errcode").toString());
    }






}
