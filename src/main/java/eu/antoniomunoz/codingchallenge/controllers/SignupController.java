package eu.antoniomunoz.codingchallenge.controllers;

import eu.antoniomunoz.codingchallenge.controllers.forms.SignupForm;
import eu.antoniomunoz.codingchallenge.model.Account;
import eu.antoniomunoz.codingchallenge.model.Country;
import eu.antoniomunoz.codingchallenge.services.AccountService;
import eu.antoniomunoz.codingchallenge.services.CountryService;
import eu.antoniomunoz.codingchallenge.support.web.MessageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class SignupController {

    private static final String SIGNUP_VIEW_NAME = "signup/signup";
    private static final String INVALID_COUNTRY_MESSAGE = "notBlank.message";

	@Autowired
	private AccountService accountService;

    @Autowired
    private CountryService countryService;

	@RequestMapping(value = "signup")
	public String signup(Model model) {
		model.addAttribute(new SignupForm());
        model.addAttribute("countries", countryService.findAll());
        return SIGNUP_VIEW_NAME;
	}
	
	@RequestMapping(value = "signup", method = RequestMethod.POST)
	public String signup(@Valid @ModelAttribute SignupForm signupForm, Errors errors, RedirectAttributes ra, Model model) {
        Country country = null;
        if(signupForm != null && signupForm.getCountryId() != null){
            country = countryService.findById(signupForm.getCountryId());
            if(country == null){
                errors.rejectValue("countryId", INVALID_COUNTRY_MESSAGE);
            }
        }

		if (errors.hasErrors()) {
            model.addAttribute("countries", countryService.findAll());
            return SIGNUP_VIEW_NAME;
		}

		Account newAccount = new Account();
		newAccount.setEmail(signupForm.getEmail());
		newAccount.setPassword(signupForm.getPassword());
        newAccount.setBirthDate(signupForm.getBirthDate());
        newAccount.setStreet(signupForm.getStreet());
        newAccount.setCity(signupForm.getCity());
        newAccount.setZipCode(signupForm.getZipCode());
        newAccount.setCountry(country);

		Account account = accountService.save(newAccount);
		accountService.signin(account);

        MessageHelper.addSuccessAttribute(ra, "signup.success");
		return "redirect:/";
	}
}
