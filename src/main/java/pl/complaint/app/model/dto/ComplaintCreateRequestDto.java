package pl.complaint.app.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ComplaintCreateRequestDto {

    @NotNull(message = "Content must not be null")
    private UUID productId;
    @NotBlank(message = "Content must not be blank")
    private String content;
    @Email(message = "Reporter must be a valid email address")
    @NotBlank(message = "Reporter must not be blank")
    private String reporter;

}
