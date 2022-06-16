package br.com.karate.model.person;

import br.com.karate.enums.EnumGender;
import br.com.karate.model.association.AssociationInput;
import br.com.karate.model.athlete.AthleteInput;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class PersonInput {

    public static class Save {
        public long code;
        @NotNull(message = "Nome da Pessoa é obrigatório")
        @NotBlank(message = "Nome da Pessoa é obrigatório")
        public String name;

        @NotNull(message = "Sexo é obrigatório.")
        public EnumGender gender;

        public String document;

        public LocalDate birth;

        public long associationCode;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Setter
    @Getter
    public static class Filter {
        public String name;
        public long code;
        public EnumGender gender;
        public String document;
        public LocalDate birth;
        public boolean notProfessor;
        public boolean notAthlete;
        public AssociationInput.Filter association;
        public AthleteInput.Filter athlete;
    }

}
