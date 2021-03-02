package H2OSpoon.command;

import H2OSpoon.ApplyModel;
import H2OSpoon.service.ReadExcel;
import H2OSpoon.service.WebServicePollutionHistory;
import hex.genmodel.easy.RowData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Scope("prototype")
public class PredictFromWebCommand {

    @Autowired
    BeanFactory beanFactory;

    @Autowired
    WebServicePollutionHistory history;

    static Logger logger = LoggerFactory.getLogger("PredictFromWebCommand");

    String modelName;

    public PredictFromWebCommand(String modelName) {
        this.modelName = modelName;
    }

    public Double execute() throws Exception {
        ApplyModel applyModel = beanFactory.getBean(ApplyModel.class);
        applyModel.init(modelName);

        logger.info("retrieving pollutions records ...");
        RowData row = new RowData();
        String benzene = "benzene_lag";
        for (int i = 1; i <= 48; i++) {
            Double value = history.getBenzenelagNumber(i);
            row.put(benzene + Integer.toString(i), value);
        }
        String titanium = "titanium_lag";
        for (int i = 1; i <= 48; i++) {
            Double value = history.getTitaniumLagNumber(i);
            row.put(titanium + Integer.toString(i), value);
        }
        logger.info("retrieving pollutions records done");

        Double predictedValue = applyModel.predictedValue(row);
        logger.info("predicted value for benzene is {}", predictedValue);
        return predictedValue;
    }
}
