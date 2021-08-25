package ru.lightcrm.repositories.specifications;

import org.springframework.data.jpa.domain.Specification;
import ru.lightcrm.entities.Project;

public class ProjectSpecification {
    public static Specification<Project> managerEquals(long manager_id){
        return (Specification<Project>)(root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("manager").get("id"), manager_id);
    }

    public static Specification<Project> companyEquals(long company_id){
        return (Specification<Project>)(root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("company").get("id"), company_id);
    }
}
