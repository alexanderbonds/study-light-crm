package ru.lightcrm.entities;

import java.util.Set;
import javax.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
// @Data заменил на эти две аннотации - иначе зацикливание
@Getter
@Setter
@NoArgsConstructor
@Table(name = "priorities")
public class Priority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "visible_name")
    private String visibleName;

    @ManyToMany(mappedBy = "priorities")
    private Set<Role> roles;
}
