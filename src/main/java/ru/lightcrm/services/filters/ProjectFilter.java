package ru.lightcrm.services.filters;

import org.springframework.data.jpa.domain.Specification;
import ru.lightcrm.entities.Project;
import ru.lightcrm.repositories.specifications.ProjectSpecification;

import java.util.Map;

public class ProjectFilter {
    private Specification<Project> specification;

    public ProjectFilter(Map<String, String> params){
        specification = Specification.where(null);

        if(params == null) return;

        if (params.containsKey("managerId") && !params.get("managerId").isBlank())
            specification = specification.and(ProjectSpecification.managerEquals(Long.parseLong(params.get("managerId"))));

        if (params.containsKey("companyId") && !params.get("companyId").isBlank())
            specification = specification.and(ProjectSpecification.companyEquals(Long.parseLong(params.get("companyId"))));
    }

    public Specification<Project> getSpecification() {
        return specification;
    }

    public void setSpecification(Specification<Project> specification) {
        this.specification = specification;
    }
}
