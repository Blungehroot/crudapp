package com.app.crudapp.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "media")
@Data
@RequiredArgsConstructor
public class Media implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String fileName;

    @Column
    private String fileLink;

    @OneToOne(mappedBy = "media", cascade = CascadeType.ALL)
    @JoinColumn(name = "event_id")
    private Event event;
}
