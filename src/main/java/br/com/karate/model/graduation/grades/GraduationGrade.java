package br.com.karate.model.graduation.grades;

import br.com.karate.model.athlete.Athlete;
import br.com.karate.model.audit.EntityAudit;
import br.com.karate.model.graduation.Graduation;
import br.com.karate.model.professor.Professor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "graduation_grades")
public class GraduationGrade extends EntityAudit<UUID> {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_professor")
    private Professor professor;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_graduation")
    private Graduation graduation;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_athlete")
    private Athlete athlete;

    @NotNull
    @Min(value=0)
    private Double grade;

    private String description;

}
