package br.com.karate.model.person;

import br.com.karate.enums.EnumGender;
import br.com.karate.model.association.AssociationOutput;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

public class PersonOutput {
    @EqualsAndHashCode
    public static class Dto {
        public long code;
        public String name;
        public LocalDate birth;
        public EnumGender gender;
        public String document;
        public AssociationOutput.Dto association;
    }
}
