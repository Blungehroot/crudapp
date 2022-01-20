package com.app.crudapp.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "users")
@Data
@RequiredArgsConstructor
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String name;

    @Column
    private UserRoles role;

    @Column
    private String password;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Event> events;
}
