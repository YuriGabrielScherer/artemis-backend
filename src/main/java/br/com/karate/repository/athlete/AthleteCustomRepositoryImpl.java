package br.com.karate.repository.athlete;

import br.com.karate.model.athlete.Athlete;
import br.com.karate.model.athlete.QAthlete;
import br.com.karate.model.graduation.Graduation;
import br.com.karate.model.graduation.athlete.QAthleteGraduation;
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
public class AthleteCustomRepositoryImpl implements AthleteCustomRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Page<Athlete> findAvailableAthletesToGraduation(Graduation graduation, Pageable pageable) {
        JPQLQuery<Athlete> query = new JPAQuery(em);

        query.from(getEntityPath())//
                .where(getEntityPath().id.notIn( //
                        JPAExpressions.select(QAthleteGraduation.athleteGraduation.athlete.id) //
                                .from(QAthleteGraduation.athleteGraduation) //
                                .where(QAthleteGraduation.athleteGraduation.graduation.eq(graduation))));//

        final long count = query.fetchCount();
        final List<Athlete> athletes = query.limit(pageable.getPageSize()).offset(pageable.getOffset()).fetch();
        return new PageImpl<>(athletes, pageable, count);
    }

    private QAthlete getEntityPath() {
        return QAthlete.athlete;
    }
}
