package Mpz003.Mpotify.rest;

import Mpz003.Mpotify.dao.UserRepository;
import Mpz003.Mpotify.entity.Playlist;
import Mpz003.Mpotify.entity.Song;
import Mpz003.Mpotify.entity.User;
import Mpz003.Mpotify.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/mpz")
public class UserRestController {
    private UserService userService;
    private UserRepository userRepository;
    private PasswordEncoder encoder;

    @Autowired
    public UserRestController(UserService userService,UserRepository userRepository, PasswordEncoder encoder) {
        this.userService = userService;
        this.encoder=encoder;
        this.userRepository=userRepository;
    }

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/users/{id}")
    public User getUser(@PathVariable int id) {
        User user = userService.getUserByUsername(id);

        if (user == null) {
            throw new RuntimeException("Employee id not found - " + id);
        }
        return user;
    }

    @PostMapping("/users")
    public User addUser(@RequestBody User user) {
        return userService.addUser(user);
    }

    @PutMapping("/users/{id}")
    public User updatingUser(@PathVariable Integer id, @RequestBody User updatedUser){
        return userService.updateUser(id,updatedUser);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Integer id) {
        try {
            userService.deleteUser(id);
            return ResponseEntity.ok("Deleted the user with id: " + id);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("User not found with id: " + id);
        }
    }
    @GetMapping("/users/search")
    public List<User> searchUsers(@RequestParam(required = false) String userName,
                                  @RequestParam(required = false) String email) {
        return userService.searchUsers(userName, email);
    }

    @PostMapping("/users/register")
    public ResponseEntity<String> register(@RequestBody User user) {
        if (userRepository.findByUserName(user.getUserName()).isPresent()) {
            return ResponseEntity.badRequest().body("Username already exists");
        }

        user.setPassword(encoder.encode(user.getPassword()));
        userRepository.save(user);
        return ResponseEntity.ok("User registered successfully");
    }


}
