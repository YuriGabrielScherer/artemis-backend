package br.com.karate.model.util.controller.input;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class PageableInput {
    public int size;
    public int page;
    public List<SortInput> sort = new ArrayList<>();

    public PageableInput() {
        sort.add(new SortInput());
    }
}
