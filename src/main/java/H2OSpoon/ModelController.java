package H2OSpoon;

import H2OSpoon.service.ReadCsv;
import H2OSpoon.service.WebServicePollutionHistory;
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
import org.springframework.http.ResponseEntity;
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
     * Exercise 2:
     * complete the method.
     * The predictResult is meant to perform a prediction based on the given model for the input data RawDataDTO.
     * To do so you must insert all couples (fieldName, value) inside the RowData istance named, in this example, row.
     * The names of the input files according to the model can be retrieved calling the method getNames() given by the GenModel class.
     * 1) retrieve the genModel from the class EasyPredictModelWrapper initialized in ApplyModel class
     * 2) retrieve the model fields names
     * 3) insert the couples (fieldName, value) inside the row data instance
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

    /**
     * an instance of a Service autowired directly inside the controller.
     * For a properly configured class (see the @Service annotaion inside class ReadCsv)
     * Spring instantiate automatically the variable readCsv with an instance of the service class ReadCsv.
     * We do not need to manually instantiate it via the keyword "new".
     * You may find additional information and a more complete description of this mechanics at
     * https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#beans-basics
     */
    @Autowired
    ReadCsv readCsv;

    /*
    Exercise 3:
    complete this method to perform multiple predictions.
    The import values should be importes from a csv from a known location,indicated as a path
    (its value depends on where you placed your input file).
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
    public List<String> listModels() {
        List<String> modelsName = new ArrayList<>();
        Reflections reflections = new Reflections(
                "H2OSpoon", new SubTypesScanner());

        Set<Class<? extends GenModel>> classes = reflections.getSubTypesOf(GenModel.class);
        for (Class<? extends GenModel> aClass : classes) {
            modelsName.add(aClass.getName());
        }
        return modelsName;
    }

    @GetMapping("model/{modelName}")
    public ModelFeatures getDetails(@PathParam("modelName") String modelName) {
        ModelFeatures feat = new ModelFeatures();
        try {
            applyModel.init(modelName);
            feat.setModelName(modelName);
            for (String name : applyModel.getModel().m.getNames()) {
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

    /**
     * Exercise 4:
     * create ex novo an endpoint that will apply predictions to an input retrived from an online microservice
     * The link and endpoint are shared during the exercise part of the labcamp.
     * We suggest a look to Spring tutorial: https://spring.io/guides/gs/consuming-rest/
     * as an excellent starting point for this part.
     * 1) define the endpoint through an annotation such as @GetMapping(endpoint)
     * 2) have a look at the WebServicePollutionHistory and its methods getBenzenelagNumber(String lagi) and getTitanialagNumber(String lagi)
     * that make a request toward an online service for the registered value of benzene/titania for the last 48 hours
     * 3) similarly to what have been done in Exercise 3, initialize the model and fill a RowData instance with the recovered values.
     * 4) Invoke the predictedValue method to recall the model for the prepared info
     */

    /*
    //solution:
    @Autowired
    WebServicePollutionHistory history;

    //TODO check variables names!!
    @GetMapping("onlinePrediction")
    public ResponseEntity<Double> getOnlinePrediction(String modelName) throws IllegalAccessException, InstantiationException, ClassNotFoundException, PredictException {
        applyModel.init(modelName);
        RowData row = new RowData();
        String benzene = "benzene_lag";
        for (int i = 1; i <= 48; i++) {
            Double value = history.getBenzenelagNumber(i);
            row.put(benzene + Integer.toString(i), value);
        }
        String titania = "titania_lag";
        for (int i = 1; i <= 48; i++) {
            Double value = history.getTitanialagNumber(i);
            row.put(titania + Integer.toString(i), value);
        }
        Double predictedValue = applyModel.predictedValue(row);
        logger.info("predicted value for benzene is {}", predictedValue);
        return ResponseEntity.ok(predictedValue);
    }
    */

}
