package pl.complaint.app.model.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import pl.complaint.app.controller.exception.model.InvalidSortParamException;
import pl.complaint.app.frontend.model.MetaDataRest;
import pl.complaint.app.frontend.model.OrderByRest;

import java.util.List;

import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.springframework.data.domain.Sort.Direction.ASC;
import static org.springframework.data.domain.Sort.Direction.DESC;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Paging {

    public static void checkSortParam(MetaDataRest meta, List<String> allowedValues) {
        if (meta == null) {
            return;
        }
        checkSortParam(meta.getSortBy(), allowedValues);
    }

    public static void checkSortParam(String sortBy, List<String> allowedValues) {
        if (isBlank(sortBy)) {
            return;
        }
        if (!allowedValues.contains(sortBy)) {
            throw new InvalidSortParamException("Invalid sort param, allowed values: " + String.join(", ", allowedValues));
        }
    }

    public static Pageable pageableFrom(MetaDataRest meta) {
        if (isBlank(meta.getSortBy())) {
            return PageRequest.of(meta.getActualPage().intValue(), meta.getPageSize().intValue());
        }
        var direction = meta.getOrderBy() == OrderByRest.DESC ? DESC : ASC;
        return PageRequest.of(meta.getActualPage().intValue(), meta.getPageSize().intValue(), Sort.by(direction, meta.getSortBy()));
    }

    public static Sort sortFrom(String sortBy, OrderByRest order) {
        if (isNotBlank(sortBy) && order != null) {
            var direction = order == OrderByRest.DESC ? DESC : ASC;
            return Sort.by(direction, sortBy);
        }
        return Sort.unsorted();
    }
}
