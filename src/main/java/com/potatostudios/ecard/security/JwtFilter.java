package com.potatostudios.ecard.security;

import com.potatostudios.ecard.util.JwtUtil;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.Collections;

@Component
public class JwtFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;

    public JwtFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        logger.info("🔍 JwtFilter executed for request: " + request.getRequestURI()); // ✅ Log request

        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            try {
                if (jwtUtil.validateToken(token)) {
                    logger.info("✅ JWT found in request!");  // ✅ Log when JWT is found

                    String email = jwtUtil.getEmailFromToken(token);
                    UserDetails userDetails = User.withUsername(email).password("").authorities(Collections.emptyList()).build();
                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    logger.info("token : " + token);  // ✅ Log when JWT is found
                    logger.info("email : " + email);  // ✅ Log when JWT is found
                    logger.info("userDetail : " + userDetails.toString());  // ✅ Log when JWT is found
                }
            } catch (JwtException e) {
                logger.info("✅ Invalid JWT TOKEN!");  // ✅ Log when JWT is found
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid JWT Token");
                return;
            }
        }

        chain.doFilter(request, response);
    }
}