package br.com.karate.model.graduation;

import br.com.karate.enums.EnumGraduationSituation;
import br.com.karate.model.athlete.AthleteInput;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

public class GraduationInput {

    public static class Save {
        @NotNull(message = "Código é obrigatório")
        @Min(value = 1, message = "Código inválido")
        public long code;

        @NotNull(message = "Título do Evento é obrigatório")
        public String title;

        public String description;

        public String place;

        @NotNull(message = "Data do Evento é obrigatório")
        public LocalDate date;

    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Setter
    @Getter
    public static class Filter {
        public long code;
        public String title;
        public String description;
        public String place;
        public LocalDate date;

        public List<Long> athletesCode;
    }

    public static class UpdateStatus {
        @NotNull(message = "Código é do exame de graduação é obrigatório")
        @Min(value = 1, message = "Código do exame de graduação inválido")
        public long code;
        @NotNull(message = "Situação do exame de graduação é obrigatória.")
        public EnumGraduationSituation situation;
    }

    public static class RegisterProfessors {
        @NotNull(message = "Código do exame de graduação é obrigatório.")
        @NotEmpty(message = "Insira ao menos um exame de graduação.")
        public List<Long> graduationsCode;

        @NotNull(message = "Código dos professores é obrigatório.")
        @NotEmpty(message = "Insira ao menos um professor.")
        public List<Long> professorsCode;
    }

    public static class RequestParticipation {
        @NotNull(message = "Código do exame de graduação é obrigatório")
        @Min(value = 1, message = "Código do exame de graduação inválido.")
        public Long graduationCode;

        @NotNull(message = "Código do atleta é obrigatório")
        @NotEmpty(message = "Insira ao menos um código de atleta")
        public List<Long> athletes;
    }

    public static class SetGraduationGradesInput {
        @NotNull(message = "Código do exame de graduação é obrigatório")
        @Min(value = 1, message = "Código do exame de graduação inválido.")
        public Long graduationCode;

        @NotNull(message = "As notas são obrigatórias")
        @NotEmpty(message = "Insira a nota de ao menos um atleta")
        public List<AthleteProfessorGrade> grades;
    }

    public static class AthleteProfessorGrade {
        @NotNull(message = "Código do atleta é obrigatório")
        @Min(value = 1, message = "Código do atleta inválido.")
        public Long athleteCode;

        @NotNull(message = "Código do professor é obrigatório")
        @Min(value = 1, message = "Código do professor inválido.")
        public Long professorCode;

        @NotNull(message = "Nota é obrigatória")
        @Min(value = 0, message = "Nota inválida.")
        public Double grade;

        public String description;

    }

}
