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
 */
public class ApiLoader {

    static Logger logger = LoggerFactory.getLogger(ApiLoader.class);

    private static ArrayList<ApiObjectModel> apiObjectModelArrayList = new ArrayList<>();


    /**
     * 加载目录下所有 yaml文件 并储存到apiObjectModelArrayList中
     * @param dirPath 目标目录
     */
    public static void load(String dirPath){
        // 遍历目录下所有文件  都进行反序列化
        Arrays.stream(new File(dirPath).list()).forEach(filePath -> {
            try {
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
        // 遍历apiObjectModelArrayList 过滤出name = apiName的model
        // 再在model中获取到name = actionName 的action
        apiObjectModelArrayList.stream().filter(model -> model.getName().equals(apiName)).forEach(model ->{
            apiActionModel[0] = model.getActions().get(actionName);
        });

        if (apiActionModel[0] !=null){
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
