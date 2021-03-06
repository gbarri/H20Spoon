package H2OSpoon;

import java.util.ArrayList;
import java.util.List;

/**
 * a very simple example of model class: an intutive rappresentation of the input value of a model,
 * defined by the model name and variables fields
 */
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
