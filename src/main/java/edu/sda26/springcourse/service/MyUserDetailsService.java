//package edu.sda26.springcourse.service;
//
//import edu.sda26.springcourse.model.MyUser;
//import edu.sda26.springcourse.model.Users;
//
//import edu.sda26.springcourse.repository.UsersRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Service
//public class MyUserDetailsService implements UserDetailsService {
//
//    @Autowired
//    private UsersRepository userRepository;
//
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//
//        Users myUser = userRepository.findByUserName(username);
//
//        if (myUser == null) {
//            throw new UsernameNotFoundException("No user found with username: " + username);
//        }
//        boolean enabled = true;
//        boolean accountNonExpired = true;
//        boolean credentialsNonExpired = true;
//        boolean accountNonLocked = true;
//        String  password = passwordEncoder.encode(myUser.getPassword());
//
//        return new org.springframework.security.core.userdetails.User(
//                myUser.getUsername(), password, enabled, accountNonExpired,
//                credentialsNonExpired, accountNonLocked, getAuthorities(List.of("USER")));
//    }
//
//    private static List<GrantedAuthority> getAuthorities (List<String> roles) {
//        List<GrantedAuthority> authorities = new ArrayList<>();
//        for (String role : roles) {
//            authorities.add(new SimpleGrantedAuthority(role));
//        }
//        return authorities;
//    }
//}