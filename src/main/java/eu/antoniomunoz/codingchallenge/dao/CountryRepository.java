package eu.antoniomunoz.codingchallenge.dao;

import eu.antoniomunoz.codingchallenge.model.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CountryRepository extends JpaRepository<Country, Long> {

}