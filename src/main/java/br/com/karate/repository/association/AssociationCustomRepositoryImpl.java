package br.com.karate.repository.association;

import br.com.karate.model.association.Association;
import br.com.karate.model.association.AssociationInput;
import br.com.karate.model.association.QAssociation;
import br.com.karate.model.athlete.QAthlete;
import br.com.karate.model.person.QPerson;
import br.com.karate.repository.AbstractRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static br.com.karate.model.association.QAssociation.association;

@Repository
public class AssociationCustomRepositoryImpl extends AbstractRepository<Association, QAssociation> implements AssociationCustomRepository {

    @Override
    protected QAssociation getEntityPath() {
        return association;
    }

    @Override
    protected PathBuilder<Association> getPathBuilder() {
        return new PathBuilder(Association.class, Association.class.getSimpleName().toLowerCase());
    }

    @Override
    public Page<Association> list(AssociationInput.Filter filter, Pageable pageable) {

        final BooleanBuilder predicate = new BooleanBuilder();

        if (filter.name != null && filter.name.trim().isBlank()) {
            predicate.and(association.name.likeIgnoreCase("%" + filter.name + "%"));
        }

        if (filter.code > 0) {
            predicate.and(association.code.eq(filter.code));
        }

        if (filter.city != null && filter.city.trim().isBlank()) {
            predicate.and(association.city.likeIgnoreCase("%" + filter.city + "%"));
        }

        if (filter.manager != null && filter.manager.code > 0) {
            predicate.and(association.manager.code.eq(filter.manager.code));
        }

        return find(pageable, predicate);
    }

    @Override
    public List<Association> topAssociationsByAthletesNumber() {
        JPAQuery<Association> query = new JPAQuery(em);

        query.select(association)//
                .from(association) //
                .join(QPerson.person) //
                .on(QPerson.person.association.id.eq(association.id)) //
                .where(QPerson.person.id.in(JPAExpressions.select(QAthlete.athlete.person.id).from(QAthlete.athlete))) //
                .groupBy(association) //
                .limit(10) //
                .orderBy(new OrderSpecifier(Order.DESC, association.people.size()));

        return query.fetch();
    }
}
