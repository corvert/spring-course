package edu.sda26.springcourse.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountCreationRequestDto {
    private Long id;
    private Double balance;
    private Long customerId;


}
