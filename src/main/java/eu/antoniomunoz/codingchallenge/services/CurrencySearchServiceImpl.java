package eu.antoniomunoz.codingchallenge.services;

import eu.antoniomunoz.codingchallenge.dao.CurrencySearchRepository;
import eu.antoniomunoz.codingchallenge.model.Account;
import eu.antoniomunoz.codingchallenge.model.CurrencyResult;
import eu.antoniomunoz.codingchallenge.model.CurrencySearch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class CurrencySearchServiceImpl implements CurrencySearchService {

    @Autowired
    private CurrencyService currencyService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private CurrencySearchRepository currencySearchRepository;

    @Override
    public CurrencySearch findAndSaveSearch(Date date, String currency) {
        CurrencyResult currencyResult = currencyService.getRateFromUSD(date);
        Double rate = currencyResult.getRates().get(currency);

        CurrencySearch currencySearch = new CurrencySearch();

        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        Account userAccount = accountService.findUserByEmail(userEmail);
        currencySearch.setUser(userAccount);
        currencySearch.setSearchDate(currencyResult.getSearchDate());
        currencySearch.setRateDate(currencyResult.getRateDate());
        currencySearch.setCurrency(currency);
        currencySearch.setRateValue(rate);

        currencySearchRepository.save(currencySearch);

        return currencySearch;
    }

    @Override
    public Page<CurrencySearch> getLatestSearches(int numberOfResults) {
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        Account userAccount = accountService.findUserByEmail(userEmail);

        return currencySearchRepository.findByUserOrderByIdDesc(userAccount, new PageRequest(0, numberOfResults));
    }
}
