package co.spring.rest.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import co.spring.rest.entity.bo.Role;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    private AuthenticationProvider authenticationProvider;

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{

        //Stateless sessions (token-based authentication)
        //Disable CSRF
        //Authorize

        httpSecurity
            .sessionManagement(c -> c.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .csrf(c -> c.disable())
            .authenticationProvider(authenticationProvider)
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
            .authorizeHttpRequests( (c) -> {
                c.requestMatchers("/api/user/**").hasRole(Role.ROLE_ADMINISTRATOR);
                
                c.requestMatchers(HttpMethod.GET,"/api/product/**").hasAnyRole(new String[]{Role.ROLE_ADMINISTRATOR,Role.ROLE_OPERATOR,Role.ROLE_CUSTOMER});
                c.requestMatchers(HttpMethod.POST,"/api/product/**").hasAnyRole(new String[]{Role.ROLE_ADMINISTRATOR});
                c.requestMatchers(HttpMethod.PUT,"/api/product/**").hasAnyRole(new String[]{Role.ROLE_ADMINISTRATOR,Role.ROLE_OPERATOR});
                c.requestMatchers(HttpMethod.DELETE,"/api/product/**").hasAnyRole(new String[]{Role.ROLE_ADMINISTRATOR});

                c.requestMatchers(HttpMethod.GET,"/api/category/**").hasAnyRole(new String[]{Role.ROLE_ADMINISTRATOR,Role.ROLE_OPERATOR,Role.ROLE_CUSTOMER});
                c.requestMatchers(HttpMethod.POST,"/api/category/**").hasAnyRole(new String[]{Role.ROLE_ADMINISTRATOR});
                c.requestMatchers(HttpMethod.PUT,"/api/category/**").hasAnyRole(new String[]{Role.ROLE_ADMINISTRATOR,Role.ROLE_OPERATOR});
                c.requestMatchers(HttpMethod.DELETE,"/api/category/**").hasAnyRole(new String[]{Role.ROLE_ADMINISTRATOR});

                c.requestMatchers("/api/auth/**").permitAll();
            });

        return httpSecurity.build();
    }

}
