package eu.antoniomunoz.codingchallenge.services;

import eu.antoniomunoz.codingchallenge.exceptions.CurrencyServiceException;
import eu.antoniomunoz.codingchallenge.exceptions.RatesWSNotAvailable;
import eu.antoniomunoz.codingchallenge.model.CurrencyResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.PostConstruct;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class CurrencyServiceImpl implements CurrencyService{

    @Value("${ratesService.url}")
    private String ratesServiceUrl;

    @Value("${ratesService.paramIdentifier}")
    private String ratesServiceParamIdentifier;

    @Value("${ratesService.appId}")
    private String ratesServiceAppId;

    @Value("${ratesService.urlServiceLatest}")
    private String ratesServiceUrlServiceLatest;

    @Value("${ratesService.urlServiceHistorical}")
    private String ratesServiceUrlServiceHistorical;

    private String completeServiceURL;
    private RestTemplate restTemplate;

    private static final String SERVICE = "\\{service\\}";
    private static final String DATE = "\\{date\\}";
    private static final SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd");

    @PostConstruct
    public void init(){
        String url = UriComponentsBuilder.fromPath(ratesServiceUrl)
                        .queryParam(ratesServiceParamIdentifier, ratesServiceAppId).build().toUriString()
        ;
        completeServiceURL = url.replaceFirst("/", "//");

        restTemplate = new RestTemplate();
    }

    @Override
    public CurrencyResult getRateFromUSD(Date time) {
        CurrencyJson webServiceResult = null;

        if(time != null && time.after(new Date())){
            throw new CurrencyServiceException("The system cannot find rates in the future");
        }

        try {
            String url;
            if(time == null) {
                url = completeServiceURL.replaceAll(SERVICE, ratesServiceUrlServiceLatest);
            }else{
                url = completeServiceURL.replaceAll(SERVICE,
                        ratesServiceUrlServiceHistorical.replaceAll(DATE, sdf.format(time)));
            }
            webServiceResult = restTemplate.getForObject(url, CurrencyJson.class);
        }catch(Exception e){
            throw new RatesWSNotAvailable(e);
        }

        if(webServiceResult == null){
            throw new RatesWSNotAvailable("Web service returned empty result");
        }

        CurrencyResult currencyResult = new CurrencyResult();

        currencyResult.setRateDate(time);
        currencyResult.setSearchDate(new Date());
        currencyResult.setRates(webServiceResult.getRates());

        return currencyResult;
    }



    void setRatesServiceUrl(String ratesServiceUrl) {
        this.ratesServiceUrl = ratesServiceUrl;
    }

    void setRatesServiceParamIdentifier(String ratesServiceParamIdentifier) {
        this.ratesServiceParamIdentifier = ratesServiceParamIdentifier;
    }

    void setRatesServiceAppId(String ratesServiceAppId) {
        this.ratesServiceAppId = ratesServiceAppId;
    }

    void setRatesServiceUrlServiceLatest(String ratesServiceUrlServiceLatest) {
        this.ratesServiceUrlServiceLatest = ratesServiceUrlServiceLatest;
    }

    void setRatesServiceUrlServiceHistorical(String ratesServiceUrlServiceHistorical) {
        this.ratesServiceUrlServiceHistorical = ratesServiceUrlServiceHistorical;
    }
}



class CurrencyJson{
    private String base;
    private Map<String, Double> rates;

    public CurrencyJson() {
       rates = new HashMap<>();
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

        public Map<String, Double> getRates() {
            return rates;
        }

        public void setRates(Map<String, Double> rates) {
            this.rates = rates;
        }
}

