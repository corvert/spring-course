package edu.sda26.springcourse.config;

import edu.sda26.springcourse.model.Users;
import edu.sda26.springcourse.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests((authorize) -> authorize
                        .anyRequest()
                        .authenticated()
                )
                .httpBasic(withDefaults())
                .formLogin(withDefaults());
        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Autowired
            private UsersRepository usersRepository;

            @Autowired
            private PasswordEncoder passwordEncoder;

            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                Users users = usersRepository.findByUsername(username);

                if (users == null) {
                    throw new UsernameNotFoundException("No user found with username: " + username);
                }
                boolean enabled = true;
                boolean accountNonExpired = true;
                boolean credentialsNonExpired = true;
                boolean accountNonLocked = true;
                String password = passwordEncoder.encode(users.getPassword());

                User user =
                        new org.springframework.security.core.userdetails.User(
                                users.getUsername(), password,
                                enabled, accountNonExpired,
                                credentialsNonExpired, accountNonLocked,
                                getAuthorities(List.of("USER", "ADMIN")));
                return user;
            }

            private List<GrantedAuthority> getAuthorities(List<String> roles) {
                List<GrantedAuthority> authorities = new ArrayList<>();
                for (String role : roles) {
                    authorities.add(new SimpleGrantedAuthority(role));
                }
                return authorities;
            }
        };

    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
