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
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import co.spring.rest.entity.bo.Permission;
import co.spring.rest.entity.bo.Role;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    private AuthenticationProvider authenticationProvider;

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    private AuthenticationEntryPoint authenticationEntryPoint;

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
                
                c.requestMatchers(HttpMethod.GET,"/api/product/**").hasAuthority(Permission.READ);
                c.requestMatchers(HttpMethod.POST,"/api/product/**").hasAuthority(Permission.CREATE);
                c.requestMatchers(HttpMethod.PUT,"/api/product/**").hasAuthority(Permission.UPDATE);
                c.requestMatchers(HttpMethod.DELETE,"/api/product/**").hasAuthority(Permission.DELETE);

                c.requestMatchers(HttpMethod.GET,"/api/category/**").hasAuthority(Permission.READ);
                c.requestMatchers(HttpMethod.POST,"/api/category/**").hasAuthority(Permission.CREATE);
                c.requestMatchers(HttpMethod.PUT,"/api/category/**").hasAuthority(Permission.UPDATE);
                c.requestMatchers(HttpMethod.DELETE,"/api/category/**").hasAuthority(Permission.DELETE);

                c.requestMatchers("/api/auth/**").permitAll();
            })
            .exceptionHandling(exception -> {
                exception.authenticationEntryPoint(authenticationEntryPoint);
            });

        return httpSecurity.build();
    }

}
