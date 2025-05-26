package Mpz003.Mpotify.security;



import Mpz003.Mpotify.dao.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@EnableMethodSecurity
@Configuration
public class SecurityConfig {
    private UserRepository userRepository;

    public SecurityConfig(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()                     // ✅ allow Swagger
                        .requestMatchers(HttpMethod.POST, "/api/users/register").permitAll()                 // ✅ allow user registration
                        .requestMatchers(HttpMethod.GET, "/mpz/songs/**").hasAnyRole("USER", "ADMIN")        // ✅ read songs
                        .requestMatchers("/mpz/playlists/**").hasAnyRole("USER", "ADMIN")                    // ✅ playlists
                        .requestMatchers("/mpz/**").hasRole("ADMIN")                                          // ❌ fallback
                        .anyRequest().authenticated()                                                        // 🚫 all else must auth
                )


                .httpBasic(Customizer.withDefaults());
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}

