package br.com.karate.repository.person;

import br.com.karate.model.person.Person;
import br.com.karate.repository.AbstractRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PersonRepository extends AbstractRepository<Person> {
}
