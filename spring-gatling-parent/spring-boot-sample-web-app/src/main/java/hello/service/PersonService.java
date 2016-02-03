package hello.service;

import hello.domain.Person;
import hello.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    public Object findAll(){
        return personRepository.findAll();
    }

    public Person findById(String id){
        return personRepository.findOne(id);
    }

    public Person save(Person person){
        return personRepository.save(person);
    }

}
