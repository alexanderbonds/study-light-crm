package ru.lightcrm.services.interfaces;

import ru.lightcrm.entities.Project;
import ru.lightcrm.entities.dtos.ProjectDto;

import java.util.List;
import java.util.Map;

public interface ProjectService {

    List<ProjectDto> findAll(Map<String, String> params);

    Project findEntityById(Long id);

    ProjectDto findById(Long id);

    ProjectDto findOneByName(String name);

    List<ProjectDto> findByManagerId(Long id);

    ProjectDto saveOrUpdate(ProjectDto projectDTO);

    void deleteById(Long id);
}
