package Mpz003.Mpotify.security;



import Mpz003.Mpotify.dao.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
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
    private UserDetailsServiceImpl userDetailsService;

    public SecurityConfig(UserRepository userRepository, UserDetailsServiceImpl userDetailsService) {
        this.userRepository = userRepository;
        this.userDetailsService = userDetailsService;
    }

    @Bean
    @Order(1)
    public SecurityFilterChain swaggerSecurityFilterChain(HttpSecurity http) throws Exception {
        http
                .securityMatcher("/swagger-ui/**", "/v3/api-docs/**", "/swagger-ui.html", "/swagger-resources/**", "/webjars/**")
                .authorizeHttpRequests(auth -> auth.anyRequest().permitAll())
                .csrf(csrf -> csrf.disable())
                .httpBasic(Customizer.withDefaults());
        return http.build();
    }

    @Bean
    @Order(2)
    public SecurityFilterChain appSecurityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.POST, "/mpz/users/register").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/mpz/users/**").hasAnyRole("USER", "ADMIN")
                        .requestMatchers("/mpz/users/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/mpz/songs/**").hasAnyRole("USER", "ADMIN")
                        .requestMatchers("/mpz/songs/**").hasRole("ADMIN")
                        .requestMatchers("/mpz/playlists/**").hasAnyRole("USER", "ADMIN")
                        .requestMatchers("/mpz/**").hasRole("ADMIN")
                        .anyRequest().denyAll()
                )
                .userDetailsService(userDetailsService)
                .httpBasic(Customizer.withDefaults());
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}

