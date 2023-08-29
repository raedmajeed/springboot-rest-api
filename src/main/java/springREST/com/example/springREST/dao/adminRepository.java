package springREST.com.example.springREST.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import springREST.com.example.springREST.entity.admin;

@Repository
public interface adminRepository extends JpaRepository<admin, Long> {
    admin findByUsername(String username);
}
