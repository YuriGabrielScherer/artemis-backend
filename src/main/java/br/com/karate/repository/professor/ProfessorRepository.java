package br.com.karate.repository.professor;

import br.com.karate.model.graduation.Graduation;
import br.com.karate.model.professor.Professor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProfessorRepository extends JpaRepository<Professor, UUID>, QuerydslPredicateExecutor<Professor> {
    public List<Professor> findAllByPersonCodeIn(List<Long> codeList);

    public Page<Professor> findAllByGraduationsIn(List<Graduation> graduationList, Pageable pageable);
}
