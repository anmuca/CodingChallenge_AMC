package eu.antoniomunoz.codingchallenge.controllers.forms;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.util.Date;

public class RateForm {

    private static final String PAST_DATE = "{pastDate.message}";
    private static final String NOT_BLANK_MESSAGE = "{notBlank.message}";

    @DateTimeFormat(pattern="dd/MM/yyyy")
    @Past(message = RateForm.PAST_DATE)
    private Date rateDate;

    @NotNull(message = RateForm.NOT_BLANK_MESSAGE)
    private String currency;

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Date getRateDate() {
        return rateDate;
    }

    public void setRateDate(Date rateDate) {
        this.rateDate = rateDate;
    }
}
