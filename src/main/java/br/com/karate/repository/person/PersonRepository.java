package br.com.karate.repository.person;

import br.com.karate.model.person.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;


@Repository
public interface PersonRepository extends JpaRepository<Person, UUID> {
    public Optional<Person> findByCode(long code);
    public Person findFirstByOrderByCodeDesc();
}
