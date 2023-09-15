package springREST.com.example.springREST.dto;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

@NoArgsConstructor
@Component
public class JsonResponse extends CommonResponse{
    private Map<Integer, Map<String, String>> userList;

    public Map<Integer, Map<String, String>> getUserList() {
        return userList;
    }

    public void setUserList(Map<Integer, Map<String, String>> userList) {
        this.userList = userList;
    }
}
