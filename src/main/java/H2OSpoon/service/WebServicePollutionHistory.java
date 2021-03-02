package H2OSpoon.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


/**
 * This class is a service class that is meant to manage the connection with a second microservice which provides
 * the registered values of the last 48h of the chemicals Benzene and Titania.
 * Notice that to be properly autowired in the controller it must be annotated with the @Service, or @Component annotaion.
 * Without the first run will terminate with an exception due to an unsatisfied dependency for a "WebServicePollutionHistory" class ("required a bean of type 'H2OSpoon.service.WebServicePollutionHistory'")
 * * @Service and @Component annoation allows Spring context to discover this class and istamtiate it
 * Be sure to uncomment the following line!
 */
//@Service
public class WebServicePollutionHistory {

    Logger logger = LoggerFactory.getLogger("WebServicePollutionHistory");

    @Autowired
    Environment environment;

    /**
     * during the labcamp the hostname will be shared via chat.
     * please be sure to change hostUrl inside application.properties from "http://localhost:8080/" to the shared information
     */

    private String retrieveHost(){
        if(environment.containsProperty("source.externalms.host")){
            return environment.getProperty("source.externalms.host");
        }else {
            return "##control the application.properties file##";
        }
    }

    public Double getBenzenelagNumber(String lagi) {
        RestTemplate restTemplate = new RestTemplate();
        String resourceUrl = retrieveHost()+"/api/benzene/{lagi}";
        ResponseEntity<Double> response
                = restTemplate.getForEntity(resourceUrl, Double.class, lagi);
        logger.debug("benzene_lag{} recorded value is {}", lagi, response.getBody());
        return response.getBody();
    }

    public Double getBenzenelagNumber(Integer lagi){
        return this.getBenzenelagNumber(Integer.toString(lagi));
    }

    /**
     * exercise 4:
     * the remote mciroservice exposes two apis to retrieve the titanim concentration with a given lag
     * In a similar fashion to what has been done with the benzene implement a method that retrieves the titanium levels
     * 1) find the correct api to recall
     * 2) as above use a RestTemplate to make a request to the defined endpoint
     */
    public Double getTitaniumLagNumber(String lagi) {
        throw new UnsupportedOperationException("must be completed");
        /*
         //sol:
        RestTemplate restTemplate = new RestTemplate();
        String resourceUrl = retrieveHost()+"/api/titanium/{lagi}";
        ResponseEntity<Double> response
                = restTemplate.getForEntity(resourceUrl, Double.class, lagi);
        logger.debug("titanium_lag{} recorded value is {}", lagi, response.getBody());
        return response.getBody();
        */
    }

    public Double getTitaniumLagNumber(Integer lagi) {
        return this.getTitaniumLagNumber(Integer.toString(lagi));
    }

}
