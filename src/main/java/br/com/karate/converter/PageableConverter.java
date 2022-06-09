package br.com.karate.converter;

import br.com.karate.model.util.pageable.PageableDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

@Component
public class PageableConverter {
    public PageableDto toDto(String jsonInput) throws JsonProcessingException {
        if (jsonInput == null || jsonInput.length() == 0) {
            return new PageableDto();
        }

        final PageableDto dto = new ObjectMapper().readValue(jsonInput, PageableDto.class);
        return dto;
    }
}
