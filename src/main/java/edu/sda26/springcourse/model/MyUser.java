package edu.sda26.springcourse.model;

import jakarta.persistence.*;
import lombok.*;


@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
@Table(name = "Users")
public class MyUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;
    private String username;
    private String password;
    private String role;
    private String email;
}
