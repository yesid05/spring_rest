package co.spring.rest.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import co.spring.rest.entity.bo.User;
import co.spring.rest.entity.dto.UserDto;
import co.spring.rest.entity.mapper.UserMapper;
import co.spring.rest.service.JsonWebTokenAccessServ;
import co.spring.rest.service.UserServ;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter{

    @Autowired
    private JsonWebTokenAccessServ jsonWebTokenAccessServ;

    @Autowired
    private UserServ userServ;

    @Autowired
    private UserMapper userMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String jwt = jsonWebTokenAccessServ.getTokenRequest(request);
    
        if(jwt == null){
            filterChain.doFilter(request, response);
            System.err.println("JwtAuthenticationFilter: Token is null");
            return;
        }

        if(!jsonWebTokenAccessServ.validateToken(jwt)){
            filterChain.doFilter(request, response);
            System.out.println("JwtAuthenticationFilter: Token no validate");
            return;
        }

        String email = jsonWebTokenAccessServ.getClaims(jwt).getSubject();

        UserDto userDto = userServ.findByEmail(email);
        User aUser = userMapper.toUser(userDto);
        
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(aUser, null,aUser.getAuthorities());

        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(request, response);
    }

}
