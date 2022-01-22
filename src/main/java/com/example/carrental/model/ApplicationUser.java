package com.example.carrental.model;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;


@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationUser implements UserDetails {

    @Id
    @GeneratedValue
    private Long id;
    @Column(unique = true)
    private String username;
    private String password;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<ApplicationUserRole> roles;
    @OneToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "client_id")
    private Client client;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream().map(role ->
                new SimpleGrantedAuthority(role.getName())).collect(Collectors.toSet());
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void setClient(Client client) {
        if (client == null) {
            if (this.client != null) {
                this.client.setUser(null);
            }
        }
        else {
            client.setUser(this);
        }
        this.client = client;
    }
}
