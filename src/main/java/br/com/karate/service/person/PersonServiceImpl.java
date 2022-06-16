package br.com.karate.service.person;

import br.com.karate.model.association.Association;
import br.com.karate.model.person.Person;
import br.com.karate.model.person.PersonInput;
import br.com.karate.model.util.pageable.PageableDto;
import br.com.karate.repository.person.PersonCustomRepository;
import br.com.karate.repository.person.PersonRepository;
import br.com.karate.service.association.AssociationService;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class PersonServiceImpl implements PersonService {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private PersonCustomRepository customRepository;

    @Autowired
    private AssociationService associationService;

    @Override
    public Person save(Person input) {

        Person person = personRepository.findByCode(input.getCode()).orElse(null);
        if (person == null) {
            person = new Person();
            person.setCode(getNextCode());

        }
        person.setName(input.getName());
        person.setBirth(input.getBirth());
        person.setGender(input.getGender());
        person.setDocument(input.getDocument());

        if (input.getAssociation() == null || input.getAssociation().getCode() <= 0) {
            person.setAssociation(null);
        } else {
            final Association association = associationService.findByCodeThrowsException(input.getAssociation().getCode());
            person.setAssociation(association);
        }

        try {
            return personRepository.save(person);
        } catch (DataIntegrityViolationException ex) {
            if (ex.getCause() instanceof ConstraintViolationException) {
                final String constraintName = ((ConstraintViolationException) ex.getCause()).getConstraintName();
                if ("uk_people_code".equalsIgnoreCase(constraintName)) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Código da pessoa já cadastrado.");
                }

                if ("uk_people_document".equalsIgnoreCase(constraintName)) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Documento da pessoa já cadastrado.");
                }
            }


            throw ex;
        }
    }

    @Override
    public Person findByCode(long code) {
        return personRepository.findByCode(code).orElse(null);
    }

    @Override
    public Person findByCodeThrowsException(long code) {
        final Person person = personRepository.findByCode(code).orElse(null);
        if (person == null) {
            throw new NoSuchElementException("Pessoa não encontrada.");
        }
        return person;
    }

    @Override
    public List<Person> findAll() {
        return personRepository.findAll();
    }

    @Override
    public Page<Person> list(PageableDto input, PersonInput.Filter filter) {
        return customRepository.list(filter, input.getPageable());
    }

    @Override
    public boolean delete(long code) {
        final Person person = personRepository.findByCode(code).orElse(null);
        if (person == null) {
            return true;
        }

        personRepository.delete(person);
        return true;
    }

    private Long getNextCode() {
        final Person person = personRepository.findFirstByOrderByCodeDesc();
        if (person == null) {
            return 1L;
        }
        return person.getCode() + 1;
    }
}
