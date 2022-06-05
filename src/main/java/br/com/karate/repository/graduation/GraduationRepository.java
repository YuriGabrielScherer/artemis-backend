package br.com.karate.repository.graduation;

import br.com.karate.model.graduation.Graduation;
import br.com.karate.repository.AbstractRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GraduationRepository extends AbstractRepository<Graduation> {

    public List<Graduation> findAllByCodeIn(List<Long> codeList);

    public Page<Graduation> findAllByCode(long code, Pageable pageable);

}
