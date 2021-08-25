package ru.lightcrm.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.Set;

@Entity
@Table(name = "tasks")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "producer_id")
    private Profile producer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "responsible_id")
    private Profile responsible;

    @CreationTimestamp
    @Column(name = "start_date")
    private OffsetDateTime startDate;

    @Column(name = "end_date")
    private OffsetDateTime endDate;

    @Column(name = "deadline")
    private OffsetDateTime deadline;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_state_id")
    private TaskState taskState;

    @Column(name = "allow_change_deadline")
    private boolean allowChangeDeadline;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    private Project project;

    @Column(name = "expired")
    private boolean expired;

    @ManyToMany
    @JoinTable(name = "tasks_coexecutors",
                joinColumns = @JoinColumn(name = "task_id"),
                inverseJoinColumns = @JoinColumn(name = "profile_id"))
    private Set<Profile> coExecutors;

    @ManyToMany
    @JoinTable(name = "tasks_spectators",
                joinColumns = @JoinColumn(name = "task_id"),
                inverseJoinColumns = @JoinColumn(name = "profile_id"))
    private Set<Profile> spectators;

    @OneToMany
    @JoinTable(name = "tasks_comments",
                joinColumns = @JoinColumn(name = "task_id"),
                inverseJoinColumns = @JoinColumn(name = "comment_id"))
    private Set<Comment> comments;

    @OneToOne
    @JoinColumn(name = "company_id")
    private Company company;

    public Task(Long id, String title, String description, Profile producer, Profile responsible, TaskState taskState, Project project) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.producer = producer;
        this.responsible = responsible;
        this.taskState = taskState;
        this.project = project;
    }
}
