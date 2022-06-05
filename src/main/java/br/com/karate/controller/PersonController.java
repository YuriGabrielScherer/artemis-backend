package br.com.karate.controller;

import br.com.karate.controller.abstracts.CrudAbstractController;
import br.com.karate.model.person.Person;
import br.com.karate.model.person.PersonInput;
import br.com.karate.model.person.PersonOutput;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/person")
public class PersonController extends CrudAbstractController<Person, PersonInput.Save, PersonOutput.Dto, PersonInput.Filter> {
}
