package eu.antoniomunoz.codingchallenge.dao;

import eu.antoniomunoz.codingchallenge.model.Account;
import eu.antoniomunoz.codingchallenge.model.CurrencySearch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CurrencySearchRepository extends JpaRepository<CurrencySearch, Long> {

    Page<CurrencySearch> findByUserOrderByIdDesc(Account user, Pageable pageable);

}