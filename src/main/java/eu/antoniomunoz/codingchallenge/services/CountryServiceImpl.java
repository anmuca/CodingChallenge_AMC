package eu.antoniomunoz.codingchallenge.services;

import eu.antoniomunoz.codingchallenge.dao.CountryRepository;
import eu.antoniomunoz.codingchallenge.model.Country;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CountryServiceImpl implements CountryService {

    @Autowired
    private CountryRepository countryRepository;

    @Override
    public List<Country> findAll() {
        return countryRepository.findAll();
    }

    @Override
    public Country findById(Long countryId) {
        return countryRepository.findOne(countryId);
    }
}
