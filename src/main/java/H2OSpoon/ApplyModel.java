package H2OSpoon;

import hex.genmodel.GenModel;
import hex.genmodel.easy.EasyPredictModelWrapper;
import hex.genmodel.easy.RowData;
import hex.genmodel.easy.exception.PredictException;
import hex.genmodel.easy.prediction.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class ApplyModel {

    static Logger logger = LoggerFactory.getLogger("ModelController");

    public String modelClassName;
    private EasyPredictModelWrapper model;

    public ApplyModel() {
    }

    public void init(String modelClassName) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        logger.info("initializing model {}", modelClassName);
        this.modelClassName = modelClassName;
        GenModel rawModel = (hex.genmodel.GenModel) Class.forName(modelClassName).newInstance();
        this.model = new EasyPredictModelWrapper(rawModel);
    }

    public EasyPredictModelWrapper getModel() {
        return model;
    }

    public GenModel getRawModel(){ return model.m; }

    /*
        this method is used to effectively determine the prediction of the model for the RowData given.
        Which type is correct depends on the used model.
        Considering your trained model, which one is correct?
        decomment the right one to complete the method and return its value
     */
    public Double predictedValue(RowData row) throws PredictException {
        //OrdinalModelPrediction predicted = model.predictOrdinal(row);
        //AnomalyDetectionPrediction predicted = model.predictAnomalyDetection(row);
        //MultinomialModelPrediction predicted = model.predictMultinomial(row);
        //ClusteringModelPrediction predicted = model.predictClustering(row);
        //BinomialModelPrediction predicted = model.predictBinomial(row);
        //RegressionModelPrediction predicted = model.predictRegression(row);
        //DimReductionModelPrediction predicted = model.predictDimReduction(row);
        //Word2VecPrediction predicted = model.predictWord2Vec(row);
        //KLimeModelPrediction predicted = model.predictKLime(row);
        return null;
    }
}