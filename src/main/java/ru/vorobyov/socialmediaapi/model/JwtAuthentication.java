package ru.vorobyov.socialmediaapi.model;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import java.util.Collection;

public class JwtAuthentication implements Authentication {

    private boolean authenticated;
    private String email;
    private String firstName;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() { return null; }

    @Override
    public Object getCredentials() { return null; }

    @Override
    public Object getDetails() { return null; }

    @Override
    public Object getPrincipal() { return email; }

    @Override
    public boolean isAuthenticated() { return authenticated; }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        this.authenticated = isAuthenticated;
    }

    @Override
    public String getName() { return firstName; }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
}
