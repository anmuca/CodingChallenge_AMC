package eu.antoniomunoz.codingchallenge.model;

import java.util.Date;
import java.util.Map;

public class CurrencyResult {

    private Date searchDate;
    private Date rateDate;
    private Map<String, Double> rates;

    public Date getSearchDate() {
        return searchDate;
    }

    public void setSearchDate(Date searchDate) {
        this.searchDate = searchDate;
    }

    public Date getRateDate() {
        return rateDate;
    }

    public void setRateDate(Date rateDate) {
        this.rateDate = rateDate;
    }

    public Map<String, Double> getRates() {
        return rates;
    }

    public void setRates(Map<String, Double> rates) {
        this.rates = rates;
    }
}
