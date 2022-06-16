package br.com.karate.repository.person;

import br.com.karate.model.person.Person;
import br.com.karate.model.person.PersonInput;
import br.com.karate.model.person.QPerson;
import br.com.karate.repository.AbstractRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PersonCustomRepository {

    public Page<Person> list(PersonInput.Filter filter, Pageable dto);

}
