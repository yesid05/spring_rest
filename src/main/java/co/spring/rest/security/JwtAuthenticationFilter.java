package co.spring.rest.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import co.spring.rest.entity.bo.User;
import co.spring.rest.entity.dto.UserDto;
import co.spring.rest.entity.mapper.UserMapper;
import co.spring.rest.service.JwtServ;
import co.spring.rest.service.UserServ;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter{

    @Autowired
    private JwtServ jwtServ;

    @Autowired
    private UserServ userServ;

    @Autowired
    private UserMapper userMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String jwt = jwtServ.getTokenRequest(request);
    
        if(jwt == null){
            filterChain.doFilter(request, response);
            System.err.println("JwtAuthenticationFilter: Token is null");
            return;
        }

        if(!jwtServ.validateToken(jwt)){
            filterChain.doFilter(request, response);
            System.out.println("JwtAuthenticationFilter: Token no validate");
            return;
        }

        String email = jwtServ.getClaims(jwt).getSubject();

        UserDto userDto = userServ.findByEmail(email);
        User aUser = userMapper.toUser(userDto);

        String aRole = jwtServ.getClaims(jwt).get("role", String.class);

        List<Map<String,String>> permissions = jwtServ.getClaims(jwt).get("permission", ArrayList.class);

        List<GrantedAuthority> listGrantedAuthorities = permissions.stream()
            .map(mapPermission -> {
                String aPermission = mapPermission.get("name");
                return new SimpleGrantedAuthority(aPermission);
            })
            .collect(Collectors.toList());
        
        listGrantedAuthorities.add(new SimpleGrantedAuthority("ROLE_"+aRole));
        
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(aUser, null,listGrantedAuthorities);

        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(request, response);
    }

}
