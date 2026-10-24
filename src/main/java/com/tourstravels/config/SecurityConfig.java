package com.tourstravels.config;

import com.tourstravels.service.CustomUserDetailsService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {
    @Bean PasswordEncoder passwordEncoder() { return new BCryptPasswordEncoder(); }
    @Bean DaoAuthenticationProvider authenticationProvider(CustomUserDetailsService users, PasswordEncoder encoder) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(users); provider.setPasswordEncoder(encoder); return provider;
    }
    @Bean SecurityFilterChain filterChain(HttpSecurity http, DaoAuthenticationProvider provider) throws Exception {
        http.authenticationProvider(provider)
          .authorizeHttpRequests(auth -> auth
            .requestMatchers("/", "/destinations", "/packages", "/hotels", "/activities", "/contact", "/register", "/login", "/css/**", "/js/**", "/images/**", "/error").permitAll()
            .requestMatchers("/admin/**").hasRole("ADMIN")
            .requestMatchers("/bookings/**", "/account/**", "/wishlist/**").authenticated()
            .anyRequest().permitAll())
          .formLogin(form -> form.loginPage("/login").successHandler((HttpServletRequest request, HttpServletResponse response, Authentication authentication) -> {
              boolean admin = authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
              response.sendRedirect(admin ? "/admin" : "/account");
          }).permitAll())
          .logout(logout -> logout.logoutSuccessUrl("/?logout").permitAll())
          .exceptionHandling(ex -> ex.accessDeniedPage("/access-denied"));
        return http.build();
    }
}
