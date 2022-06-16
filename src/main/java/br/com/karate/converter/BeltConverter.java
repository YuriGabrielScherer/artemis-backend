package br.com.karate.converter;

import br.com.karate.converter.abstracts.SimpleConverter;
import br.com.karate.model.belt.Belt;
import br.com.karate.model.belt.BeltInput;
import br.com.karate.model.belt.BeltOutput;
import org.springframework.stereotype.Component;

@Component
public class BeltConverter implements SimpleConverter<Belt, BeltOutput.Dto> {

    @Override
    public BeltOutput.Dto toDto(Belt belt) {
        final BeltOutput.Dto dto = new BeltOutput.Dto();
        dto.belt = belt.getBelt();
        dto.minMonths = belt.getMinMonths();
        dto.sequence = belt.getSequence();
        return dto;
    }

    public Belt toEntity(BeltInput.Save input) {
        final Belt belt = new Belt();

        belt.setBelt(input.belt);
        belt.setMinMonths(input.minMonths);

        return belt;
    }

}
