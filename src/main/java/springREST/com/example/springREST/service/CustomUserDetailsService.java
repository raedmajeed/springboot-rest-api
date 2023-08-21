package springREST.com.example.springREST.service;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import springREST.com.example.springREST.dao.UserRepository;

@Service
@Data
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    @Lazy
    private UserDetails userDetails;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        springREST.com.example.springREST.entity.User user = userRepository.getUserByUsername(username);

        UserDetails uDetails = User.withUsername(user.getUsername())
                .password(user.getPassword())
                .roles(user.getRole())
                .build();
        return uDetails;
    }

}
