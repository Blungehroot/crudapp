package com.app.crudapp.repository;

import com.app.crudapp.model.Media;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MediaRepository extends JpaRepository<Media, Integer> {
}
