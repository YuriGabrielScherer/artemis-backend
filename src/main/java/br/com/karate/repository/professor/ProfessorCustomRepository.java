package br.com.karate.repository.professor;

import br.com.karate.model.graduation.Graduation;
import br.com.karate.model.professor.Professor;
import br.com.karate.model.professor.ProfessorInput;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProfessorCustomRepository {

    public Page<Professor> findAvailableProfessorsToGraduation(Graduation event, Pageable pageable);

    public Page<Professor> list(ProfessorInput.Filter filter, Pageable pageable);

}
