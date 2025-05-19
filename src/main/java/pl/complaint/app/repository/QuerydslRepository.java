package pl.complaint.app.repository;

import org.springframework.data.jpa.repository.support.Querydsl;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

public class QuerydslRepository extends QuerydslRepositorySupport {

    protected QuerydslRepository(Class<?> domainClass) {
        super(domainClass);
    }

    protected Querydsl querydsl() {
        var querydsl = super.getQuerydsl();
        if(querydsl == null) {
            throw new IllegalStateException("Querydsl helper not available");
        }
        return querydsl;
    }

}
