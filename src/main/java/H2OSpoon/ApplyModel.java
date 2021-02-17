package H2OSpoon;

import hex.genmodel.GenModel;
import hex.genmodel.easy.EasyPredictModelWrapper;
import hex.genmodel.easy.RowData;
import hex.genmodel.easy.exception.PredictException;
import hex.genmodel.easy.prediction.AbstractPrediction;
import hex.genmodel.easy.prediction.BinomialModelPrediction;
import hex.genmodel.easy.prediction.RegressionModelPrediction;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class ApplyModel {

    public String modelClassName;
    private EasyPredictModelWrapper model;

    public ApplyModel() {
    }

    public void init(String modelClassName) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        this.modelClassName = modelClassName;
        GenModel rawModel = (hex.genmodel.GenModel) Class.forName(modelClassName).newInstance();
        this.model = new EasyPredictModelWrapper(rawModel);
    }

    public EasyPredictModelWrapper getModel() {
        return model;
    }

    public double predictedValue(RowData row) throws PredictException {
        RegressionModelPrediction regValue = model.predictRegression(row);
        return regValue.value;
    }
}
