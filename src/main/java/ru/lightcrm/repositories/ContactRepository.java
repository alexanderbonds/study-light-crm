package ru.lightcrm.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.lightcrm.entities.Contact;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {
}
