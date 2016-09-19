package eu.antoniomunoz.codingchallenge.controllers;

import eu.antoniomunoz.codingchallenge.controllers.forms.RateForm;
import eu.antoniomunoz.codingchallenge.model.CurrencySearch;
import eu.antoniomunoz.codingchallenge.services.CurrencySearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.List;

@Controller
@Secured({"ROLE_USER"})
public class CurrencyController {

    private static final String VIEW_NAME = "home/homeSignedIn";
    private static final String INVALID_CURRENCY_MESSAGE = "notBlank.message";


    @Value("#{'${app.currenciesShown}'.split(',')}")
    private List<String> currenciesShown;

    @Value("${app.numberOfResults}")
    private int numberOfResults;

    @Autowired
    private CurrencySearchService currencySearchService;

    @RequestMapping(value = "/calculate")
    public String signup(Model model) {
        model.addAttribute(new RateForm());
        commonViewObjects(model);
        return VIEW_NAME;
    }

    private void commonViewObjects(Model model) {
        model.addAttribute("currencies", currenciesShown);
        Page<CurrencySearch> latestSearches = currencySearchService.getLatestSearches(numberOfResults);
        model.addAttribute("latestSearches", latestSearches.getContent());
    }

    @RequestMapping(value = "/calculate", method = RequestMethod.POST)
    public String calculate(@Valid @ModelAttribute RateForm rateForm, Errors errors, Model model) {

        if(rateForm != null && rateForm.getCurrency() != null){
            if(!currenciesShown.contains(rateForm.getCurrency())){
                errors.rejectValue("currency", INVALID_CURRENCY_MESSAGE);
            }
        }
        if (!errors.hasErrors()) {
            CurrencySearch result = currencySearchService.findAndSaveSearch(rateForm.getRateDate(), rateForm.getCurrency());
            if(result != null){
                model.addAttribute("lastSearch", result);
            }
        }
        commonViewObjects(model);
        return VIEW_NAME;
    }
}
