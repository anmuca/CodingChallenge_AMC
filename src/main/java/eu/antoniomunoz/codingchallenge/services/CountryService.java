package eu.antoniomunoz.codingchallenge.services;

import eu.antoniomunoz.codingchallenge.model.Country;

import java.util.List;

public interface CountryService {

    List<Country> findAll();

    Country findById(Long countryId);
}
