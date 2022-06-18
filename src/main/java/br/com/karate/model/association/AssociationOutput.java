package br.com.karate.model.association;

import br.com.karate.model.person.PersonOutput;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

public class AssociationOutput {

    @EqualsAndHashCode
    public static class Dto {
        public long code;
        public String name;
        public String city;
        public LocalDate since;
        public PersonOutput.Dto manager;
    }

    public static class TopAssociations {
        public String name;
        public long count;
    }

}
