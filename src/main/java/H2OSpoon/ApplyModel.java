package H2OSpoon;

import hex.genmodel.GenModel;
import hex.genmodel.easy.EasyPredictModelWrapper;
import hex.genmodel.easy.RowData;
import hex.genmodel.easy.exception.PredictException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;

/**
 * Wrapper class for a EasyPredictModelWrapper,
 * described inside the documentation at https://docs.h2o.ai/h2o/latest-stable/h2o-genmodel/javadoc/index.html
 * This class initilizes the model variable based on the name it has been given,
 * allowing for test purpose online to call multiple models to be called without the need to restart the application.
 * In a production context it would be better to instantiate one instance through @Autowired annotation
 * and re-deploy the microservice for each update of the model
 */
@Component
public class ApplyModel {

    static Logger logger = LoggerFactory.getLogger("ModelController");

    public String modelClassName;
    private EasyPredictModelWrapper model;

    public ApplyModel() {
    }

    public void init(String modelClassName) throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        logger.info("initializing model {}", modelClassName);
        this.modelClassName = modelClassName;
        GenModel rawModel = (hex.genmodel.GenModel) Class.forName(modelClassName).getDeclaredConstructor().newInstance();
        this.model = new EasyPredictModelWrapper(rawModel);
    }

    public EasyPredictModelWrapper getModel() {
        return model;
    }

    public GenModel getRawModel(){ return model.m; }

    /*
    Exercise 1:
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
        logger.info("Predictied {} from {} and input {}", "##fillMeIn!##", modelClassName, row);
        return null;
    }
}
