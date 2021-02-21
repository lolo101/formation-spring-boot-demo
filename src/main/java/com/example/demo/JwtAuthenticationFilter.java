package com.example.demo;

import io.jsonwebtoken.ClaimJwtException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static java.util.Collections.emptyList;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final String AUTH_SCHEME = "Bearer ";
    private final SecretKey key = new SecretKeySpec("0123456789ABCDEF0123456789ABCDEF".getBytes(StandardCharsets.UTF_8), "HmacSHA256");

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {
        try {
            String token = jwtToken(httpServletRequest);
            Authentication authentication = authentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            filterChain.doFilter(httpServletRequest, httpServletResponse);
        } catch (ClaimJwtException | IllegalArgumentException ex) {
            httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
            httpServletResponse.addHeader("WWW-Authenticate", "Bearer realm=\"" + httpServletRequest.getServerName() + "\"");
        }
    }

    private String jwtToken(HttpServletRequest httpServletRequest) {
        String authorization = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION);
        if (authorization != null && authorization.startsWith(AUTH_SCHEME)) {
            return authorization.substring(AUTH_SCHEME.length());
        }
        return null;
    }

    private Authentication authentication(String token) throws ClaimJwtException {
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(key).parseClaimsJws(token);
        Claims body = claimsJws.getBody();
        Object principal = body.getSubject();
        return new UsernamePasswordAuthenticationToken(principal, null, emptyList());
    }
}
