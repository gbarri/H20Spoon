package H2OSpoon.command;

import H2OSpoon.ApplyModel;
import H2OSpoon.service.ReadExcel;
import hex.genmodel.easy.RowData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

@Component
@Scope("prototype")
public class PredictFromXlsxCommand {

    static Logger logger = LoggerFactory.getLogger("PredictFromXlsxCommand");

    @Autowired
    BeanFactory beanFactory;

    /**
     * an instance of a Service autowired directly inside the controller.
     * For a properly configured class (see the @Service annotation inside class ReadExcel)
     * Spring instantiate automatically the variable readExcel with an instance of the service class ReadExcel.
     * We do not need to manually instantiate it via the keyword "new".
     * You may find additional information and a more complete description of this mechanics at
     * https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#beans-basics
     */
    @Autowired
    ReadExcel readExcel;

    String modelName;
    String filePath;

    public PredictFromXlsxCommand(String modelName, String filePath) {
        this.modelName = modelName;
        this.filePath = filePath;
    }

    public List<Double> execute() throws Exception {

        //STEP 1: initialize an instance of ApplyModel like done before

        List<Double> results = new ArrayList<>();
        //STEP 2: add code to
        //  read from excel file (use the defined ReadExcel field with autowiring)
        //STEO 3: populate a RowData from each line you've read
        //        make a prediction and include the predicted value inside results
        return results;
    }
}
