package framework_test.apiObject;



import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

/**
 * Project: wework_api_test
 * Created by JJJJ on 2020-12-27 10:27
 * Description: api反序列化的对象
 *
 * todo: 将api_yaml中内容进行反序列化
 * 1 声明 yaml对应变量
 * 2 创一个load方法 传入yaml文件路径
 * load 方法实现
 * 1 加载yaml文件进行反序列化
 * 2 将反序列化后的结果对象进行返回
 */
public class ApiObjectModel {
    private String name;
    private HashMap<String , ApiActionModel> actions;

    /**
     * 加载yaml文件数据
     * @param path yaml文件路劲
     * @return ApiObjectModel
     * @throws IOException
     */
    public static ApiObjectModel load(String path) throws IOException {
        // 对yaml文件进行反序列化
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        return mapper.readValue(new File(path),ApiObjectModel.class);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HashMap<String, ApiActionModel> getActions() {
        return actions;
    }

    public void setAction(HashMap<String, ApiActionModel> actions) {
        this.actions = actions;
    }


}
