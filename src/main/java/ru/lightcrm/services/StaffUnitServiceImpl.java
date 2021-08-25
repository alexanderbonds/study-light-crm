package ru.lightcrm.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.lightcrm.entities.Role;
import ru.lightcrm.entities.StaffUnit;
import ru.lightcrm.entities.dtos.StaffUnitDto;
import ru.lightcrm.exceptions.ResourceNotFoundException;
import ru.lightcrm.repositories.StaffUnitRepository;
import ru.lightcrm.services.interfaces.RoleService;
import ru.lightcrm.services.interfaces.StaffUnitService;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StaffUnitServiceImpl implements StaffUnitService {
    private final StaffUnitRepository staffUnitRepository;
    private final RoleService roleService;

    @Override
    public StaffUnit findByName(String name) {
        return staffUnitRepository.findOneByName(name).orElseThrow(() -> new ResourceNotFoundException(String.format("Должность с названием %s не найдена", name)));
    }

    @Override
    public StaffUnitDto findDtoByName(String name) {
        return new StaffUnitDto(findByName(name));
    }

    @Override
    public List<String> getNames() {
        return staffUnitRepository.findAll().stream().map(StaffUnit::getName).collect(Collectors.toList());
    }

    @Override
    public StaffUnitDto saveOrUpdate(StaffUnitDto staffUnitDto) {
        StaffUnit staffUnit;
        if (staffUnitDto.getId() == null) {
            staffUnit = new StaffUnit();

        } else {
            staffUnit = staffUnitRepository.findOneByName(staffUnitDto.getName())
                    .orElseThrow(() -> new ResourceNotFoundException(String.format("Должность %s не найдена", staffUnitDto.getName())));
        }
        staffUnit.setName(staffUnitDto.getName());

        Set<Role> roles = null;
        if (!staffUnitDto.getRoles().isEmpty()) {
         roles = staffUnitDto.getRoles().stream().map(roleDto -> roleService.findRoleByName(roleDto.getName())).collect(Collectors.toSet());
       }
        staffUnit.setRoles(roles);
        return new StaffUnitDto(staffUnitRepository.save(staffUnit));
    }

    @Override
    public void deleteById(Long id) {
        staffUnitRepository.deleteById(id);
    }
}
