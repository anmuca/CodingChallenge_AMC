package eu.antoniomunoz.codingchallenge.services;

import eu.antoniomunoz.codingchallenge.model.Account;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface AccountService {
    Account save(Account account);

    Account findUserByEmail(String username) throws UsernameNotFoundException;

    void signin(Account account);

}
