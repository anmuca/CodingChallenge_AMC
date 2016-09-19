package eu.antoniomunoz.codingchallenge.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.Instant;
import java.util.Date;

@SuppressWarnings("serial")
@Entity
@Table(name = "account")
public class Account implements java.io.Serializable {

	@Id
	@GeneratedValue
	@Column(name = "ID")
	private Long id;

	@Column(unique = true, name = "EMAIL")
	private String email;

    @JsonIgnore
	@Column(name = "PASSWORD")
    private String password;

	@Column(name = "BIRTHDATE")
	private Date birthDate;

	@Column(name = "STREET")
	private String street;

	@Column(name = "ZIPCODE")
	private String zipCode;

	@Column(name = "CITY")
	private String city;

    @ManyToOne
	@JoinColumn(name = "COUNTRY_ID")
    private Country country;

	@Transient
	private String role = "ROLE_USER";

	@Column(name= "CREATION_DATE")
	private Instant created;

	public Long getId() {
		return id;
	}

    public String getEmail() {
		return email;
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

	public String getRole() {
		return role;
	}

	public Instant getCreated() {
		return created;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

    public void setId(Long id) {
        this.id = id;
    }

    public void setCreated(Instant created) {
        this.created = created;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }
}
