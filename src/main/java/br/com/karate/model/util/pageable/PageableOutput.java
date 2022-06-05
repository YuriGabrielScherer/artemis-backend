package br.com.karate.model.util.pageable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class PageableOutput {
    private List<?> records;
    private long totalRecords;
}
