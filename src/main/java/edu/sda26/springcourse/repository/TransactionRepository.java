package edu.sda26.springcourse.repository;

import edu.sda26.springcourse.model.Transaction;
import edu.sda26.springcourse.model.enums.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findAllByAccountId(Long accountID);

}
