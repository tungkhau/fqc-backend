package vn.com.assistant.fqcbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.com.assistant.fqcbackend.entity.InspectingSession;


@Repository
public interface InspectingSessionRepository extends JpaRepository<InspectingSession, String> {
}
