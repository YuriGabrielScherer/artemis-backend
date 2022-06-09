package br.com.karate.model.association;


import br.com.karate.model.person.PersonInput;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class AssociationInput {

    public static class Save {
        @NotNull(message = "Código da Associação é obrigatório.")
        @Min(value = 1, message = "Código inválido.")
        public long code;

        @NotNull(message = "Nome da Associação é obrigatório.")
        @NotBlank(message = "Nome da Associação é obrigatório.")
        public String name;
        @NotNull(message = "Cidade da Associação é obrigatório.")
        @NotBlank(message = "Cidade da Associação é obrigatório.")
        public String city;
        public LocalDate since;

        @NotNull
        @Min(value = 1, message = "Código de Responsável inválido.")
        public long managerCode;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Setter
    @Getter
    public static class Filter {
        public String name;
        public String city;
        public long code;
        public LocalDate since;
        public PersonInput.Filter manager;
    }

}
