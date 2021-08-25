package ru.lightcrm.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.lightcrm.entities.dtos.SystemUserDto;

import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.List;

@Entity
@Table(name = "profiles")
@Data
@NoArgsConstructor
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "firstname")
    private String firstname;

    @Column(name = "lastname")
    private String lastname;

    @Column(name = "middlename")
    private String middlename;

    @Column(name = "sex")
    private String sex;

    @Column(name = "phone")
    private String phone;

    @Column(name = "email")
    private String email;

    @Lob
    @Column(name = "about")
    private String about;

    @Column(name = "birthdate")
    private OffsetDateTime birthdate;

    @Column(name = "employment_date")
    private OffsetDateTime employmentDate;

    @Column(name = "dismissal_date")
    private OffsetDateTime dismissalDate;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne
    @JoinColumn(name = "staff_unit_id")
    private StaffUnit staffUnit;

    @OneToOne
    @JoinColumn(name = "photo_id")
    private FileInfo photo;

    @OneToOne
    @JoinColumn(name = "preview_id")
    private FileInfo preview;

    @ManyToMany
    @JoinTable(name = "companies_managers",
            joinColumns = @JoinColumn(name = "profile_id"),
            inverseJoinColumns = @JoinColumn(name = "company_id"))
    private List<Company> companies;

    @OneToMany(mappedBy = "leader")
    private List<Department> managedDepartments;

    @ManyToMany
    @JoinTable(name = "departments_profiles",
            joinColumns = @JoinColumn(name = "profile_id"),
            inverseJoinColumns = @JoinColumn(name = "department_id"))
    private List<Department> departments;

    @OneToMany(mappedBy = "author")
    private List<Comment> comments;

    @OneToMany(mappedBy = "manager")
    private List<Project> managedProjects;

    @ManyToMany
    @JoinTable(name = "employees_projects",
            joinColumns = @JoinColumn(name = "profile_id"),
            inverseJoinColumns = @JoinColumn(name = "project_id"))
    private List<Project> projects;

    @ManyToMany
    @JoinTable(name = "tasks_coexecutors",
            joinColumns = @JoinColumn(name = "profile_id"),
            inverseJoinColumns = @JoinColumn(name = "task_id"))
    private List<Task> tasks;

    @ManyToMany
    @JoinTable(name = "tasks_spectators",
            joinColumns = @JoinColumn(name = "profile_id"),
            inverseJoinColumns = @JoinColumn(name = "task_id"))
    private List<Task> observedTasks;

    public static Profile createNewProfileForUserRegistration(SystemUserDto systemUserDto, User user, StaffUnit staffUnit, List<Department> departments) {
        Profile profile = new Profile();
        profile.setUser(user);
        profile.setStaffUnit(staffUnit);
        profile.setDepartments(departments);
        profile.setFirstname(systemUserDto.getFirstname());
        profile.setLastname(systemUserDto.getLastname());
        profile.setMiddlename(systemUserDto.getMiddlename());
        profile.setEmploymentDate(systemUserDto.getEmploymentDate() != null
                ? systemUserDto.getEmploymentDate()
                : OffsetDateTime.now());
        return profile;
    }
}
