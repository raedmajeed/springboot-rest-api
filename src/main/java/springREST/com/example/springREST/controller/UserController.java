package springREST.com.example.springREST.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springREST.com.example.springREST.dto.CommonResponse;
import springREST.com.example.springREST.dto.LoggedResponse;
import springREST.com.example.springREST.dto.LoginRequest;
import springREST.com.example.springREST.entity.User;
import springREST.com.example.springREST.service.JwtService;
import springREST.com.example.springREST.service.userService;

@RestController
public class UserController {
    @Autowired
    JwtService jwtService;
    @Autowired
    private userService userService;

    @PostMapping("/user/register")
    private ResponseEntity<CommonResponse> userRegister(@RequestBody User user) {
        return userService.registerUser(user);
    }


    @PostMapping("/user/login")
    public ResponseEntity<LoggedResponse> loggedInPage(@RequestBody LoginRequest loginRequest) {
        return userService.loginAuthenticate(loginRequest);
    }

    @GetMapping("/welcome")
    public ResponseEntity<CommonResponse> welcome(@RequestHeader("Authorization") String header) {
        CommonResponse response = new CommonResponse();
        String username = jwtService.extractUsername(header.substring(7));
        response.setResponseMessage("Username: " + username);
        response.setSuccess(true);
        return ResponseEntity.ok(response);
    }
}
