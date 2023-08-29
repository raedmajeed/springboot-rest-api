package springREST.com.example.springREST.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import springREST.com.example.springREST.dto.LoggedResponse;
import springREST.com.example.springREST.dto.LoginRequest;
import springREST.com.example.springREST.service.adminService;

@RestController
public class adminController {
    @Autowired
    private adminService adminService;
    @PostMapping("/admin/login")
    public ResponseEntity<LoggedResponse> adminLoginAuthenticate(@RequestBody LoginRequest loginRequest) {
        return adminService.adminLoginAuthenticate(loginRequest);
    }

    @GetMapping("/admin/fetch")
    public ResponseEntity<String> welcomeScreen() {
        return ResponseEntity.ok("yes done");
    }
}

