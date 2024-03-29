package vn.com.assistant.fqcbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.com.assistant.fqcbackend.entity.Color;

import java.util.List;
@Repository
public interface ColorRepository  extends JpaRepository<Color, String> {
    List<Color> findAllByOrderByCreatedTimeDesc();
}
