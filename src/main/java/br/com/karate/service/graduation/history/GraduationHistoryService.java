package br.com.karate.service.graduation.history;

import br.com.karate.model.graduation.Graduation;
import br.com.karate.model.graduation.history.GraduationHistory;

public interface GraduationHistoryService {

    public GraduationHistory newGraduation(Graduation graduation);

}
