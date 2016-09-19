package eu.antoniomunoz.codingchallenge.services;

import eu.antoniomunoz.codingchallenge.dao.CurrencySearchRepository;
import eu.antoniomunoz.codingchallenge.model.Account;
import eu.antoniomunoz.codingchallenge.model.CurrencyResult;
import eu.antoniomunoz.codingchallenge.model.CurrencySearch;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CurrencySearchServiceImplTest {

    @InjectMocks
    private CurrencySearchServiceImpl currencySearchService = new CurrencySearchServiceImpl();

    @Mock
    private CurrencyService currencyServiceMock;

    @Mock
    private AccountService accountServiceMock;

    @Mock
    private CurrencySearchRepository currencySearchRepositoryMock;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Captor
    ArgumentCaptor<Pageable> captorPageable;


    private Account account = new Account();

    @Before
    public void init() {
        Authentication authentication = Mockito.mock(Authentication.class);
        account.setEmail("email@email.com");

        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);

        Mockito.when(accountServiceMock.findUserByEmail(any())).thenReturn(account);
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    public void testGetLatestSearches(){
        currencySearchService.getLatestSearches(10);

        verify(currencySearchRepositoryMock, times(1)).findByUserOrderByIdDesc(eq(account), captorPageable.capture());
        assertEquals(captorPageable.getValue().getPageSize(), 10);

    }

    @Test
    public void testOneDataFindAndSaveSearch() {
        String currency = "EUR";
        Double expectedRateValue = 2.01;

        CurrencyResult expectedResult = new CurrencyResult();
        Map<String, Double> expectedRates = new HashMap<>();
        expectedRates.put(currency, expectedRateValue);
        expectedResult.setRates(expectedRates);

        when(currencyServiceMock.getRateFromUSD(null)).thenReturn(expectedResult);

        CurrencySearch result = currencySearchService.findAndSaveSearch(null, currency);

        verify(currencyServiceMock, times(1)).getRateFromUSD(null);
        verify(currencySearchRepositoryMock, times(1)).save(result);
        assertNull(result.getRateDate());
        assertEquals(result.getCurrency(), currency);
        assertEquals(result.getRateValue(), expectedRateValue);
    }



}
