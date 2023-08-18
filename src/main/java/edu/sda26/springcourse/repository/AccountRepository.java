package edu.sda26.springcourse.repository;

import edu.sda26.springcourse.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    Account findByCustomerId(Long id);
    List<Account> findAllByCustomerId(Long id);
    List<Account> findAllByBalanceGreaterThan(Double balance);
    List<Account> findAllByBalanceGreaterThanAndBalanceLessThan(Double min, Double max);

}
