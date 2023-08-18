package edu.sda26.springcourse.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerCreateRequestDto {
    private String name;
    private Integer age;
    private String phone;
    private String email;


}
