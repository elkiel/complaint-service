package pl.complaint.app.model.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import pl.complaint.app.frontend.model.ComplaintRest;
import pl.complaint.app.model.Complaint;
import pl.complaint.app.model.dto.ComplaintFindProjection;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ComplaintRestMapper {

    public static ComplaintRest fromComplaint(Complaint complaint) {
        return complaint == null ? new ComplaintRest() : new ComplaintRest()
                .id(complaint.getId())
                .productId(complaint.getProductId())
                .content(complaint.getContent())
                .reporter(complaint.getReporter())
                .country(complaint.getCountry())
                .createdAt(complaint.getCreatedAt())
                .updatedAt(complaint.getUpdatedAt())
                .counter(complaint.getCounter());
    }


    public static ComplaintRest from(ComplaintFindProjection complaint) {
        return complaint == null ? new ComplaintRest() : new ComplaintRest()
                .id(complaint.getId())
                .productId(complaint.getProductId())
                .content(complaint.getContent())
                .reporter(complaint.getReporter())
                .country(complaint.getCountry())
                .createdAt(complaint.getCreatedAt())
                .updatedAt(complaint.getUpdatedAt())
                .counter(complaint.getCounter());
    }
}
