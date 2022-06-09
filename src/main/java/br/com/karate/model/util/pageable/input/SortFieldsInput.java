package br.com.karate.model.util.pageable.input;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Sort;

@AllArgsConstructor
@Setter
@Getter
public class SortFieldsInput {
    public String property;
    public Sort.Direction direction;

    public SortFieldsInput() {
        property = "id";
        direction = Sort.Direction.DESC;
    }
}
