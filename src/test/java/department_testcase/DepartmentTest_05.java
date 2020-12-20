package department_testcase;

import api_object.DepartmentObject;
import api_object.TokenHelper;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;

import java.util.ArrayList;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 * 基础用例编写 ： 创建部门、修改部门、查询部门、删除部门 缺点：只能全部用例一起执行，无法单个用例单独执行
 * 初步优化： 单个用例能够独立执行  缺点：每个用例执行完需要手动删除页面数据才能再次执行用例
 * 优化： 解决单个用例无法重复执行 解决办法一 ：利用时间戳动态设置部门名  解决办法二： 用例前置清除数据  缺点：代码重复，不易维护
 * 分层优化： 封装重复代码，增加代码复用性，减少维护成本  缺点： 入参不灵活
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DepartmentTest_05 {
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


    @BeforeEach
    void createDepartment(){
        Response createResponse = DepartmentObject.createDepartment(accessToken);
        departmentId = createResponse.path("id") != null ? createResponse.path("id").toString() : null;
    }

    @Test
    @Order(1)
    void crateDepartmentTest(){
        // 创建部门
        departmentName = "研发分院";
        departmentEnName = "RDGZ";
        Response response = DepartmentObject.createDepartment(departmentName, departmentEnName, accessToken);
        assertEquals("0",response.path("errcode").toString());
    }

    @Test
    @Order(2)
    void updateDepartmentTest(){
        // 修改部门
        Response response = DepartmentObject.updateDepartment(departmentId,"研究分院","ggg",accessToken);
        assertEquals("0",response.path("errcode").toString());
    }

    @Test
    @Order(3)
    void searchDepartmentTest(){
        // 搜索部门
        Response response = DepartmentObject.getDepartmentList(accessToken,departmentId);
        System.out.println(response.body().toString());
        assertEquals("0",response.path("errcode").toString());
    }

    @Test
    @Order(4)
    void deleteDepartmentTest(){
        // 删除部门
        Response response = DepartmentObject.deleteDepartment(accessToken,departmentId);
        assertEquals("0",response.path("errcode").toString());
    }
}
