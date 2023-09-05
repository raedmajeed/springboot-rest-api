package springREST.com.example.springREST.controller;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import springREST.com.example.springREST.dto.CommonResponse;
import springREST.com.example.springREST.dto.JsonResponse;
import springREST.com.example.springREST.dto.LoggedResponse;
import springREST.com.example.springREST.dto.LoginRequest;
import springREST.com.example.springREST.entity.User;
import springREST.com.example.springREST.service.AdminService;

@RestController
public class adminController {
    @Autowired
    private AdminService adminService;
    @PostMapping("/admin/login")
    public ResponseEntity<LoggedResponse> adminLoginAuthenticate(@RequestBody LoginRequest loginRequest) {
        return adminService.adminLoginAuthenticate(loginRequest);
    }

    @GetMapping("/admin/fetch")
    public ResponseEntity<CommonResponse> welcomeScreen() {
        return adminService.displayAllUsers();
    }

    @DeleteMapping("/admin/delete/{id}")
    @Transactional
    public ResponseEntity<CommonResponse> deleteUser(@PathVariable int id) {
        return adminService.deleteUser(id);
    }

    @PutMapping("/admin/update/{id}")
    public ResponseEntity<CommonResponse> updateUser(@PathVariable int id, @RequestBody User user) {
        return adminService.updateUser(id, user);
    }

    @GetMapping("/admin/search")
    public ResponseEntity<JsonResponse> searchUser(@RequestParam ("name") String searchChar) {
        System.out.println(searchChar);
        return adminService.searchUser(searchChar);
    }

    @GetMapping("/admin/displayUser/{id}")
    public ResponseEntity<CommonResponse> displayUser(@PathVariable int id) {
        return adminService.displayUser(id);
    }

    @PostMapping("/admin/lockUser/{id}")
    public ResponseEntity<CommonResponse> lockUser(@PathVariable int id) {
        return adminService.lockUnlockUser(id, true);
    }

    @PostMapping("/admin/unlockUser/{id}")
    public ResponseEntity<CommonResponse> unlockUser(@PathVariable int id) {
        return adminService.lockUnlockUser(id, false);
    }

}

