package br.com.karate.repository;

import com.querydsl.core.types.EntityPath;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Component
public abstract class AbstractRepository<T, E extends EntityPath<T>> {
    @PersistenceContext
    protected EntityManager em;

    protected abstract E getEntityPath();

    protected abstract PathBuilder<T> getPathBuilder();

    public Page<T> find(Pageable pageable, Predicate predicate) {
        JPQLQuery<T> query = new JPAQuery<T>(em);
        query.from(getEntityPath());
        query.where(predicate);

        final long total = query.fetchCount();

        query.offset(pageable.getOffset()).limit(pageable.getPageSize());

        PathBuilder<T> orderByExpression = getPathBuilder();
        for (Sort.Order o : pageable.getSort()) {
            query.orderBy(new OrderSpecifier(o.isAscending() ? Order.ASC : Order.DESC, orderByExpression.get(o.getProperty())));
        }
        final List<T> results = query.fetch();

        return new PageImpl<>(results, pageable, total);
    }
}
