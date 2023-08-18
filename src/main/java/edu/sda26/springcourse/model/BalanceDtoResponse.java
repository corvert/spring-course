package edu.sda26.springcourse.model;

import edu.sda26.springcourse.model.Account;
import edu.sda26.springcourse.model.Transaction;
import jakarta.persistence.Entity;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Builder
public class BalanceDtoResponse {
    Transaction transaction;
    Account account;
}
