package H2OSpoon;

import java.util.ArrayList;
import java.util.List;

public class ModelFeatures {
    private String modelName;
    private List<String> fieldInputNames = new ArrayList<>();

    public ModelFeatures() {
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public List<String> getFieldInputNames() {
        return fieldInputNames;
    }

    public void setFieldInputNames(List<String> fieldInputNames) {
        this.fieldInputNames = fieldInputNames;
    }

}
