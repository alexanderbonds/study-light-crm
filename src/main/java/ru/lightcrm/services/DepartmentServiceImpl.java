package ru.lightcrm.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.lightcrm.entities.Department;
import ru.lightcrm.entities.dtos.DepartmentDto;
import ru.lightcrm.exceptions.ResourceNotFoundException;
import ru.lightcrm.repositories.DepartmentRepository;
import ru.lightcrm.services.interfaces.DepartmentService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {
    private final DepartmentRepository departmentRepository;

    @Override
    public List<DepartmentDto> findAll() {
        return departmentRepository.findAll().stream().map(DepartmentDto::new).collect(Collectors.toList());
    }
    @Override
    public List<String> getAllDepartmentNames() {
        return departmentRepository.findAll().stream().map(Department::getName).collect(Collectors.toList());
    }

    @Override
    public DepartmentDto findDtoById(Long id) {
        return new DepartmentDto(departmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Отдел с id = %s не найден", id))));
    }

    @Override
    public Department findOneByName(String name) {
        return departmentRepository.findOneByName(name)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Отдел с наименованием = \"%s\" не найден", name)));
    }

    @Override
    public DepartmentDto findOneDtoByName(String name) {
        return new DepartmentDto(findOneByName(name));
    }

    @Override
    public DepartmentDto findOneByLeaderId(Long id) {
        return new DepartmentDto(departmentRepository.findOneByLeaderId(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Отдел руководителя (id = %d) не найден", id))));
    }
}
