package com.app.crudapp.service;

import com.app.crudapp.model.Media;
import com.app.crudapp.model.User;
import org.springframework.http.HttpHeaders;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface MediaService {

    Media getById(int id);

    List<Media> getAll();

    Media save(MultipartFile file, User user);

    Media update(Media media);

    void deleteById(int id);
}
