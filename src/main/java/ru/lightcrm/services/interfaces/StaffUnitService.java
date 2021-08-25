package ru.lightcrm.services.interfaces;

import java.util.List;
import ru.lightcrm.entities.StaffUnit;
import ru.lightcrm.entities.dtos.ProjectDto;
import ru.lightcrm.entities.dtos.StaffUnitDto;

public interface StaffUnitService {
    StaffUnit findByName(String name);

    StaffUnitDto findDtoByName(String name);

    List<String> getNames();

    StaffUnitDto saveOrUpdate(StaffUnitDto staffUnitDto);

    void deleteById(Long id);
}
