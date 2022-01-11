package com.app.crudapp.service;

import com.app.crudapp.model.Media;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface MediaService {

    Media getById(int id);

    List<Media> getAll();

    Media save(MultipartFile file);

    Media update(Media media);

    void deleteById(int id);
}
