package br.com.karate.service.belt;

import br.com.karate.enums.EnumBelt;
import br.com.karate.model.belt.Belt;

import java.time.LocalDate;

public interface BeltService {
    public Belt findByBelt(EnumBelt belt);

    public Belt findNextBelt(EnumBelt currentBelt);

    public Belt validateCanGraduateAndReturnNextBelt(EnumBelt currentBelt, LocalDate formerAthleteGraduation, LocalDate graduationDate);

}
