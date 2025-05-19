package pl.complaint.app.model.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import pl.complaint.app.frontend.model.ComplaintResponseRest;
import pl.complaint.app.model.dto.ComplaintFindProjection;

import java.math.BigDecimal;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ComplaintsResponseMapper {

    public static ComplaintResponseRest from(Page<ComplaintFindProjection> page) {
        return new ComplaintResponseRest()
                .itemsTotal(BigDecimal.valueOf(page.getTotalElements()))
                .data(page.stream().map(ComplaintRestMapper::from).toList());
    }
}
