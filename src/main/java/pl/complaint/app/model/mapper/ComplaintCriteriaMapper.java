package pl.complaint.app.model.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import pl.complaint.app.frontend.model.ComplaintsCriteriaRest;
import pl.complaint.app.model.dto.ComplaintsCriteriaDto;
import pl.complaint.app.util.DateUtils;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ComplaintCriteriaMapper {

    public static ComplaintsCriteriaDto mapToComplaintsCriteriaDto(ComplaintsCriteriaRest rest) {
        return rest == null ? ComplaintsCriteriaDto.builder().build() :
                ComplaintsCriteriaDto.builder()
                        .id(rest.getId())
                        .productId(rest.getProductId())
                        .content(rest.getContent())
                        .reporter(rest.getReporter())
                        .country(rest.getCountry())
                        .counter(rest.getCounter())
                        .createdFrom(DateUtils.toOffsetDateTime(rest.getCreatedFrom()).orElse(null))
                        .createdTo(DateUtils.toOffsetDateTime(rest.getCreatedTo()).orElse(null))
                        .build();
    }

}
