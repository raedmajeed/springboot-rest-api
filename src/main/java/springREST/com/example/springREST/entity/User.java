package springREST.com.example.springREST.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(
        name = "user_table",
        uniqueConstraints = @UniqueConstraint(
                name = "user_name",
                columnNames = "username"
        )
)
public class User implements UserDetails {
    @Id
    @SequenceGenerator(
            name = "seq_gen",
            sequenceName = "seq_gen",
            allocationSize = 10
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "seq_gen"
    )
    private long id;

    @Column(
            name = "username",
            nullable = false
    )
    private String username;
    private String password;

    private String role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(role));
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

//    @Enumerated(EnumType.STRING)
//    @OneToMany(
//            cascade = CascadeType.ALL
//    )
//    private List<UserRoles> roles;

}
