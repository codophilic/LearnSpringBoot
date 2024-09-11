package com.springboot.security.config;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.server.resource.introspection.OpaqueTokenAuthenticationConverter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SpringAuthOpaqueRoleConverter implements OpaqueTokenAuthenticationConverter {
    /**
     * @param introspectedToken      the bearer token used to perform token introspection
     * @param authenticatedPrincipal the result of token introspection
     * @return
     */
    @Override
    public Authentication convert(String introspectedToken, OAuth2AuthenticatedPrincipal authenticatedPrincipal) {
        ArrayList<String> roles  = authenticatedPrincipal.getAttribute("scope");
        Collection<GrantedAuthority> grantedAuthorities = roles
                .stream().map(roleName -> "ROLE_" + roleName)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
        return new UsernamePasswordAuthenticationToken(authenticatedPrincipal.getName(), null,
                grantedAuthorities);

    }
}