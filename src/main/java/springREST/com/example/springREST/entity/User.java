package springREST.com.example.springREST.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import springREST.com.example.springREST.Constants.Roles;

import java.util.*;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(
        name = "user_table",
        uniqueConstraints = @UniqueConstraint(
                name = "username",
                columnNames = "username"
        )
)
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(
            name = "username",
            nullable = false
    )
    private String username;
    private String password;
    private String email;
    private String dob;
    private String role;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(Roles.ADMIN.name()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return this.username;
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
}

