package br.com.karate.model.belt;

import br.com.karate.enums.EnumBelt;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class BeltInput {

    public static class Save {
        @NotNull(message = "Faixa é obrigatória.")
        public EnumBelt belt;
        @NotNull(message = "Quantidade mínima de meses é obrigatório.")
        @Min(value = 1, message = "O tempo mínimo em cada faixa é 01 mês.")
        public int minMonths;
    }
}
