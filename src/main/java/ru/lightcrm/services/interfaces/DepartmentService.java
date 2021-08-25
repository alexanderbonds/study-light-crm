package ru.lightcrm.services.interfaces;

import ru.lightcrm.entities.Department;
import ru.lightcrm.entities.dtos.DepartmentDto;

import java.util.List;

public interface DepartmentService {
    List<DepartmentDto> findAll();

    DepartmentDto findDtoById(Long id);

    DepartmentDto findOneDtoByName(String name);

    Department findOneByName(String name);

    DepartmentDto findOneByLeaderId(Long id);

    List<String> getAllDepartmentNames();
}
