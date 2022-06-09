package br.com.karate.repository.person;

import br.com.karate.model.athlete.QAthlete;
import br.com.karate.model.person.Person;
import br.com.karate.model.person.PersonInput;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

import static br.com.karate.model.person.QPerson.person;

@Repository
public class PersonCustomRepositoryImpl implements PersonCustomRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Page<Person> list(PersonInput.Filter filter, Pageable pageable) {
        final BooleanBuilder predicate = new BooleanBuilder();

        if (filter.name != null && filter.name.trim().length() > 0) {
            predicate.and(person.name.likeIgnoreCase("%" + filter.name + "%"));
        }

        if (filter.document != null && filter.document.trim().length() > 0) {
            predicate.and(person.document.like(filter.document));
        }

        if (filter.code > 0) {
            predicate.and(person.code.eq(filter.code));
        }

        if (filter.birth != null) {
            predicate.and(person.birth.eq(filter.birth));
        }

        JPAQuery<Person> query = new JPAQuery(em);
        query.select(person).from(person).where(predicate);

        if (filter.onlyPerson == true) {
            query.where(person.id.notIn(JPAExpressions.select(QAthlete.athlete.person.id).from(QAthlete.athlete)));
        }

        if (filter.onlyAthlete == true) {
            query.where(person.id.in(JPAExpressions.select(QAthlete.athlete.person.id).from(QAthlete.athlete)));
        }

        final long count = query.stream().count();


        PathBuilder<Person> orderByExpression = new PathBuilder(Person.class, Person.class.getSimpleName().toLowerCase());
        for (Sort.Order o : pageable.getSort()) {
            query.orderBy(new OrderSpecifier(o.isAscending() ? Order.ASC : Order.DESC, orderByExpression.get(o.getProperty())));
        }
        final List<Person> people = query.limit(pageable.getPageSize()).offset(pageable.getOffset()).fetch();

        return new PageImpl<>(people, pageable, count);
    }


}
