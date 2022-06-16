package br.com.karate.service.graduation;

import br.com.karate.enums.EnumGraduationSituation;
import br.com.karate.model.graduation.Graduation;
import br.com.karate.model.graduation.GraduationInput;
import br.com.karate.service.abstracts.CrudAbstractService;

import java.util.List;

public interface GraduationService extends CrudAbstractService<Graduation, GraduationInput.Filter> {

    public void updateStatus(Long code, EnumGraduationSituation newSituation);

    public void registerProfessors(Long graduationCode, List<Long> professorsCode);

    public void removeProfessors(Long graduationCode, List<Long> professorsCode);

    public void requestParticipation(long graduationCode, long athleteCode);

    public void removeAthletes(long graduationCode, List<Long> athletes);

    public void setGraduationGrade(long graduationCode, long athleteCode, long professorCode, double grade, String description);

}
