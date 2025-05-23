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
                        .requestMatchers(HttpMethod.GET, "/mpz/songs/**").hasAnyRole("USER", "ADMIN") // ✅ explicitly allow
                        .requestMatchers("/mpz/playlists/**").hasAnyRole("USER", "ADMIN")
                        .requestMatchers("/mpz/**").hasRole("ADMIN") // ❌ blocks everything else for USER
                        .anyRequest().authenticated()
                )

                .httpBasic(Customizer.withDefaults());
        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder encoder) {
        UserDetails admin = User.withUsername("admin")
                .password(encoder.encode("adminpass"))
                .roles("ADMIN")
                .build();

        UserDetails user1 = User.withUsername("ali")
                .password(encoder.encode("ali123"))
                .roles("USER")
                .build();

        UserDetails user2 = User.withUsername("fatemeh")
                .password(encoder.encode("fatemeh123"))
                .roles("USER")
                .build();

        UserDetails user3 = User.withUsername("omid")
                .password(encoder.encode("omid123"))
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(admin, user1, user2,user3);
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}

