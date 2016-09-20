package eu.antoniomunoz.codingchallenge.controllers;

import eu.antoniomunoz.codingchallenge.dao.CountryRepository;
import eu.antoniomunoz.codingchallenge.exceptions.CurrencyServiceException;
import eu.antoniomunoz.codingchallenge.model.Country;
import eu.antoniomunoz.codingchallenge.model.CurrencyResult;
import eu.antoniomunoz.codingchallenge.services.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@Controller
public class StatusController {

    @Autowired
    private CurrencyService currencyService;

    @Autowired
    private CountryRepository countryRepository;

    @RequestMapping(value = "status", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public ResponseEntity<Status> getStatus(){
        Status status = new Status();

        try{
            CurrencyResult currencyResult = currencyService.getRateFromUSD(null);
            if(currencyResult == null || currencyResult.getRates() == null || currencyResult.getRates().isEmpty()){
                throw new CurrencyServiceException("The web service returns empty results");
            }
        }catch(Throwable e){
            status.setAllOK(false);
            status.setWebServiceConnection("ERROR: " + e.getMessage());
        }

        try{
            List<Country> countries = countryRepository.findAll();
            if(countries == null || countries.isEmpty()){
                throw new Exception("No countries returned");
            }
        }catch(Throwable e){
            status.setAllOK(false);
            status.setDatabase("ERROR: " + e.getMessage());
        }

        HttpStatus httpStatus = HttpStatus.OK;
        if(!status.getAllOK()){
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        ResponseEntity<Status> result = new ResponseEntity<Status>(status, httpStatus);
        return result;
    }

}

class Status{
    private Boolean allOK;
    private String database;
    private String webServiceConnection;

    private static final String OK = "OK";

    public Status() {
        allOK = true;
        database = OK;
        webServiceConnection = OK;
    }

    public Boolean getAllOK() {
        return allOK;
    }

    public void setAllOK(Boolean allOK) {
        this.allOK = allOK;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public String getWebServiceConnection() {
        return webServiceConnection;
    }

    public void setWebServiceConnection(String webServiceConnection) {
        this.webServiceConnection = webServiceConnection;
    }
}


