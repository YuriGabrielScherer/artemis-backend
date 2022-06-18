package br.com.karate.repository.graduation;

import br.com.karate.model.graduation.Graduation;
import br.com.karate.model.graduation.GraduationInput;
import br.com.karate.model.graduation.QGraduation;
import br.com.karate.repository.AbstractRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.PathBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import static br.com.karate.model.graduation.QGraduation.graduation;

@Repository
public class GraduationCustomRepositoryImpl extends AbstractRepository<Graduation, QGraduation> implements GraduationCustomRepository {

    @Override
    protected QGraduation getEntityPath() {
        return graduation;
    }

    @Override
    protected PathBuilder<Graduation> getPathBuilder() {
        return new PathBuilder(Graduation.class, Graduation.class.getSimpleName().toLowerCase());
    }

    @Override
    public Page<Graduation> list(GraduationInput.Filter filter, Pageable pageable) {
        BooleanBuilder predicate = new BooleanBuilder();

        if (filter.code > 0) {
            predicate.and(graduation.code.eq(filter.code));
        }

        if (filter.dateBegin != null) {
            predicate.and(graduation.date.after(filter.dateBegin.toLocalDate()));
        }

        if (filter.dateEnd != null) {
            predicate.and(graduation.date.before(filter.dateBegin.toLocalDate()));
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

        return find(pageable, predicate);
    }

}
