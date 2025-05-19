package pl.complaint.app.repository;

import com.querydsl.core.types.ConstructorExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import pl.complaint.app.model.Complaint;
import pl.complaint.app.model.dto.ComplaintFindProjection;
import pl.complaint.app.model.dto.ComplaintsCriteriaDto;

import static com.querydsl.core.types.Projections.constructor;
import static pl.complaint.app.model.QComplaint.complaint;

@Repository
public class ComplaintQueryRepository extends QuerydslRepository {

    private final JPAQueryFactory factory;

    public ComplaintQueryRepository(EntityManager entityManager) {
        super(Complaint.class);
        this.factory = new JPAQueryFactory(entityManager);
    }

    public Page<ComplaintFindProjection> findByCriteria(ComplaintsCriteriaDto criteria, Pageable pageable) {
        var query = complaintFindSelect();
        applyCriteria(query, criteria);
        var content = querydsl().applyPagination(pageable, query).fetch();
        return PageableExecutionUtils.getPage(content, pageable, () -> findComplaintsCount(criteria));
    }

    public Long findComplaintsCount(ComplaintsCriteriaDto criteriaDto) {
        var query = factory.select(complaint.count()).from(complaint);
        applyCriteria(query, criteriaDto);
        return query.fetchOne();
    }

    private void applyCriteria(JPAQuery<?> query, ComplaintsCriteriaDto criteriaDto) {
        if (criteriaDto.getProductId() != null) {
            query.where(complaint.productId.eq(criteriaDto.getProductId()));
        }

        if (criteriaDto.getReporter() != null && !criteriaDto.getReporter().isBlank()) {
            query.where(complaint.reporter.equalsIgnoreCase(criteriaDto.getReporter()));
        }

        if (criteriaDto.getCountry() != null && !criteriaDto.getCountry().isBlank()) {
            query.where(complaint.country.eq(criteriaDto.getCountry()));
        }

        if (criteriaDto.getCreatedFrom() != null) {
            query.where(complaint.createdAt.goe(criteriaDto.getCreatedFrom()));
        }

        if (criteriaDto.getCreatedTo() != null) {
            query.where(complaint.createdAt.loe(criteriaDto.getCreatedTo()));
        }
    }

    private JPAQuery<ComplaintFindProjection> complaintFindSelect() {
        return factory.select(complaintFindProjection())
                .from(complaint);
    }

    private ConstructorExpression<ComplaintFindProjection> complaintFindProjection() {
        return constructor(ComplaintFindProjection.class,
                complaint.id,
                complaint.productId,
                complaint.content,
                complaint.reporter,
                complaint.country,
                complaint.createdAt,
                complaint.updatedAt,
                complaint.counter);
    }
}

