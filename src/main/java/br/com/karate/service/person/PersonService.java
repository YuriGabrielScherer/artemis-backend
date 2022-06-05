package br.com.karate.service.person;

import br.com.karate.model.person.Person;
import br.com.karate.model.person.PersonInput;
import br.com.karate.service.abstracts.CrudAbstractService;

public interface PersonService extends CrudAbstractService<Person, PersonInput.Filter> {

}
