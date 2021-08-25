package ru.lightcrm.controllers;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import ru.lightcrm.controllers.interfaces.DepartmentController;
import ru.lightcrm.services.interfaces.DepartmentService;

@RestController
@RequiredArgsConstructor
public class DepartmentControllerImpl implements DepartmentController {

  private final DepartmentService departmentService;

  @Override
  public List<String> getAllDepartmentNames() {
    return departmentService.getAllDepartmentNames();
  }
}

