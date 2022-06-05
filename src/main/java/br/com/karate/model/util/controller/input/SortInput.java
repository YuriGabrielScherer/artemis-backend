package br.com.karate.model.util.controller.input;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Sort;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SortInput {
    public String property = "id";
    public Sort.Direction direction = Sort.Direction.DESC;
}
