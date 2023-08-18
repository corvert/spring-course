package edu.sda26.springcourse.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
@Getter
@Setter
public class TransactionDto {
    private Long id;
    private double amount;
    private String transactionType;
    private LocalDate transactionDate;

}
