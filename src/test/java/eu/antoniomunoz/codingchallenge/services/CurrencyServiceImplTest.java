package eu.antoniomunoz.codingchallenge.services;

import eu.antoniomunoz.codingchallenge.exceptions.CurrencyServiceException;
import eu.antoniomunoz.codingchallenge.exceptions.RatesWSNotAvailable;
import eu.antoniomunoz.codingchallenge.model.Account;
import eu.antoniomunoz.codingchallenge.model.CurrencyResult;
import eu.antoniomunoz.codingchallenge.services.CurrencyService;
import eu.antoniomunoz.codingchallenge.services.CurrencyServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.temporal.TemporalUnit;
import java.util.*;

@RunWith(MockitoJUnitRunner.class)
public class CurrencyServiceImplTest {

	@InjectMocks
	private CurrencyServiceImpl currencyService = new CurrencyServiceImpl();

    @InjectMocks
    private CurrencyServiceImpl currencyServiceWrongConfig = new CurrencyServiceImpl();

	@Rule
	public ExpectedException thrown = ExpectedException.none();


	@Before
	public void prepare() {
		MockitoAnnotations.initMocks(this);
        currencyService.setRatesServiceUrl("http://openexchangerates.org/api/{service}.json");
		currencyService.setRatesServiceAppId("05bdfa0ebc1a4e4ca6d61b0d66c3bdd6");
        currencyService.setRatesServiceParamIdentifier("app_id");
        currencyService.setRatesServiceUrlServiceHistorical("historical/{date}");
        currencyService.setRatesServiceUrlServiceLatest("latest");
        currencyService.init();

        currencyServiceWrongConfig.setRatesServiceUrl("http://openexchangerates.org/api/{service}.json");
        currencyServiceWrongConfig.setRatesServiceAppId("asdfasdf");
        currencyServiceWrongConfig.setRatesServiceParamIdentifier("app_id");
        currencyServiceWrongConfig.setRatesServiceUrlServiceHistorical("historical/{date}");
        currencyServiceWrongConfig.setRatesServiceUrlServiceLatest("latest");
        currencyServiceWrongConfig.init();
	}

	@Test
    public void getRateFromUSDShouldReturnCurrencyNow(){
        CurrencyResult result = currencyService.getRateFromUSD(null);
        Assert.assertNotNull(result);
        Assert.assertNull(result.getRateDate());
        Assert.assertFalse(result.getRates().isEmpty());
    }

    @Test
    public void getRateFromUSDShouldReturnCurrencyTomorrow(){
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.add(Calendar.DAY_OF_MONTH, 1);

        thrown.expect(CurrencyServiceException.class);
        CurrencyResult result = currencyService.getRateFromUSD(calendar.getTime());

    }

    @Test
    public void getRateFromUSDShouldReturnCurrencyOneMonthAgo(){
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.add(Calendar.MONTH, -1);

        CurrencyResult result = currencyService.getRateFromUSD(calendar.getTime());
        Assert.assertNotNull(result);
        Assert.assertEquals(result.getRateDate(), calendar.getTime());
        Assert.assertFalse(result.getRates().isEmpty());
    }

    @Test
    public void getRateFromUSDShouldFailWrongConfiguration(){
        thrown.expect(RatesWSNotAvailable.class);
        currencyServiceWrongConfig.getRateFromUSD(null);
    }



}
