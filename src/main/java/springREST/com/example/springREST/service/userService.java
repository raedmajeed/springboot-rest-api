package springREST.com.example.springREST.service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import springREST.com.example.springREST.Constants.Roles;
import springREST.com.example.springREST.dao.UserRepository;
import springREST.com.example.springREST.dto.CommonResponse;
import springREST.com.example.springREST.dto.LoggedResponse;
import springREST.com.example.springREST.dto.LoginRequest;
import springREST.com.example.springREST.entity.User;

@Component
@Data
public class userService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private JwtService jwtService;


    public ResponseEntity<CommonResponse> registerUser(@RequestBody User user) {

        CommonResponse response = new CommonResponse();

        User newUser = User.builder()
                .username(user.getUsername())
                .password(passwordEncoder.encode(user.getPassword()))
                .role(Roles.USER.name())
                .email(user.getEmail())
                .dob(user.getDob())
                .build();

        User userCheck = userRepository.findByUsername(user.getUsername());

        if (userCheck != null) {
            System.out.println("USER ALREADY EXISTS");
            response.setSuccess(false);
            response.setResponseMessage("USER EXISTS IN DB");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        userRepository.save(newUser);
        response.setResponseMessage("USER REGISTERED SUCCESSFULLY");
        response.setSuccess(true);
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

    public ResponseEntity<LoggedResponse> loginAuthenticate(@RequestBody LoginRequest loginRequest) {
        String jwtToken = null;
        User user = userRepository.findByUsername(loginRequest.getUsername());
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(loginRequest.getUsername());

        LoggedResponse response = new LoggedResponse();

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                loginRequest.getUsername(),
                loginRequest.getPassword(),
                userDetails.getAuthorities()
        );

        authenticationManager.authenticate(authenticationToken);

        if (!user.isEnabled()) {
            response.setResponseMessage("Failed to login");
            response.setSuccess(false);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        for (GrantedAuthority grantedAuthority : userDetails.getAuthorities()) {
            if (grantedAuthority.getAuthority().contains("USER")) {
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
            response.setResponseMessage("Failed to login, Admin Role");
            response.setSuccess(false);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
}
