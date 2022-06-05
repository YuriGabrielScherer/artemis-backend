package br.com.karate.repository.belt;

import br.com.karate.enums.EnumBelt;
import br.com.karate.model.belt.Belt;
import br.com.karate.model.belt.QBelt;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class BeltCustomRepositoryImpl implements BeltCustomRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Belt findNextBelt(EnumBelt belt) {
        JPQLQuery<Belt> query = new JPAQuery(em);

        query.from(getEntityPath())//
                .where(getEntityPath().sequence.eq( //
                        JPAExpressions.select(getEntityPath().sequence.add(1)) //
                                .from(getEntityPath()) //
                                .where(getEntityPath().belt.eq(belt))//
                ));

        return query.fetchOne();
    }

    private QBelt getEntityPath() {
        return QBelt.belt1;
    }


}
