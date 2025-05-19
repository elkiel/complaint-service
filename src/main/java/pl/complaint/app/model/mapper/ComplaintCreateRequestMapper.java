package pl.complaint.app.model.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import pl.complaint.app.frontend.model.ComplaintCreateRequestRest;
import pl.complaint.app.model.dto.ComplaintCreateRequestDto;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ComplaintCreateRequestMapper {

    public static ComplaintCreateRequestDto mapToCreateRequestDto(ComplaintCreateRequestRest rest) {
        return ComplaintCreateRequestDto.builder()
                .productId(rest.getProductId())
                .content(rest.getContent())
                .reporter(rest.getReporter())
                .build();
    }

}
