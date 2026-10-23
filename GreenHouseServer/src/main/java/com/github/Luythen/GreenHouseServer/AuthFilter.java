package com.github.Luythen.GreenHouseServer;

import java.io.IOException;
import java.util.ArrayList;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import com.github.Luythen.GreenHouseServer.Model.UserModel;
import com.github.Luythen.GreenHouseServer.Service.JWTService;
import com.github.Luythen.GreenHouseServer.Service.UserDetailServiceImpl;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Service 
public class AuthFilter extends OncePerRequestFilter {

    private final UserDetailServiceImpl userDetailServiceImpl;
    private final JWTService jwtService;

    public AuthFilter(UserDetailServiceImpl userDetailServiceImpl, JWTService jwtService) {
        this.userDetailServiceImpl = userDetailServiceImpl;
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        if (request.getHeader("Authorization") != null && request.getHeader("Authorization").startsWith("Bearer")) {
            String accessToken = request.getHeader("Authorization").split("Bearer ")[1];

            if (accessToken != null) {
                try {
                    UserModel userModel = (UserModel) userDetailServiceImpl.loadUserByUsername(jwtService.getClaims(accessToken, "accessToken").getSubject());
                    if (!jwtService.isTokenExpired(accessToken, "accessToken")) {
                        Authentication authentication = new UsernamePasswordAuthenticationToken(userModel, null, userModel.getAuthorities());
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }
                } catch (Exception e) {}
            }
        }

        if (request.getHeader("X-API-KEY") != null) {
            String apiKey = request.getHeader("X-API-KEY");
            if (!jwtService.isTokenExpired(apiKey, "accessToken")) {
                ArrayList<GrantedAuthority> authorities = new ArrayList<>();
                authorities.add(new SimpleGrantedAuthority("GH_Api"));

                Authentication authentication = new ApiKey(apiKey, authorities);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        filterChain.doFilter(request, response);
    }

}
