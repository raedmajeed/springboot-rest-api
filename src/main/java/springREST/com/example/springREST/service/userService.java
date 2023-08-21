package springREST.com.example.springREST.service;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
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

        User userDetail = User.builder()
                .username(user.getUsername())
                .password(passwordEncoder.encode(user.getPassword()))
                .role(user.getRole())
                .build();

        userRepository.save(userDetail);
        response.setSuccess(true);
        response.setResponseMessage("USER REGISTERED SUCCESSFULLY");

        return new ResponseEntity<CommonResponse>(
                response,
                HttpStatus.OK
        );
    }

    public ResponseEntity<LoggedResponse> loginAuthenticate(@RequestBody LoginRequest loginRequest) {
        userRepository.findByUsername(loginRequest.getUsername());

        LoggedResponse lg = new LoggedResponse();

        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword());

        authenticationManager.authenticate(auth);

        UserDetails userDetails = customUserDetailsService.loadUserByUsername(loginRequest.getUsername());

        User user = userRepository.getUserByUsername(loginRequest.getUsername());

//        if (user.getStatus() != UserStatus.ACTIVE.value()) {
//            response.setResponseMessage("Failed to login");
//            response.setSuccess(true);
//            return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
//        }

//        for (GrantedAuthority grantedAuthory : userDetails.getAuthorities()) {
//            if (grantedAuthory.getAuthority().equals(loginRequest.getRole())) {
        final String jwtToken = jwtService.generateToken(userDetails.getUsername());
//            }
//        }
//
        // user is authenticated
        if (jwtToken != null) {

            lg.setResponseMessage("Logged in sucessful");
            lg.setSuccess(true);
            lg.setToken(jwtToken);
            return new ResponseEntity<>(lg, HttpStatus.OK);

        }

        else {

            lg.setResponseMessage("Failed to login");
            lg.setSuccess(true);
            return new ResponseEntity<>(lg, HttpStatus.BAD_REQUEST);
        }

    }
}
