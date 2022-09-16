package vn.com.assistant.fqcbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.com.assistant.fqcbackend.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByCode(String code);
}
