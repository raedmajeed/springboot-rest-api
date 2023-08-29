package springREST.com.example.springREST.config;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import springREST.com.example.springREST.service.CustomAdminDetailsService;
import springREST.com.example.springREST.service.CustomUserDetailsService;

@Component
@AllArgsConstructor
public class CustomDaoAuthenticationProvider extends DaoAuthenticationProvider {
    @Autowired
    private CustomAdminDetailsService customAdminDetailsService;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    public UserDetails retrieveUser(UsernamePasswordAuthenticationToken auth, String username) throws UsernameNotFoundException {
        for (GrantedAuthority grantedAuthority : auth.getAuthorities()) {
            if (grantedAuthority.getAuthority().contains("ADMIN")) {
                return customAdminDetailsService.loadUserByUsername(username);
            }
            else {
                return customUserDetailsService.loadUserByUsername(username);
            }
        }
    }


}
