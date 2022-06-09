package br.com.karate.repository.graduation;

import br.com.karate.model.graduation.Graduation;
import br.com.karate.model.graduation.GraduationInput;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

import static br.com.karate.model.graduation.QGraduation.graduation;

@Repository
public class GraduationCustomRepositoryImpl implements GraduationCustomRepository {

    @PersistenceContext
    private EntityManager em;


    @Override
    public Page<Graduation> list(GraduationInput.Filter filter, Pageable pageable) {
        BooleanBuilder predicate = new BooleanBuilder();

        if (filter.code > 0) {
            predicate.and(graduation.code.eq(filter.code));
        }

        if (filter.date != null) {
            predicate.and(graduation.date.eq(filter.date));
        }

        if (filter.title != null && filter.title.length() > 0) {
            predicate.and(graduation.title.likeIgnoreCase("%" + filter.title + "%"));
        }
        if (filter.description != null && filter.description.length() > 0) {
            predicate.and(graduation.description.likeIgnoreCase("%" + filter.description + "%"));
        }
        if (filter.place != null && filter.place.length() > 0) {
            predicate.and(graduation.place.likeIgnoreCase("%" + filter.place + "%"));
        }

        if (filter.athletesCode != null && filter.athletesCode.size() > 0) {
            predicate.and(graduation.athleteGraduations.any().athlete.person.code.in(filter.athletesCode));
        }

        JPAQuery<Graduation> query = new JPAQuery<>(em);
        query.select(graduation).from(graduation).where(predicate);

        final long total = query.stream().count();
        List<Graduation> graduations = query.limit(pageable.getPageSize()).offset(pageable.getOffset()).fetch();
        return new PageImpl<>(graduations, pageable, total);
    }
}
