package H2OSpoon;

import java.util.ArrayList;
import java.util.List;

public class ModelFeatures {
    private String modelName;
    private List<String> fieldInputNames = new ArrayList<>();
    private List<String> getFieldOutputNames = new ArrayList<>();

/*    FARE la lettura da un csv!
    esercizio su intepretare la predizione
   grafici in funzione di una delle 5 variabili??
*/

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

    public List<String> getGetFieldOutputNames() {
        return getFieldOutputNames;
    }

    public void setGetFieldOutputNames(List<String> getFieldOutputNames) {
        this.getFieldOutputNames = getFieldOutputNames;
    }
}
