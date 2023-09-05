package springREST.com.example.springREST.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import springREST.com.example.springREST.dto.CommonResponse;
import springREST.com.example.springREST.dto.JsonResponse;
import springREST.com.example.springREST.entity.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    public void findByUsernameContaining() {
        JsonResponse jsonResponse = new JsonResponse();
        List<User> usersList = userRepository.findByUsernameContaining("h");

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
        System.out.println(jsonResponse);
        System.out.println(userMap);
    }

    @Test
    public void findAllUsers() {
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
        jsonResponse.setResponseMessage("SEARCHED DATA");
        jsonResponse.setSuccess(true);
        jsonResponse.setStatus(HttpStatus.ACCEPTED);
        System.out.println(jsonResponse);
        System.out.println(userMap);
    }

    @Test
    public void findUser() {
        JsonResponse jsonResponse = new JsonResponse();
        Optional<User> user = userRepository.findById(2);
        Map<Integer, Map<String, String>> userMap = new HashMap<>();
        Map<String, String> userDetailsMap = new HashMap<>();
//        userDetailsMap.put("username", user.getUsername());
//        userDetailsMap.put("dob", user.getDob());
//        userDetailsMap.put("email", user.getEmail());
//        userMap.put(
//                user.getId(), userDetailsMap
//        );
        jsonResponse.setUserList(userMap);
        jsonResponse.setResponseMessage("SEARCHED DATA");
        jsonResponse.setSuccess(true);
        jsonResponse.setStatus(HttpStatus.ACCEPTED);
        System.out.println(jsonResponse);
        System.out.println(userMap);
    }

    @Test
    public void deleteUser() {
        userRepository.deleteById(2);
        List<User> users = userRepository.findAll();
        System.out.println(users);
    }

    @Test
    public void update() {
        CommonResponse response = new CommonResponse();
        Optional<User> userCheck = userRepository.findById(452);

        if (userCheck.isEmpty()) {
            response.setResponseMessage("NOT FOUND");
            response.setSuccess(false);
            response.setStatus(HttpStatus.BAD_REQUEST);
        }

        User userFromDB = userCheck.get();
        userFromDB.setAccountNonLocked(false);
        userRepository.save(userFromDB);
        System.out.println(userRepository.findById(452).get());

        response.setResponseMessage("USER LOCKED");
        response.setSuccess(true);
        response.setStatus(HttpStatus.ACCEPTED);
        }

}