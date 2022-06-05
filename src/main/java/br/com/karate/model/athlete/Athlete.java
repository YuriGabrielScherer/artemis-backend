package br.com.karate.model.athlete;

import br.com.karate.enums.EnumBelt;
import br.com.karate.model.audit.EntityAudit;
import br.com.karate.model.belt.Belt;
import br.com.karate.model.graduation.Graduation;
import br.com.karate.model.graduation.athlete.AthleteGraduation;
import br.com.karate.model.graduation.grades.GraduationGrade;
import br.com.karate.model.person.Person;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.JoinFormula;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "athletes")
public class Athlete extends EntityAudit<UUID> {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @NotNull
    private LocalDate since;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_person")
    private Person person;

    @OneToMany(mappedBy = "athlete", cascade = CascadeType.ALL)
    private Set<AthleteGraduation> graduations = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinFormula(" (select b.id from athlete_graduation ag join belts b on b.id = ag.id_belt where ag.id_athlete = id and ag.situation = 'APPROVED' order by ag.created_date desc limit 1) ")
    private Belt currentBelt;

    public void addGraduation(AthleteGraduation graduation) {
        graduations.add(graduation);
        graduation.setAthlete(this);
    }

    public void removeGraduation(AthleteGraduation graduation) {
        graduations.remove(graduation);
        graduation.setAthlete(null);
    }
}
