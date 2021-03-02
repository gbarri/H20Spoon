package H2OSpoon.command;

import H2OSpoon.ApplyModel;
import H2OSpoon.service.ReadExcel;
import hex.genmodel.easy.RowData;
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

    @Autowired
    BeanFactory beanFactory;

    @Autowired
    ReadExcel readExcel;

    String modelName;
    String filePath;

    public PredictFromXlsxCommand(String modelName, String filePath) {
        this.modelName = modelName;
        this.filePath = filePath;
    }

    public List<Double> execute() throws Exception {
        ApplyModel applyModel = beanFactory.getBean(ApplyModel.class);
        applyModel.init(modelName);
        List<Double> results = new ArrayList<>();
        //sol:
        List<RowData> rows = readExcel.toRowData(readExcel.getExcelFileAsWorkbook(filePath));
        for(RowData row : rows) {
            results.add(applyModel.predictedValue(row));
        }
        return results;
    }
}
