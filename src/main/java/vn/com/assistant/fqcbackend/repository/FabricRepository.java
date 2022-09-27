package vn.com.assistant.fqcbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.com.assistant.fqcbackend.entity.Fabric;

import java.util.List;

public interface FabricRepository extends JpaRepository<Fabric, String> {
    List<Fabric> findAllByOrderByCreatedTimeDesc();
}
