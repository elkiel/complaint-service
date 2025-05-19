package pl.complaint.app.model.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Data
public class ComplaintFindProjection {
    private final UUID id;
    private final UUID productId;
    private final String content;
    private final String reporter;
    private final String country;
    private final OffsetDateTime createdAt;
    private final OffsetDateTime updatedAt;
    private final BigDecimal counter;
}
