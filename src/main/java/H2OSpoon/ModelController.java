package H2OSpoon;

import H2OSpoon.service.ReadCsv;
import hex.genmodel.GenModel;
import hex.genmodel.easy.RowData;
import hex.genmodel.easy.exception.PredictException;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import javax.xml.ws.Service;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "api/", produces = {MediaType.APPLICATION_JSON_VALUE,
        "application/hal+json", MediaType.APPLICATION_JSON_VALUE})
public class ModelController {

    static Logger logger = LoggerFactory.getLogger("ModelController");

    @Autowired
    ApplyModel applyModel;

    /**
     * complete the method.
     * 1) recover the GenModel implemented in the POJO you have generated through H2O
     * 2) insert all couples (fieldName, value) inside the RowData istance
     */
    @PostMapping("predict")
    public Double predictResult(@RequestBody(required = true) RowDataDTO body) throws Exception {

        applyModel.init(body.getModelName());
        RowData row = new RowData();
        /*
        //solution:
        for(String name : applyModel.getModel().m.getNames()){
            if(body.getNameValueMap().containsKey(name)){
                row.put(name, body.getNameValueMap().get(name));
            }
        }
         */
        return applyModel.predictedValue(row);
    }

    @Autowired
    ReadCsv readCsv;

    /*
    complete this method to perform multiple predictions.
    The import values should be importes from a csv from a known location
    To facilitate this task you may find helpful the methods contained inside the ReadCsv class we declared a few lines before
    */
    @GetMapping("predictFromXls")
    public List<Double> predictMultipleResults(@RequestParam(required = true) String filePath,
                                               @RequestParam(required = true) String modelName) throws IllegalAccessException, InstantiationException, ClassNotFoundException, IOException, PredictException {
        applyModel.init(modelName);
        List<Double> results = new ArrayList<>();
        /* sol:
        List<RowData> rows = readCsv.toRowData(readCsv.getExcelFileAsWorkbook(filePath));
        for(RowData row : rows) {
            results.add(applyModel.predictedValue(row));
        }
        */
        return results;

    }

    @GetMapping("model")
    public List<String> listModels(){
        List<String> modelsName = new ArrayList<>();
        Reflections reflections =  new Reflections(
                "H2OSpoon", new SubTypesScanner());

        //Reflections reflections = new Reflections("java.H2OSpoon");
        Set<Class<? extends GenModel>> classes = reflections.getSubTypesOf(GenModel.class);
        for (Class<? extends GenModel> aClass : classes) {
            modelsName.add(aClass.getName());
        }
        return modelsName;
    }

    @GetMapping("model/{modelName}")
    public ModelFeatures getDetails(@PathParam("modelName") String modelName){
        ModelFeatures feat = new ModelFeatures();
        try {
            applyModel.init(modelName);
            feat.setModelName(modelName);
            for(String name : applyModel.getModel().m.getNames()){
                feat.getFieldInputNames().add(name);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        return feat;
    }
}
