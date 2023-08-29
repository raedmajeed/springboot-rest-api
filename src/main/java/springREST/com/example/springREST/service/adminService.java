package springREST.com.example.springREST.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import springREST.com.example.springREST.dto.LoggedResponse;
import springREST.com.example.springREST.dto.LoginRequest;
import springREST.com.example.springREST.entity.User;
//import springREST.com.example.springREST.service.CustomUserDetailsService;
import springREST.com.example.springREST.dao.adminRepository;
import springREST.com.example.springREST.entity.admin;

@Service
public class adminService {

    @Autowired
    private  CustomAdminDetailsService adminDetailsService;

    @Autowired
    private adminRepository adminRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    public ResponseEntity<LoggedResponse> adminLoginAuthenticate(@RequestBody LoginRequest loginRequest) {
        String jwtToken = null;

        UserDetails userDetails = adminDetailsService.loadUserByUsername(loginRequest.getUsername());
        admin admin = adminRepository.findByUsername(loginRequest.getUsername());

        System.out.println(userDetails);

        LoggedResponse response = new LoggedResponse();

        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                loginRequest.getUsername(),
                loginRequest.getPassword(),
                userDetails.getAuthorities()
        );

        authenticationManager.authenticate(auth);

        if (!userDetails.isEnabled()) {
            response.setResponseMessage("Failed to login");
            response.setSuccess(true);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        System.out.println(userDetails.getAuthorities());

        for (GrantedAuthority grantedAuthority : userDetails.getAuthorities()) {
            System.out.println(grantedAuthority.getAuthority());
            if (grantedAuthority.getAuthority().contains("ADMIN")){
                jwtToken = jwtService.generateToken(userDetails.getUsername());
            }
        }

        if (jwtToken != null) {
            response.setResponseMessage("Logged in successful");
            response.setSuccess(true);
            response.setToken(jwtToken);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        else {
            response.setResponseMessage("Failed to login");
            response.setSuccess(false);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
}
