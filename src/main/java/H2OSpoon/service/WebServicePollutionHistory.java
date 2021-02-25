package H2OSpoon.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    /**
     * during the labcamp the hostname will be shared via chat.
     * please be sure to change hostUrl from "http://localhost:8080/" to the shared information
     */
    public static String hostUrl = "http://localhost:8080";

    public Double getBenzenelagNumber(String lagi) {
        RestTemplate restTemplate = new RestTemplate();
        String resourceUrl = "/api/benzene/{lagi}";
        ResponseEntity<Double> response
                = restTemplate.getForEntity(resourceUrl, Double.class, lagi);
        logger.debug("benzene_lag{} recorded value is {}", lagi, response.getBody());
        return response.getBody();
    }

    public Double getBenzenelagNumber(Integer lagi){
        return this.getBenzenelagNumber(Integer.toString(lagi));
    }

    public Double getTitanialagNumber(String lagi) {
        RestTemplate restTemplate = new RestTemplate();
        String resourceUrl = "/api/titania/{lagi}";
        ResponseEntity<Double> response
                = restTemplate.getForEntity(resourceUrl, Double.class, lagi);
        logger.debug("titania_lag{} recorded value is {}", lagi, response.getBody());
        return response.getBody();
    }

    public Double getTitanialagNumber(Integer lagi) {
        return this.getTitanialagNumber(Integer.toString(lagi));
    }

}
