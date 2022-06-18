package br.com.karate.repository.athlete;

import br.com.karate.model.athlete.Athlete;
import br.com.karate.model.athlete.AthleteInput;
import br.com.karate.model.athlete.QAthlete;
import br.com.karate.model.graduation.Graduation;
import br.com.karate.model.graduation.athlete.QAthleteGraduation;
import br.com.karate.model.person.Person;
import br.com.karate.repository.AbstractRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

import static br.com.karate.model.athlete.QAthlete.athlete;

@Repository
public class AthleteCustomRepositoryImpl extends AbstractRepository<Athlete, QAthlete> implements AthleteCustomRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    protected QAthlete getEntityPath() {
        return QAthlete.athlete;
    }

    @Override
    protected PathBuilder<Athlete> getPathBuilder() {
        return new PathBuilder(Athlete.class, Athlete.class.getSimpleName().toLowerCase());
    }

    @Override
    public Page<Athlete> findAvailableAthletesToGraduation(Graduation graduation, Pageable pageable) {
        JPQLQuery<Athlete> query = new JPAQuery(em);

        query.from(getEntityPath())//
                .where(getEntityPath().id.notIn( //
                        JPAExpressions.select(QAthleteGraduation.athleteGraduation.athlete.id) //
                                .from(QAthleteGraduation.athleteGraduation) //
                                .where(QAthleteGraduation.athleteGraduation.graduation.eq(graduation))));//

        final long count = query.fetchCount();
        PathBuilder<Person> orderByExpression = new PathBuilder(Athlete.class, Athlete.class.getSimpleName().toLowerCase());
        for (Sort.Order o : pageable.getSort()) {
            query.orderBy(new OrderSpecifier(o.isAscending() ? Order.ASC : Order.DESC, orderByExpression.get(o.getProperty())));
        }
        final List<Athlete> athletes = query.limit(pageable.getPageSize()).offset(pageable.getOffset()).fetch();
        return new PageImpl<>(athletes, pageable, count);
    }

    @Override
    public Page<Athlete> list(AthleteInput.Filter filter, Pageable pageable) {

        final BooleanBuilder predicate = new BooleanBuilder();

        if (filter.person.name != null && filter.person.name.trim().length() > 0) {
            predicate.and(athlete.person.name.likeIgnoreCase("%" + filter.person.name + "%"));
        }

        if (filter.person.document != null && filter.person.document.trim().length() > 0) {
            predicate.and(athlete.person.document.like(filter.person.document));
        }

        if (filter.person.code > 0) {
            predicate.and(athlete.person.code.eq(filter.person.code));
        }

        if (filter.person.birth != null) {
            predicate.and(athlete.person.birth.eq(filter.person.birth));
        }

        if (filter.belt != null) {
            predicate.and(athlete.currentBelt.belt.eq(filter.belt));
        }

        return find(pageable, predicate);
    }


}
