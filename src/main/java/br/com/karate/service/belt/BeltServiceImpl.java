package br.com.karate.service.belt;

import br.com.karate.enums.EnumBelt;
import br.com.karate.model.belt.Belt;
import br.com.karate.model.util.pageable.PageableDto;
import br.com.karate.repository.belt.BeltCustomRepository;
import br.com.karate.repository.belt.BeltRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;

@Service
public class BeltServiceImpl implements BeltService {

    @Autowired
    private BeltRepository repository;

    @Autowired
    private BeltCustomRepository customRepository;

    @Override
    public Belt save(Belt input) {
        final Belt belt = findByBelt(input.getBelt());

        belt.setMinMonths(input.getMinMonths());

        return repository.save(belt);
    }

    @Override
    public Page<Belt> list(PageableDto pageableDto) {
        return repository.findAll(pageableDto.getPageable());
    }

    @Override
    public Belt findByBelt(EnumBelt belt) {
        return repository.findByBelt(belt);
    }

    @Override
    public Belt findNextBelt(EnumBelt currentBelt) {
        if (EnumBelt.BLACK_10.equals(currentBelt)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Não há próxima graduação.");
        }
        return customRepository.findNextBelt(currentBelt);
    }

    @Override
    public Belt validateCanGraduateAndReturnNextBelt(EnumBelt currentBeltInput, LocalDate formerAthleteGraduationDate, LocalDate graduationDate) {
        final Belt nextBelt = findNextBelt(currentBeltInput);

        final LocalDate baseDate = graduationDate.minusMonths(nextBelt.getMinMonths());

        if (baseDate.isBefore(formerAthleteGraduationDate)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Atleta não possui carência para realizar o exame.");
        }

        return nextBelt;
    }
}
