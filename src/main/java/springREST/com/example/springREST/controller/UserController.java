package springREST.com.example.springREST.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springREST.com.example.springREST.dto.CommonResponse;
import springREST.com.example.springREST.dto.LoggedResponse;
import springREST.com.example.springREST.dto.LoginRequest;
import springREST.com.example.springREST.entity.User;
import springREST.com.example.springREST.service.userService;

@RestController
public class UserController {

    @Autowired
    private userService userService;

    @PostMapping("/user/register")
    private ResponseEntity<CommonResponse> userRegister(@RequestBody User user) {
        return userService.registerUser(user);
    }

    @GetMapping("/user/register")
    public String login() {
        return "here";
    }

    @GetMapping("/user/login")
    public String loginPage() {
        return "null";
    }

    @PostMapping("/user/login")
    public ResponseEntity<LoggedResponse> loggedInPage(@RequestBody LoginRequest loginRequest) {
        System.out.println(loginRequest);
        return userService.loginAuthenticate(loginRequest);
    }

    @GetMapping("/welcome")
    public String welcome() {
        return "welcome";
    }
}
