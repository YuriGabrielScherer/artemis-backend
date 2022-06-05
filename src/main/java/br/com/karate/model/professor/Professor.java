package br.com.karate.model.professor;

import br.com.karate.model.audit.EntityAudit;
import br.com.karate.model.graduation.Graduation;
import br.com.karate.model.person.Person;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
@Table(name = "professors")
public class Professor extends EntityAudit<UUID> {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @JoinColumn(name = "id_person")
    @OneToOne(fetch = FetchType.EAGER, optional = false)
    private Person person;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "professors")
    private Set<Graduation> graduations;

}
