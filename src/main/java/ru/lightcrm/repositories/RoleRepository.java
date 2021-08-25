package ru.lightcrm.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.lightcrm.entities.Role;

import javax.validation.constraints.NotNull;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findOneByName(@NotNull String name);
}
