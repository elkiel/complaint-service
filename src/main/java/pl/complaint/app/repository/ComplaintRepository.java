package pl.complaint.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.complaint.app.model.Complaint;

import java.util.Optional;
import java.util.UUID;

public interface ComplaintRepository extends JpaRepository<Complaint, UUID> {

    Optional<Complaint> findByProductIdAndReporter(UUID productId, String reporter);

}
