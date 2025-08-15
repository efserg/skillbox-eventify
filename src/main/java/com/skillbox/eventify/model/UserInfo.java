package com.skillbox.eventify.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import lombok.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import com.skillbox.eventify.schema.User;

@Value
public class UserInfo implements UserDetails, Serializable {
    Long id;
    String email;
    String password;
    Collection<? extends GrantedAuthority> authorities;

    public UserInfo(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.authorities = List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()));
    }

    @Override
    public String getUsername() {
        return email;
    }
}
