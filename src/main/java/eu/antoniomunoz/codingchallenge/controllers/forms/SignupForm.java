package eu.antoniomunoz.codingchallenge.controllers.forms;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.util.Date;

public class SignupForm {

	private static final String NOT_BLANK_MESSAGE = "{notBlank.message}";
	private static final String EMAIL_MESSAGE = "{email.message}";
	private static final String PAST_DATE = "{pastDate.message}";
    private static final String MAX_SIZE_ZIPCODE = "{signupForm.zipcode.maxSize.message}";

    @NotBlank(message = SignupForm.NOT_BLANK_MESSAGE)
	@Email(message = SignupForm.EMAIL_MESSAGE)
	private String email;

    @NotBlank(message = SignupForm.NOT_BLANK_MESSAGE)
	private String password;

	@NotNull(message = SignupForm.NOT_BLANK_MESSAGE)
	@DateTimeFormat(pattern="dd/MM/yyyy")
	@Past(message = SignupForm.PAST_DATE)
	private Date birthDate;

    @NotBlank(message = SignupForm.NOT_BLANK_MESSAGE)
	private String street;

    @NotBlank(message = SignupForm.NOT_BLANK_MESSAGE)
    @Size(max = 8, message = SignupForm.MAX_SIZE_ZIPCODE)
    private String zipCode;

    @NotBlank(message = SignupForm.NOT_BLANK_MESSAGE)
    private String city;

    @NotNull(message = SignupForm.NOT_BLANK_MESSAGE)
    private Long countryId;

    public String getEmail() {
		return email;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Long getCountryId() {
        return countryId;
    }

    public void setCountryId(Long countryId) {
        this.countryId = countryId;
    }
}
