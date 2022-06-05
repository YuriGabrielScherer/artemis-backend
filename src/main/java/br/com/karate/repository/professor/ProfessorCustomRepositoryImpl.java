package br.com.karate.repository.professor;

import br.com.karate.model.graduation.Graduation;
import br.com.karate.model.professor.Professor;
import br.com.karate.model.professor.QProfessor;
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

@Repository
public class ProfessorCustomRepositoryImpl implements ProfessorCustomRepository {

    @PersistenceContext
    private EntityManager em;

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

    private QProfessor getEntityPath() {
        return QProfessor.professor;
    }
}
