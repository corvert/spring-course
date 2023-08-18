package edu.sda26.springcourse.service;

import edu.sda26.springcourse.exception.UserAlreadyExistingException;
import edu.sda26.springcourse.model.Users;
import edu.sda26.springcourse.repository.UsersRepository;
import org.springframework.stereotype.Service;

@Service
public class UsersService {
    private final UsersRepository usersRepository;

    public UsersService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    public Users save(Users users) throws UserAlreadyExistingException {
        Users users1 = usersRepository.findByUsername(users.getUsername());
        if (users1 != null) {
            throw new UserAlreadyExistingException("User with the same username already exists", 2);
        }
        return usersRepository.save(users);
    }


}

