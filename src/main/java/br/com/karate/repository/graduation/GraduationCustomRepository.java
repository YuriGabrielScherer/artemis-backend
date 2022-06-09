package br.com.karate.repository.graduation;

import br.com.karate.model.graduation.Graduation;
import br.com.karate.model.graduation.GraduationInput;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface GraduationCustomRepository {

    public Page<Graduation> list(GraduationInput.Filter filter, Pageable pageable);

}
