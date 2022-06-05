package br.com.karate.model.graduation;

import br.com.karate.model.audit.EntityAudit;
import br.com.karate.model.graduation.athlete.AthleteGraduation;
import br.com.karate.model.graduation.history.GraduationHistory;
import br.com.karate.model.professor.Professor;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.JoinFormula;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "graduations")
public class Graduation extends EntityAudit<UUID> {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @NotNull
    private String title;

    private String description;

    private String place;

    @NotNull
    private long code;

    @NotNull
    private LocalDate date;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "graduation_professors",
            joinColumns = {@JoinColumn(name =  "id_graduation")},
            inverseJoinColumns = {@JoinColumn(name = "id_professor")}
    )
    private Set<Professor> professors;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "graduation", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<AthleteGraduation> athleteGraduations;

    @OneToMany(mappedBy = "graduation", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<GraduationHistory> history = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinFormula("(select gh.id from graduation_history gh where gh.id_graduation = id order by gh.created_date desc limit 1)")
    private GraduationHistory situation;

    public void addHistory(GraduationHistory history) {
        this.history.add(history);
        history.setGraduation(this);
    }

    public void removeHistory(GraduationHistory history) {
        this.history.remove(history);
        history.setGraduation(null);
    }

    public void removeAthlete(AthleteGraduation athleteGraduation) {
        this.athleteGraduations.remove(athleteGraduation);
    }

    public void addProfessors(List<Professor> professors) {
        this.professors.addAll(professors);
    }
}
