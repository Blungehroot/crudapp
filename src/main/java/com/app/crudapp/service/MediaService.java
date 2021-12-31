package com.app.crudapp.service;

import com.app.crudapp.model.Media;

import java.util.List;

public interface MediaService {
    Media getById(int id);

    List<Media> getAll();

    Media save(Media media);

    Media update(Media media);

    void deleteById(int id);
}
