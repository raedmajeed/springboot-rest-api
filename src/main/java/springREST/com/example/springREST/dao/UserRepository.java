package springREST.com.example.springREST.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import springREST.com.example.springREST.entity.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, CrudRepository<User, Long> {
    User findByUsername(String username);
    Optional<User> findById(int id);

    List<User> findByUsernameContaining(String name);
    List<User> findAll();
    void deleteById(int id);

//    @Query(
//            value = "alter table user_table set "
//    )
//    User updateUser(int id);
}
