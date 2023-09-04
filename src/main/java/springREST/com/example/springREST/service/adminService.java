package springREST.com.example.springREST.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import springREST.com.example.springREST.dao.UserRepository;
import springREST.com.example.springREST.dto.CommonResponse;
import springREST.com.example.springREST.dto.JsonResponse;
import springREST.com.example.springREST.dto.LoggedResponse;
import springREST.com.example.springREST.dto.LoginRequest;
//import springREST.com.example.springREST.service.CustomUserDetailsService;
import springREST.com.example.springREST.entity.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class adminService {
    @Autowired
    private CustomUserDetailsService customUserDetailsService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserRepository userRepository;

    public ResponseEntity<LoggedResponse> adminLoginAuthenticate(@RequestBody LoginRequest loginRequest) throws UsernameNotFoundException {
        String jwtToken = null;

        UserDetails userDetails = customUserDetailsService.loadUserByUsername(loginRequest.getUsername());
        if (userDetails == null) {
            throw new UsernameNotFoundException("No ADMIN found in DB");
        }

        LoggedResponse response = new LoggedResponse();

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                loginRequest.getUsername(),
                loginRequest.getPassword(),
                userDetails.getAuthorities()
        );

        authenticationManager.authenticate(authenticationToken);

        if (!userDetails.isEnabled()) {
            response.setResponseMessage("Failed to login");
            response.setSuccess(false);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        for (GrantedAuthority grantedAuthority : userDetails.getAuthorities()) {
            if (grantedAuthority.getAuthority().contains("ADMIN")){
                jwtToken = jwtService.generateToken(userDetails.getUsername());
            }
        }

        if (jwtToken != null) {
            response.setResponseMessage("Log in successful");
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

    public ResponseEntity<CommonResponse> deleteUser(int id) {
        CommonResponse response = new CommonResponse();
        Optional<User> userCheck = userRepository.findById(id);

        if (userCheck.isEmpty()) {
            response.setSuccess(false);
            response.setResponseMessage("USER DOES NOT EXIST IN DB");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        userRepository.deleteById(id);
        response.setSuccess(true);
        response.setResponseMessage("Deletion Successful");
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

    public ResponseEntity<CommonResponse> updateUser(int id, User user) {
        CommonResponse response = new CommonResponse();
        Optional<User> userCheck = userRepository.findById(id);
        if (userCheck.isEmpty()) {
            response.setSuccess(false);
            response.setResponseMessage("User not found");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

//        BeanUtils.copyProperties(user, userCheck, "id");
        User userFromDB = userCheck.get();
        userFromDB.setUsername("rr");
        userFromDB.setDob("123");
//        userFromDB.setPassword(passwordEncoder.encode(user.getPassword()));

        userRepository.save(userFromDB);
        System.out.println(userCheck.get());
        response.setSuccess(true);
        response.setResponseMessage("UPDATED SUCCESSFULLY");
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

    public ResponseEntity<JsonResponse> searchUser(String searchName) {
        JsonResponse jsonResponse = new JsonResponse();
        List<User> usersList = userRepository.findByUsernameContaining(searchName);

        Map<Integer, Map<String, String>> userMap = new HashMap<>();

        for (User user: usersList) {
            if (user.getRole() != null && !user.getRole().contains("ADMIN")) {
                Map<String, String> userDetailsMap = new HashMap<>();
                userDetailsMap.put("username", user.getUsername());
                userDetailsMap.put("dob", user.getDob());
                userDetailsMap.put("email", user.getEmail());
                userMap.put(
                        user.getId(), userDetailsMap
                );
            }
        }
        jsonResponse.setUserList(userMap);
        jsonResponse.setResponseMessage("SEARCHED DATA");
        jsonResponse.setSuccess(true);
        jsonResponse.setStatus(HttpStatus.ACCEPTED);
        return new ResponseEntity<>(jsonResponse, HttpStatus.ACCEPTED);
    }

    public ResponseEntity<CommonResponse> displayUser(int id) {
        JsonResponse jsonResponse = new JsonResponse();
        Optional<User> userOptional = userRepository.findById(id);

        if (userOptional.isEmpty()) {
            jsonResponse.setResponseMessage("SNOT FOUND");
            jsonResponse.setSuccess(false);
            jsonResponse.setStatus(HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(jsonResponse, HttpStatus.BAD_REQUEST);
        }
        User user = userOptional.get();

        Map<Integer, Map<String, String>> userMap = new HashMap<>();
        Map<String, String> userDetailsMap = new HashMap<>();
        userDetailsMap.put("username", user.getUsername());
        userDetailsMap.put("dob", user.getDob());
        userDetailsMap.put("email", user.getEmail());
        userMap.put(
                user.getId(), userDetailsMap
        );
        jsonResponse.setUserList(userMap);
        jsonResponse.setResponseMessage("SEARCHED DATA");
        jsonResponse.setSuccess(true);
        jsonResponse.setStatus(HttpStatus.ACCEPTED);
        return new ResponseEntity<>(jsonResponse, HttpStatus.ACCEPTED);
    }

    public ResponseEntity<CommonResponse> displayAllUsers() {
        JsonResponse jsonResponse = new JsonResponse();
        List<User> usersList = userRepository.findAll();
        Map<Integer, Map<String, String>> userMap = new HashMap<>();

        for (User user: usersList) {
            if (user.getRole() != null && !user.getRole().contains("ADMIN")) {
                Map<String, String> userDetailsMap = new HashMap<>();
                userDetailsMap.put("username", user.getUsername());
                userDetailsMap.put("dob", user.getDob());
                userDetailsMap.put("email", user.getEmail());
                userMap.put(
                        user.getId(), userDetailsMap
                );
            }
        }
        jsonResponse.setUserList(userMap);
        jsonResponse.setResponseMessage("DISPLAYING ALL USERS");
        jsonResponse.setSuccess(true);
        jsonResponse.setStatus(HttpStatus.ACCEPTED);
        return new ResponseEntity<>(jsonResponse, HttpStatus.ACCEPTED);
    }

//    public ResponseEntity<CommonResponse> logout() {
//        CommonResponse response = new CommonResponse();
//
//        jwtService.
//    }
}
