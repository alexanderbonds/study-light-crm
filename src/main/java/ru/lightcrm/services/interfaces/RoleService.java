package ru.lightcrm.services.interfaces;

import ru.lightcrm.entities.Role;
import ru.lightcrm.entities.dtos.RoleDto;

public interface RoleService {
    RoleDto findByName(String name);

   Role findRoleByName(String name);
}
