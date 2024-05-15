package com.projetsiback.projetsiback.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document("users")
public class User implements UserDetails {
    @Id
    private int id;
    private String mail;
    private String password;
    private String avatar;
    private String lastName;
    private String firstName;
    private String address;
    private int companyNote;
    private Role role;
    private AccountStatus accountStatus;

    // Ajoutez le constructeur pour une nouvelle instance de User
    public User(String mail, String password, String avatar, String lastName, String firstName, String address, int companyNote, Role role, AccountStatus accountStatus) {
        this.mail = mail;
        this.password = password;
        this.avatar = avatar;
        this.lastName = lastName;
        this.firstName = firstName;
        this.address = address;
        this.companyNote = companyNote;
        this.role = role;
        this.accountStatus = accountStatus;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (role != null) {
            SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role.name());
            return Collections.singletonList(authority);
        } else {
            return Collections.emptyList();
        }
    }

    @Override
    public String getUsername() {
        return mail;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountStatus != AccountStatus.RADIE;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
