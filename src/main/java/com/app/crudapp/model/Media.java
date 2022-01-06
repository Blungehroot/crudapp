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

    @Column(name = "filename")
    private String fileName;

    @Column(name = "filelink")
    private String fileLink;

    @OneToOne(mappedBy = "media")
    private Event event;
}
