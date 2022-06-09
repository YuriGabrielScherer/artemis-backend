package br.com.karate.model.util.pageable;


import br.com.karate.model.util.pageable.input.SortFieldsInput;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class PageableDto {
    private int page;
    private int size;
    private List<SortFieldsInput> sortFields = new ArrayList<>();

    public PageableDto() {
        page = 0;
        size = 10;
        sortFields.add(new SortFieldsInput());
    }

    public PageableDto(int page, int size) {
        this.page = page;
        this.size = size;
        sortFields.add(new SortFieldsInput());
    }

    public Pageable getPageable() {
        final List<Sort.Order> orders = new ArrayList<>();
        sortFields.forEach(s -> {
            orders.add(new Sort.Order(s.direction, s.property));
        });
        final Sort sort = Sort.by(orders);
        return PageRequest.of(page, size, sort);
    }
}
