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
