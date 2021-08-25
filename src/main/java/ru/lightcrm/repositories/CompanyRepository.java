package ru.lightcrm.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.lightcrm.entities.Company;
import ru.lightcrm.entities.Contact;

import javax.validation.constraints.NotNull;
import java.util.Optional;


public interface CompanyRepository extends JpaRepository<Company, Long> {
    Optional<Company> findOneByName(@NotNull String name);

    Optional<Company> findOneByInn(@NotNull Long inn);

    Optional<Company> findOneById(@NotNull Long id);
}
