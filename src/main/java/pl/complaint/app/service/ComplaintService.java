package pl.complaint.app.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.complaint.app.model.Complaint;
import pl.complaint.app.model.dto.ComplaintCreateRequestDto;
import pl.complaint.app.model.dto.ComplaintFindProjection;
import pl.complaint.app.model.dto.ComplaintsCriteriaDto;
import pl.complaint.app.repository.ComplaintQueryRepository;
import pl.complaint.app.repository.ComplaintRepository;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ComplaintService {

    private final ComplaintQueryRepository complaintQueryRepository;
    private final ComplaintRepository complaintRepository;
    private final CountryService countryService;

    public Page<ComplaintFindProjection> findComplaints(Pageable pageable, ComplaintsCriteriaDto complaintsCriteriaDto) {
        return complaintQueryRepository.findByCriteria(complaintsCriteriaDto, pageable);
    }

    public Complaint updateContent(UUID id, String newContent) {
        return complaintRepository.findById(id)
                .map(complaint -> {
                    complaint.setContent(newContent);
                    return complaintRepository.save(complaint);
                })
                .orElseThrow(() -> new EntityNotFoundException("Complaint not found: " + id));
    }

    public Complaint saveComplaint(ComplaintCreateRequestDto request, String ip) {
        UUID productId = request.getProductId();
        String reporter = request.getReporter();

        return complaintRepository
                .findByProductIdAndReporter(productId, reporter)
                .map(existing -> {
                    existing.incrementCounter();
                    return complaintRepository.save(existing);
                })
                .orElseGet(() -> {
                    Complaint newComplaint = Complaint.builder()
                            .productId(productId)
                            .reporter(reporter)
                            .content(request.getContent())
                            .country(countryService.resolveCountry(ip))
                            .counter(BigDecimal.ONE)
                            .build();
                    return complaintRepository.save(newComplaint);
                });
    }

}
