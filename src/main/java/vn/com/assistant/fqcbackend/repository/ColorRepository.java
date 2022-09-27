package vn.com.assistant.fqcbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.com.assistant.fqcbackend.entity.Color;

import java.util.List;

public interface ColorRepository  extends JpaRepository<Color, String> {
    List<Color> findAllByOrderByCreatedTimeDesc();
}
