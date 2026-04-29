package co.spring.rest.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;

import co.spring.rest.service.JsonWebTokenAccessServ;
import co.spring.rest.service.JsonWebTokenRefreshServ;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomLogout implements LogoutHandler{

    @Autowired
    private JsonWebTokenAccessServ jsonWebTokenAccessServ;

    @Autowired
    private JsonWebTokenRefreshServ jsonWebTokenRefreshServ;


    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication){
        
        String tokenAccess = "";
        String tokenRefresh = "";

        try {
            
            tokenAccess = jsonWebTokenAccessServ.getTokenRequest(request);
            tokenRefresh = WebUtils.getCookie(request, "refreshToken").getValue();

        } catch (NullPointerException e) {
            throw new BadCredentialsException(e.getMessage());
        }

        boolean isDisableJsonWebTokenAccess = disableJsonWebTokenAccess(tokenAccess);
        boolean isDisableJsonWebTokenRefresh = disableJsonWebTokenRefresh(tokenRefresh);

        if(!isDisableJsonWebTokenAccess)
            throw new BadCredentialsException("Credentials token access not found");

        if(!isDisableJsonWebTokenRefresh)
            throw new BadCredentialsException("Credentials token refresh not found");

    }

        
    private boolean disableJsonWebTokenAccess(String tokenAccess){

        boolean disableToken = false;

        if(jsonWebTokenAccessServ.isActiveToken(tokenAccess))
            disableToken = jsonWebTokenAccessServ.disableToken(tokenAccess);    
    
        return disableToken;

    }

    private boolean disableJsonWebTokenRefresh(String tokenRefresh){

        boolean disableToken = false;

        if(jsonWebTokenRefreshServ.isActiveToken(tokenRefresh))
            disableToken = jsonWebTokenRefreshServ.disableToken(tokenRefresh);

        return disableToken;

    }

}
