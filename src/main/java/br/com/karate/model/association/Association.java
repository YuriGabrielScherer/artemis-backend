package br.com.karate.model.association;

import br.com.karate.model.audit.EntityAudit;
import br.com.karate.model.person.Person;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "associations")
public class Association extends EntityAudit<UUID> {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;
    @NotNull
    private long code;
    @NotNull
    private String name;
    private String city;
    private LocalDate since;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_manager")
    private Person manager;

    @OneToMany(mappedBy = "association")
    private Set<Person> people;

}
