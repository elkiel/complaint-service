package pl.complaint.app.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import pl.complaint.app.frontend.api.ComplaintsApi;
import pl.complaint.app.frontend.model.ComplaintCreateRequestRest;
import pl.complaint.app.frontend.model.ComplaintResponseRest;
import pl.complaint.app.frontend.model.ComplaintRest;
import pl.complaint.app.frontend.model.ComplaintsRequestRest;
import pl.complaint.app.model.Complaint;
import pl.complaint.app.model.mapper.ComplaintCreateRequestMapper;
import pl.complaint.app.model.mapper.ComplaintCriteriaMapper;
import pl.complaint.app.model.mapper.ComplaintRestMapper;
import pl.complaint.app.model.mapper.ComplaintsPaging;
import pl.complaint.app.model.mapper.ComplaintsResponseMapper;
import pl.complaint.app.service.ComplaintService;
import pl.complaint.app.util.HttpReqRespUtils;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class ComplaintsController implements ComplaintsApi {

    private final ComplaintService complaintService;

    @Override
    public ResponseEntity<ComplaintRest> createComplaint(ComplaintCreateRequestRest complaintCreateRequestRest) {
        var complaintCreateRequest = ComplaintCreateRequestMapper.mapToCreateRequestDto(complaintCreateRequestRest);
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .currentRequestAttributes()).getRequest();
        var ip = HttpReqRespUtils.resolveClientIp(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ComplaintRestMapper.fromComplaint(complaintService.saveComplaint(complaintCreateRequest, ip)));
    }


    @Override
    public ResponseEntity<ComplaintResponseRest> getComplaints(ComplaintsRequestRest request) {
        var pageable = ComplaintsPaging.pageableFrom(request.getMeta());
        var page = complaintService.findComplaints(pageable, ComplaintCriteriaMapper.mapToComplaintsCriteriaDto(request.getCriteria()));
        return ResponseEntity.ok(ComplaintsResponseMapper.from(page));
    }

    @Override
    public ResponseEntity<ComplaintRest> updateComplaintContent(UUID id, String content) {
        Complaint updatedComplaint = complaintService.updateContent(id, content);
        return ResponseEntity.ok(ComplaintRestMapper.fromComplaint(updatedComplaint));
    }

}
