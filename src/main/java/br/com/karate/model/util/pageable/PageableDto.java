package br.com.karate.model.util.pageable;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Sort;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class PageableDto {
    private int page;
    private int size;
    private Sort.Direction direction;
    private List<String> properties;

    public PageableDto() {
        page = 0;
        size = 10;
        direction = Sort.Direction.ASC;
        properties = List.of("id"); // TODO Ajustar para ser createdDate
    }

    public PageableDto(int page, int size) {
        this.page = page;
        this.size = size;
        direction = Sort.Direction.ASC;
        properties = List.of("id");
    }
}
