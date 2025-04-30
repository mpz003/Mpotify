package Mpz003.Mpotify.service;

import Mpz003.Mpotify.dao.UserRepository;
import Mpz003.Mpotify.entity.Song;
import Mpz003.Mpotify.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserByUsername(int id) {
        Optional<User> result = userRepository.findById(id);

        User TheUser = null;

        if (result.isPresent()) {
            TheUser = result.get();
        }
        else {
            // we didn't find the employee
            throw new RuntimeException("Did not find employee id - " + id);
        }

        return TheUser;
    }

    public User addUser(User user) {
        return userRepository.save(user);
    }

    public void deleteUser(Integer id) {
        userRepository.deleteById(id);
    }

}
