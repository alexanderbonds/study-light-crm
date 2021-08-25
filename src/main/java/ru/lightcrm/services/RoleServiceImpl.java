package ru.lightcrm.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.lightcrm.entities.Role;
import ru.lightcrm.entities.dtos.RoleDto;
import ru.lightcrm.exceptions.ResourceNotFoundException;
import ru.lightcrm.repositories.RoleRepository;
import ru.lightcrm.services.interfaces.RoleService;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository rolesRepository;

    public Role findRoleByName(String name) {
        return rolesRepository.findOneByName(name).orElseThrow(() -> new ResourceNotFoundException(String.format("Роль '%s' не найдена", name)));
    }
    public RoleDto findByName(String name) {
        return new RoleDto(findRoleByName(name));
    }
}
