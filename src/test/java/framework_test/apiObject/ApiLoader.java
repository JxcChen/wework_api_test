package framework_test.apiObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Project: wework_api_test
 * Created by JJJJ on 2020-12-27 11:16
 * Description: 获取所有api-yaml配置文件 并运行run方法
 *
 * todo: 1、将对应目录中的所有yaml文件进行反序列化  2、获取对应的apiActionModel 并执行apiActionModel.run方法
 * 创建load方法 传入目录路径参数
 * load方法实现：
 * 1 遍历目录下的所有文件
 * 2 调用apiObjectModel中的load方法 传入遍历后的文件路径 获取到反序列化的结果
 * 3 将得到的反序列化结果存入apiObjectModelArrayList中
 * 创建run方法 传入需要的apiName  和  actionName
 * run方法实现：
 * 1 遍历apiObjectModelArrayList获取到每个apiObjectModel
 * 2 根据传入的apiName 和 actionName 获取到目标apiObjectModel
 * 3 返回目标apiObjectModel
 */
public class ApiLoader {

    static Logger logger = LoggerFactory.getLogger(ApiLoader.class);

    private static ArrayList<ApiObjectModel> apiObjectModelArrayList = new ArrayList<>();


    /**
     * 加载目录下所有 yaml文件 并储存到apiObjectModelArrayList中
     * @param dirPath 目标目录
     */
    public static void load(String dirPath){
        // 1 遍历目录下的所有文件
        Arrays.stream(new File(dirPath).list()).forEach(filePath -> {
            try {
                // 2 调用apiObjectModel中的load方法 传入遍历后的文件路径 获取到反序列化的结果
                // 3 将得到的反序列化结果存入apiObjectModelArrayList中
                apiObjectModelArrayList.add(ApiObjectModel.load(dirPath+"/"+filePath));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }


    /**
     * 获取目标apiActionModel
     * @param apiName 接口名
     * @param actionName 接口下对应的action名
     * @return  apiActionModel
     */
    public static ApiActionModel run(String apiName, String actionName){
        final ApiActionModel[] apiActionModel = {new ApiActionModel()};
        // 1 遍历apiObjectModelArrayList获取到每个apiObjectModel
        // 2 根据传入的apiName 和 actionName 获取到目标apiObjectModel
        apiObjectModelArrayList.stream().filter(model -> model.getName().equals(apiName)).forEach(model ->{
            apiActionModel[0] = model.getActions().get(actionName);
        });

        if (apiActionModel[0] !=null){
            // 3 返回目标apiObjectModel
            return apiActionModel[0];
        }else {
            logger.error("没有找到"+actionName+"对应的action");
        }

        return null;
    }


    public static ArrayList<ApiObjectModel> getApiObjectModelArrayList() {
        return apiObjectModelArrayList;
    }

    public static void setApiObjectModelArrayList(ArrayList<ApiObjectModel> apiObjectModelArrayList) {
        ApiLoader.apiObjectModelArrayList = apiObjectModelArrayList;
    }
}
