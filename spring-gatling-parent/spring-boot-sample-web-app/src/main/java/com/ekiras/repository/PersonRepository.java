package com.ekiras.repository;

import com.ekiras.domain.Person;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Repository
public class PersonRepository {

    Map<String, Person> personMap = new HashMap<>();

    public Person save(Person person) {
        if (person.getId() == null) {
            person.setId(UUID.randomUUID().toString());
        }
        return personMap.put(person.getId(), person);
    }

    public Person findOne(String id) {
        return personMap.get(id);
    }

    public Collection<Person> findAll() {
        return personMap.values();
    }
}
