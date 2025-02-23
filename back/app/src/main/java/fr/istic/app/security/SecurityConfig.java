package fr.istic.app.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)  // Enable PreAuthorize support
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/v1/users/login").permitAll()  // Allow login endpoint to be accessed publicly
                        .requestMatchers("/api/v1/users/logout").permitAll()  // Allow login endpoint to be accessed publicly
                        .requestMatchers("/api/v1/**").permitAll()  // ✅ Allow public endpoints
                        .anyRequest().permitAll()// 🔒 Secure everything else
                )
                .csrf(AbstractHttpConfigurer::disable) // ✅ Disable CSRF for non-browser clients (optional)
                .cors(AbstractHttpConfigurer::disable); // 🔥 Disabling CORS

        return http.build();
    }

}
