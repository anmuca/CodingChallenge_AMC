package eu.antoniomunoz.codingchallenge.services;

import eu.antoniomunoz.codingchallenge.model.CurrencySearch;
import org.springframework.data.domain.Page;

import java.util.Date;

public interface CurrencySearchService {

    CurrencySearch findAndSaveSearch(Date date, String currency);

    Page<CurrencySearch> getLatestSearches(int numberOfResults);
}
