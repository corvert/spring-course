package edu.sda26.springcourse.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Name cant be empty")
    private String name;
    private Integer age;
    private String phone;
    @Email(message = "Wrong email format wrong")
    private String email;
    private Boolean active;
    private String password;


}
