package pl.complaint.app.model.mapper;


import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import pl.complaint.app.frontend.model.MetaDataRest;
import pl.complaint.app.frontend.model.OrderByRest;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ComplaintsPaging {

    private static final List<String> ALLOWED_SORT_FIELDS = List.of(
            "id", "productId", "reporter", "country", "createdAt", "updatedAt"
    );

    public static Sort sortFrom(String sortBy, OrderByRest orderBy) {
        Paging.checkSortParam(sortBy, ALLOWED_SORT_FIELDS);
        return Paging.sortFrom(sortBy, orderBy);
    }

    public static Pageable pageableFrom(MetaDataRest meta) {
        Paging.checkSortParam(meta, ALLOWED_SORT_FIELDS);
        return Paging.pageableFrom(meta);
    }
}
