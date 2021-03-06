package H2OSpoon;

import H2OSpoon.command.PredictFromWebCommand;
import H2OSpoon.command.PredictFromXlsxCommand;
import H2OSpoon.service.*;
import hex.genmodel.GenModel;
import hex.genmodel.easy.RowData;
import hex.genmodel.easy.exception.PredictException;
import io.swagger.v3.oas.annotations.Operation;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

@RestController
@RequestMapping(value = "api/", produces = {MediaType.APPLICATION_JSON_VALUE,
        "application/hal+json", MediaType.APPLICATION_JSON_VALUE})
public class ModelController {

    static Logger logger = LoggerFactory.getLogger("ModelController");

    @Autowired
    BeanFactory beanFactory;

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
    @Operation(description = "Apply the H2O Model identified by its name to the input data contained inside the body of the request, returns the predicted value")
    public ResponseEntity<Double> predictResult(@RequestBody(required = true) RowDataDTO body) throws Exception {
        ApplyModel applyModel = beanFactory.getBean(ApplyModel.class);
        applyModel.init(body.getModelName());
        RowData row = new RowData();
        //##
        // populate the variable row
        //##
        return ResponseEntity.ok(applyModel.predictedValue(row));
    }

    /*
    Exercise 3:
    complete the command method to perform multiple predictions.
    The import values should be importes from an excel file from a known location,indicated as a path
    (its value depends on where you placed your input file).
    To facilitate this task you may find helpful the methods contained inside the ReadExcel class
    */
    @Autowired
    Environment environment;

    @GetMapping("predictFromXls")
    @Operation(description = "Perform several predictions using an excel file as source")
    public ResponseEntity<List<Double>> predictMultipleResults(@RequestParam(required = true) String modelName) throws Exception {
        if(!environment.containsProperty("source.excel.path")){
            throw new IllegalStateException("please include a path for the source data file (in excel format)");
        }
        String filePath = environment.getProperty("source.excel.path");

        PredictFromXlsxCommand command = beanFactory.getBean(PredictFromXlsxCommand.class, modelName, filePath);
        List<Double> results = command.execute();

        return ResponseEntity.ok(results);

    }

    @GetMapping("model")
    @Operation(description = "List the name of all H2O models inside the H2OSpoon package")
    public ResponseEntity<List<String>> listModels() {
        List<String> modelsName = new ArrayList<>();
        Reflections reflections = new Reflections(
                "H2OSpoon", new SubTypesScanner());

        Set<Class<? extends GenModel>> classes = reflections.getSubTypesOf(GenModel.class);
        for (Class<? extends GenModel> aClass : classes) {
            modelsName.add(aClass.getName());
        }
        return ResponseEntity.ok(modelsName);
    }

    @GetMapping("model/{modelName}")
    @Operation(description = "List the name of all input variables required by a specific model")
    public ResponseEntity<ModelFeatures> getDetails(@PathParam("modelName") String modelName) {
        ModelFeatures feat = new ModelFeatures();
        try {
            ApplyModel applyModel = beanFactory.getBean(ApplyModel.class);
            applyModel.init(modelName);
            feat.setModelName(modelName);
            for (String name : applyModel.getRawModel().getNames()) {
                feat.getFieldInputNames().add(name);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok(feat);
    }

    /**
     * Exercise 4:
     * create ex novo an endpoint that will apply predictions to an input retrived from an online microservice
     * The link and endpoint are shared during the exercise part of the labcamp.
     * We suggest a look to Spring tutorial: https://spring.io/guides/gs/consuming-rest/
     * as an excellent starting point for this part.
     * 1) define a new pubblic method that returns a ResponseEntity<Double> and requires a String modelName as input
     * 2) Annotate the endpoint through an annotation such as @GetMapping(<nome endpoint>)
     * 3) initialize the command class PredictFromWebCommand as done in the other method
     * 4) inside the command class initialize ApplyModel instance
     * 5) To retrieve the input values for each lag of benzene and titanium, have a look at the WebServicePollutionHistory and its exposed methods getBenzenelagNumber(String lagi) and getTitanialagNumber(String lagi).
     *    Those methods make a request toward an online service for the registered value of benzene/titanium for the last 48 hours
     * 6) Create a new variable Rowdata and put all input values for your model.
     * 7) Invoke the predictedValue method to recall the model for the prepared info and return the value
     */


}
