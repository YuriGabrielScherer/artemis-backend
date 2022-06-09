package br.com.karate.service.professor;

import br.com.karate.model.graduation.Graduation;
import br.com.karate.model.professor.Professor;
import br.com.karate.model.professor.ProfessorInput;
import br.com.karate.model.util.pageable.PageableDto;
import br.com.karate.service.abstracts.CrudAbstractService;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProfessorService extends CrudAbstractService<Professor, ProfessorInput.Filter> {

    public List<Professor> findAllByCode(List<Long> codeList);

    public Page<Professor> findAvailableProfessorsToGraduation(Graduation graduation, PageableDto pageable);

    public Page<Professor> findProfessorsByGraduation(Graduation graduation, PageableDto pageable);

}
