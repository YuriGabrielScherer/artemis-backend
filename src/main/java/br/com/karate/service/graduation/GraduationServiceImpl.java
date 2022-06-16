package br.com.karate.service.graduation;

import br.com.karate.enums.EnumAthleteGraduationSituation;
import br.com.karate.enums.EnumGraduationSituation;
import br.com.karate.model.athlete.Athlete;
import br.com.karate.model.graduation.Graduation;
import br.com.karate.model.graduation.GraduationInput;
import br.com.karate.model.graduation.athlete.AthleteGraduation;
import br.com.karate.model.graduation.grades.GraduationGrade;
import br.com.karate.model.graduation.history.GraduationHistory;
import br.com.karate.model.professor.Professor;
import br.com.karate.model.util.pageable.PageableDto;
import br.com.karate.repository.graduation.GraduationCustomRepository;
import br.com.karate.repository.graduation.GraduationRepository;
import br.com.karate.service.athlete.AthleteService;
import br.com.karate.service.graduation.athlete.AthleteGraduationService;
import br.com.karate.service.graduation.grade.GraduationGradeService;
import br.com.karate.service.professor.ProfessorService;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class GraduationServiceImpl implements GraduationService {

    @Autowired
    private GraduationRepository repository;

    @Autowired
    private GraduationCustomRepository customRepository;

    @Autowired
    private ProfessorService professorService;

    @Autowired
    private AthleteGraduationService athleteGraduationService;

    @Autowired
    private AthleteService athleteService;

    @Autowired
    private GraduationGradeService graduationGradeService;

    @Override
    public Graduation save(Graduation input) {
        Graduation graduation = repository.findByCode(input.getCode()).orElse(null);

        if (graduation == null) {
            graduation = new Graduation();
            graduation.setCode(getNextCode());
        }

        graduation.setTitle(input.getTitle());
        graduation.setDescription(input.getDescription());
        graduation.setPlace(input.getPlace());

        if (graduation.getDate() == null && input.getDate().isBefore(LocalDate.now())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Data do exame não pode ser antes que a data atual.");
        }

        graduation.setDate(input.getDate());
        if (graduation.getHistory() == null || graduation.getHistory().isEmpty()) {
            final GraduationHistory history = new GraduationHistory();
            history.setSituation(EnumGraduationSituation.CREATED);
            graduation.addHistory(history);
            graduation.setSituation(history);
        }

        try {
            return repository.saveAndFlush(graduation);
        } catch (ConstraintViolationException ex) {
            if ("uk_graduations_code".equalsIgnoreCase(ex.getConstraintName())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Código do exame de graduação já cadastrado.");
            }
            throw ex;
        }
    }

    @Override
    public List<Graduation> findAll() {
        return repository.findAll();
    }

    @Override
    public Graduation findByCode(long code) {
        return repository.findByCode(code).orElse(null);
    }

    @Override
    public Graduation findByCodeThrowsException(long code) {
        final Graduation event = findByCode(code);
        if (event == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Exame de Graduação não encontrado.");
        }

        return event;
    }

    @Override
    public Page<Graduation> list(PageableDto input, GraduationInput.Filter filter) {
        return customRepository.list(filter, input.getPageable());
    }


    @Override
    public boolean delete(long code) {
        final Graduation graduation = repository.findByCode(code).orElse(null);
        if (graduation == null) {
            return true;
        }
        repository.delete(graduation);
        return true;
    }

    @Override
    public void updateStatus(Long code, EnumGraduationSituation newSituation) {
        final Graduation graduation = findByCodeThrowsException(code);

        if (EnumGraduationSituation.CREATED.equals(newSituation) && Boolean.FALSE.equals(EnumGraduationSituation.CREATED.equals(graduation.getSituation().getSituation()))) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Exame de graduação não pode voltar à situação \"Criado\"");
        }

        if (EnumGraduationSituation.FINISHED.equals(newSituation)) {
            finishGraduation(graduation);
        } else if (EnumGraduationSituation.CLOSE_SUBSCRIPTION.equals(newSituation)) {
            createGraduationGrades(graduation);
        }

        final GraduationHistory history = new GraduationHistory();
        history.setSituation(newSituation);
        graduation.addHistory(history);
        repository.save(graduation);
    }

    @Override
    public void registerProfessors(Long graduationCode, List<Long> professorsCode) {
        final Graduation graduation = findByCodeThrowsException(graduationCode);

        final List<Professor> professors = professorService.findAllByCode(professorsCode);

        if (CollectionUtils.isEmpty(professors)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Nenhum professor encontrado.");
        }

        final List<EnumGraduationSituation> situations = List.of(EnumGraduationSituation.CANCELED, EnumGraduationSituation.FINISHED);
        graduation.addProfessors(professors);

        repository.save(graduation);
        if (EnumGraduationSituation.CLOSE_SUBSCRIPTION.equals(graduation.getSituation().getSituation())) {
            createGraduationGrades(graduation);
        }
    }

    @Override
    public void removeProfessors(Long graduationCode, List<Long> professorsCode) {
        final Graduation graduation = findByCodeThrowsException(graduationCode);

        final List<Professor> professors = professorService.findAllByCode(professorsCode);

        if (CollectionUtils.isEmpty(professors)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Nenhum professor encontrado.");
        }

        final List<EnumGraduationSituation> situations = List.of(EnumGraduationSituation.CANCELED, EnumGraduationSituation.FINISHED);

        professors.forEach(graduation.getProfessors()::remove);

        repository.save(graduation);
        graduationGradeService.removeProfessors(graduation, professors);
    }

    @Override
    public void requestParticipation(long graduationCode, long athleteCode) {
        final Graduation graduation = findByCodeThrowsException(graduationCode);
        if (Boolean.FALSE.equals(EnumGraduationSituation.OPEN_SUBSCRIPTION.equals(graduation.getSituation().getSituation()))) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O exame de graduação não está com as inscrições abertas.");
        }
        final Athlete athlete = athleteService.findByCodeThrowsException(athleteCode);
        athleteGraduationService.save(graduation, athlete, EnumAthleteGraduationSituation.REGISTERED);
    }

    @Override
    public void removeAthletes(long graduationCode, List<Long> athletes) {
        final Graduation graduation = repository.findByCode(graduationCode).orElse(null);

        if (graduation == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Exame de graduação não encontrado.");
        }

        if (Boolean.FALSE.equals(EnumGraduationSituation.OPEN_SUBSCRIPTION.equals(graduation.getSituation().getSituation()))) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Não é possível remover atletas deste exame de graduação");
        }

        for (Long athlete : athletes) {
            final AthleteGraduation athleteGraduation = athleteGraduationService.findByGraduationAndAthleteCode(graduation, athlete);
            graduation.removeAthlete(athleteGraduation);
        }

        repository.save(graduation);
    }

    @Override
    public void setGraduationGrade(long graduationCode, long athleteCode, long professorCode, double grade, String description) {

        final Graduation graduation = findByCodeThrowsException(graduationCode);
        if (Boolean.FALSE.equals(EnumGraduationSituation.CLOSE_SUBSCRIPTION.equals(graduation.getSituation().getSituation()))) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O exame de graduação precisa estar com as inscrições encerradas para inserir notas.");
        }

        final Professor professor = graduation.getProfessors().stream() //
                .filter(prof -> prof.getPerson().getCode() == professorCode) //
                .findFirst() //
                .orElse(null);

        if (professor == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("Professor %s não vinculado ao exame de graduação", professorCode));
        }

        final AthleteGraduation athleteGraduation = graduation.getAthleteGraduations().stream()//
                .filter(ag -> ag.getAthlete().getPerson().getCode() == athleteCode)
                .findFirst() //
                .orElse(null);

        if (athleteGraduation == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("Atleta %s não vinculado ao exame de graduação", athleteCode));
        }

        graduationGradeService.save(graduation, athleteGraduation.getAthlete(), professor, grade, description);
    }

    private void finishGraduation(Graduation graduation) {
        final List<Athlete> athletes = graduation.getAthleteGraduations().stream().map(AthleteGraduation::getAthlete).collect(Collectors.toList());
        final List<Professor> professors = new ArrayList<>(graduation.getProfessors());

        athletes.forEach(athlete -> {
            final List<GraduationGrade> athleteGrades = graduationGradeService.findByGraduationAndAthlete(graduation, athlete);

            if (athleteGrades.size() != professors.size()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Preencha todas as notas antes de finalizar o exame de graduação.");
            }

            final double finalGrade = athleteGrades.stream().mapToDouble(GraduationGrade::getGrade).sum() / athleteGrades.size();
            athleteGraduationService.setAthleteGrade(graduation, athlete, finalGrade);
        });
    }

    private void createGraduationGrades(Graduation graduation) {
        final List<Athlete> athletes = graduation.getAthleteGraduations().stream().map(AthleteGraduation::getAthlete).collect(Collectors.toList());
        final Set<Professor> professors = graduation.getProfessors();
        for (Athlete athlete : athletes) {
            for (Professor professor : professors) {
                graduationGradeService.save(graduation, athlete, professor, 0.0, null);
            }
        }
    }

    private Long getNextCode() {
        final Graduation graduation = repository.findFirstByOrderByCodeDesc();
        if (graduation == null) {
            return 1L;
        }
        return graduation.getCode() + 1;
    }
}
