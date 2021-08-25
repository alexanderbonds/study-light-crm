package ru.lightcrm.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "projects")
@Data
@NoArgsConstructor
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manager_id")
    private Profile manager;

    @OneToOne
    @JoinColumn(name = "company_id")
    private Company company;

    @ManyToMany
    @JoinTable(name = "employees_projects",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "profile_id"))
    private List<Profile> profiles;

    @OneToMany(mappedBy = "project")
    private List<Task> tasks;

    public Project(Long id, String name, String description, Profile manager) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.manager = manager;
    }
}
