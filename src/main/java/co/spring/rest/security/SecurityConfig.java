package co.spring.rest.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{

        //Stateless sessions (token-based authentication)
        //Disable CSRF
        //Authorize

        httpSecurity
            .sessionManagement(c -> c.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .csrf(c -> c.disable())
            .authorizeHttpRequests((c) -> {
                c.requestMatchers(HttpMethod.POST,"/api/user").permitAll();
                c.requestMatchers(HttpMethod.POST,"/api/auth/**").permitAll();
                c.anyRequest().authenticated();
            }
        );

        return httpSecurity.build();
    }

}
