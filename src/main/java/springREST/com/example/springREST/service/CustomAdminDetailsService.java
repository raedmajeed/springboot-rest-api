package springREST.com.example.springREST.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import springREST.com.example.springREST.entity.admin;
import springREST.com.example.springREST.dao.adminRepository;

@Component
public class CustomAdminDetailsService implements UserDetailsService {
    @Autowired
    private adminRepository adminRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        admin ad = adminRepository.findByUsername(username);

        if (ad == null) {
            return null;
        }

        return User.withUsername(ad.getUsername())
                .password(ad.getPassword())
                .roles(ad.getRole())
                .build();
    }
}
