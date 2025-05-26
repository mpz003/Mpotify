package Mpz003.Mpotify.security;

import Mpz003.Mpotify.dao.UserRepository;
import Mpz003.Mpotify.entity.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = userRepository.findByUserName(userName)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + userName));

        // ⚠️ Ensure null-safe and normalized role
        String role = Optional.ofNullable(user.getRole())
                .orElse("USER") // fallback default
                .toUpperCase();

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUserName())
                .password(user.getPassword())
                .roles(role) // Spring auto-prefixes "ROLE_"
                .build();
    }
}
