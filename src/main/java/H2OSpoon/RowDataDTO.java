package H2OSpoon;

import java.util.List;
import java.util.Map;

public class RowDataDTO {
    private String modelName;
    private Map<String, Double> nameValueMap;

    public RowDataDTO() {
    }

    public RowDataDTO(String modelName, Map<String, Double> nameValueMap) {
        this.modelName = modelName;
        this.nameValueMap = nameValueMap;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public Map<String, Double> getNameValueMap() {
        return nameValueMap;
    }

    public void setNameValueMap(Map<String, Double> nameValueMap) {
        this.nameValueMap = nameValueMap;
    }

    @Override
    public String toString() {
        return "RowDataDTO{" +
                "modelName='" + modelName + '\'' +
                ", nameValueMap=" + nameValueMap +
                '}';
    }
}
