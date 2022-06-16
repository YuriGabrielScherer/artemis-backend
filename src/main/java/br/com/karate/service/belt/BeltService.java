package br.com.karate.service.belt;

import br.com.karate.enums.EnumBelt;
import br.com.karate.model.belt.Belt;
import br.com.karate.model.util.pageable.PageableDto;
import org.springframework.data.domain.Page;

import java.time.LocalDate;

public interface BeltService {

    public Belt save(Belt belt);

    public Page<Belt> list(PageableDto pageableDto);
    public Belt findByBelt(EnumBelt belt);

    public Belt findNextBelt(EnumBelt currentBelt);

    public Belt validateCanGraduateAndReturnNextBelt(EnumBelt currentBelt, LocalDate formerAthleteGraduationDate, LocalDate graduationDate);

}
