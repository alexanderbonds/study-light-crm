package ru.lightcrm.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.lightcrm.entities.Company;
import ru.lightcrm.entities.Contact;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
public class ContactRepositoryTest {

    private final String CONTACT_NAME = "Васечкин Петр";
    private final String CONTACT_PHONE = "89457894512";
    private final long CONTACT_ID = 1L;

    @Autowired
    private ContactRepository contactRepository;

    @Test
    void findById() {
        Optional<Contact> contact = contactRepository.findById(CONTACT_ID);
        assertNotNull(contact.get());
        assertEquals(CONTACT_ID, (long) contact.get().getId());
        assertEquals(CONTACT_NAME, contact.get().getName());
        assertEquals(CONTACT_PHONE, contact.get().getPhone());
    }
}
