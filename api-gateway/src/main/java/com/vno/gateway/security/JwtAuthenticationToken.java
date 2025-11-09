package com.vno.gateway.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class JwtAuthenticationToken extends AbstractAuthenticationToken {
    private final Jwt jwt;
    private final String username;

    public JwtAuthenticationToken(Jwt jwt, List<String> roles) {
        super(roles != null ? roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                .collect(Collectors.toList()) : Collections.emptyList());
        this.jwt = jwt;
        this.username = jwt.getSubject();
        setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return "";
    }

    @Override
    public Object getPrincipal() {
        return this.username;
    }

    public Jwt getJwt() {
        return jwt;
    }
}
