package br.com.karate.service.graduation.history;

import br.com.karate.enums.EnumGraduationSituation;
import br.com.karate.model.graduation.Graduation;
import br.com.karate.model.graduation.history.GraduationHistory;
import br.com.karate.repository.graduation.GraduationHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class GraduationHistoryServiceImpl implements GraduationHistoryService {

    @Autowired
    private GraduationHistoryRepository repository;

    @Override
    public GraduationHistory newGraduation(Graduation graduation) {
        final GraduationHistory history = new GraduationHistory();

        history.setSituation(EnumGraduationSituation.CREATED);
        history.setGraduation(graduation);
        return repository.save(history);
    }
}
