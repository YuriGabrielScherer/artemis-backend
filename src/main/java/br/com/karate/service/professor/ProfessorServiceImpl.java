package br.com.karate.service.professor;

import br.com.karate.model.graduation.Graduation;
import br.com.karate.model.person.Person;
import br.com.karate.model.professor.Professor;
import br.com.karate.model.professor.ProfessorInput;
import br.com.karate.model.util.pageable.PageableDto;
import br.com.karate.repository.professor.ProfessorCustomRepository;
import br.com.karate.repository.professor.ProfessorRepository;
import br.com.karate.service.person.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ProfessorServiceImpl implements ProfessorService {

    @Autowired
    private ProfessorRepository repository;

    @Autowired
    private ProfessorCustomRepository customRepository;

    @Autowired
    private PersonService personService;

    @Override
    public Professor save(Professor input) {
        Professor professor = findByCode(input.getPerson().getCode());
        if (professor == null) {
            professor = new Professor();
            final Person person = personService.findByCodeThrowsException(input.getPerson().getCode());
            if (person.getAssociation() == null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Professores devem estar associados a uma associação.");
            }
            professor.setPerson(person);
        }

        return repository.save(professor);
    }

    @Override
    public List<Professor> findAll() {
        return repository.findAll();
    }

    @Override
    public Professor findByCode(long code) {
        final Person person = personService.findByCode(code);

        if (person == null) {
            return null;
        }

        return person.getProfessor();
    }

    @Override
    public Professor findByCodeThrowsException(long code) {
        final Professor professor = personService.findByCodeThrowsException(code).getProfessor();
        if (professor == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Professor não encontrado.");
        }
        return professor;
    }

    @Override
    public Page<Professor> list(PageableDto input, ProfessorInput.Filter filter) {
        return customRepository.list(filter, input.getPageable());
    }

    @Override
    public boolean delete(long code) {
        final Person person = personService.findByCodeThrowsException(code);
        final Professor professor = person.getProfessor();
        if (professor == null) {
            return true;
        }

        repository.delete(professor);
        return true;
    }

    @Override
    public List<Professor> findAllByCode(List<Long> codeList) {
        return repository.findAllByPersonCodeIn(codeList);
    }

    @Override
    public Page<Professor> findAvailableProfessorsToGraduation(Graduation graduation, PageableDto input) {
        return customRepository.findAvailableProfessorsToGraduation(graduation, input.getPageable());
    }

    @Override
    public Page<Professor> findProfessorsByGraduation(Graduation graduation, PageableDto input) {
        return repository.findAllByGraduationsIn(List.of(graduation), input.getPageable());
    }
}
