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

    static Logger logger = LoggerFactory.getLogger("PredictFromWebCommand");

    String modelName;

    public PredictFromWebCommand(String modelName) {
        this.modelName = modelName;
    }

    public void execute() throws Exception {
        throw new UnsupportedOperationException("to be completed");
    }
}
