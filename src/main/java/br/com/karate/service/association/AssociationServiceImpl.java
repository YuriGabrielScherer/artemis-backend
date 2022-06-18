package br.com.karate.service.association;

import br.com.karate.model.association.Association;
import br.com.karate.model.association.AssociationInput;
import br.com.karate.model.person.Person;
import br.com.karate.model.util.pageable.PageableDto;
import br.com.karate.repository.association.AssociationCustomRepository;
import br.com.karate.repository.association.AssociationRepository;
import br.com.karate.repository.person.PersonRepository;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

@Service
public class AssociationServiceImpl implements AssociationService {
    @Autowired
    private AssociationRepository repository;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private AssociationCustomRepository customRepository;

    @Override
    public Association save(Association input) {

        if (input.getSince() != null && input.getSince().isAfter(LocalDate.now())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Data de criação da Associação precisa ser antes de hoje.");
        }
        final Person manager = personRepository.findByCode(input.getManager().getCode()).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Responsável pela associação não encontrado."));

        Association association = repository.findByCode(input.getCode()).orElse(null);

        if (association == null) {
            association = new Association();
            association.setCode(getNextCode());
        }

        association.setName(input.getName());
        association.setCity(input.getCity());
        association.setSince(input.getSince());
        association.setManager(manager);

        try {
            return repository.save(association);
        } catch (ConstraintViolationException ex) {
            if ("uk_association_code".equals(ex.getConstraintName())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Código de associação já cadastrado.");
            }

            throw ex;
        }
    }

    @Override
    public List<Association> findAll() {
        return repository.findAll();
    }

    @Override
    public Association findByCode(long code) {
        return repository.findByCode(code).orElse(null);
    }

    @Override
    public Association findByCodeThrowsException(long code) {
        final Association association = repository.findByCode(code).orElse(null);
        if (association == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Associação não encontrada.");
        }
        return association;
    }

    @Override
    public Page<Association> list(PageableDto input, AssociationInput.Filter filter) {
        return repository.findAll(input.getPageable());
    }

    @Override
    public boolean delete(long code) {
        final Association association = repository.findByCode(code).orElse(null);
        if (association == null) {
            return true;
        }

        repository.delete(association);
        return true;
    }

    private Long getNextCode() {
        final Association association = repository.findFirstByOrderByCodeDesc();
        if (association == null) {
            return 1L;
        }
        return association.getCode() + 1;
    }


    @Override
    public List<Association> listTopAssociationsByAthletes() {
        return customRepository.topAssociationsByAthletesNumber();
    }
}
