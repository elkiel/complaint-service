package pl.complaint.app.util;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.Optional;

public class DateUtils {

    public static Optional<OffsetDateTime> toOffsetDateTime(LocalDate localDate) {
        return Optional.ofNullable(localDate)
                .map(date -> date.atStartOfDay(ZoneId.systemDefault()).toOffsetDateTime());
    }


}
