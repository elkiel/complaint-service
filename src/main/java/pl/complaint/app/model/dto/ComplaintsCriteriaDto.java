package pl.complaint.app.model.dto;

import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Value
@Builder
public class ComplaintsCriteriaDto {
    UUID id;
    UUID productId;
    String content;
    String reporter;
    String country;
    OffsetDateTime createdFrom;
    OffsetDateTime createdTo;
    BigDecimal counter;
}
