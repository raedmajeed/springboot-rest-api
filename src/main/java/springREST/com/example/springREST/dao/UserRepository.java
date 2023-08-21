package springREST.com.example.springREST.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;
import springREST.com.example.springREST.entity.User;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query(
            "SELECT u FROM User u WHERE u.username = :username"

    )
    User getUserByUsername(String username);

    UserDetails findByUsername(String username);
}
