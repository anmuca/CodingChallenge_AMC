package eu.antoniomunoz.codingchallenge.services;

import eu.antoniomunoz.codingchallenge.model.CurrencyResult;

import java.util.Date;

public interface CurrencyService {

    CurrencyResult getRateFromUSD(Date time);


}
