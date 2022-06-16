package br.com.karate.repository.professor;

import br.com.karate.model.graduation.Graduation;
import br.com.karate.model.professor.Professor;
import br.com.karate.model.professor.ProfessorInput;
import br.com.karate.model.professor.QProfessor;
import br.com.karate.repository.AbstractRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

import static br.com.karate.model.professor.QProfessor.professor;

@Repository
public class ProfessorCustomRepositoryImpl extends AbstractRepository<Professor, QProfessor> implements ProfessorCustomRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    protected QProfessor getEntityPath() {
        return QProfessor.professor;
    }

    @Override
    protected PathBuilder<Professor> getPathBuilder() {
        return new PathBuilder(Professor.class, Professor.class.getSimpleName().toLowerCase());
    }

    @Override
    public Page<Professor> findAvailableProfessorsToGraduation(Graduation event, Pageable pageable) {
        JPQLQuery<Professor> query = new JPAQuery(em);

        query.from(getEntityPath())//
                .where(getEntityPath().id.notIn( //
                        JPAExpressions.select(getEntityPath().id) //
                                .from(getEntityPath()) //
                                .where(getEntityPath().graduations.contains(event)) //
                ));

        final long count = query.fetchCount();
        final List<Professor> professors = query.limit(pageable.getPageSize()).offset(pageable.getOffset()).fetch();
        return new PageImpl<>(professors, pageable, count);
    }

    @Override
    public Page<Professor> list(ProfessorInput.Filter filter, Pageable pageable) {
        final BooleanBuilder predicate = new BooleanBuilder();

        if (filter!= null && filter.person != null) {
            if (filter.person.name != null && filter.person.name.trim().length() > 0) {
                predicate.and(professor.person.name.likeIgnoreCase("%" + filter.person.name + "%"));
            }

            if (filter.person.document != null && filter.person.document.trim().length() > 0) {
                predicate.and(professor.person.document.like(filter.person.document));
            }

            if (filter.person.code > 0) {
                predicate.and(professor.person.code.eq(filter.person.code));
            }

            if (filter.person.birth != null) {
                predicate.and(professor.person.birth.eq(filter.person.birth));
            }
        }
        return find(pageable, predicate);
    }

}
