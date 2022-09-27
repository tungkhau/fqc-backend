package vn.com.assistant.fqcbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.com.assistant.fqcbackend.entity.Fabric;

import java.util.List;
@Repository
public interface FabricRepository extends JpaRepository<Fabric, String> {
    List<Fabric> findAllByOrderByCreatedTimeDesc();
}
