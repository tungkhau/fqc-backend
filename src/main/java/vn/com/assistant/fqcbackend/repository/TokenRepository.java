package vn.com.assistant.fqcbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.com.assistant.fqcbackend.entity.Token;

@Repository
public interface TokenRepository extends JpaRepository<Token, String> {
    boolean findByUserCodeAndValue(String userCode, String value);
}
