package com.app.crudapp.service.impl;

import com.amazonaws.services.s3.model.ObjectMetadata;
import com.app.crudapp.aws.AwsClient;
import com.app.crudapp.model.Media;
import com.app.crudapp.model.User;
import com.app.crudapp.repository.MediaRepository;
import com.app.crudapp.service.MediaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class MediaServiceImpl implements MediaService {
    @Value("${bucket.name}")
    private String bucketName;
    private AwsClient s3 = new AwsClient();

    @Autowired
    private final MediaRepository mediaRepository;

    @Override
    public Media getById(int id) {
        log.debug("Get media by id: {}", id);
        return mediaRepository.findById(id).get();
    }

    @Override
    public List<Media> getAll() {
        log.debug("Get media list");
        return mediaRepository.findAll();
    }

    @Override
    public Media save(MultipartFile file, User user) {
        ObjectMetadata omd = new ObjectMetadata();
        omd.setContentType(file.getContentType());
        omd.setContentLength(file.getSize());
        Media media = new Media();
        try {
            log.info("==================================================");
            log.info("==================================================");
            log.info(String.valueOf(s3.getS3Connection().doesBucketExistV2(bucketName)));
            log.info("==================================================");
            log.info("==================================================");
            String data;
            data = s3.getS3Connection().putObject(bucketName, file.getOriginalFilename(), file.getInputStream(), omd).getETag();
            media.setName(file.getOriginalFilename());
            media.setUrl(data);
            media.setUser(user);
            media = mediaRepository.save(media);
            log.info("The new media was created, id: {}", media.getId());
        } catch (IOException e) {
            e.getMessage();
        }
        return media;
    }

    @Override
    public Media update(Media media) {
        log.info("Starting update the media with id: {}", media.getId());
        return mediaRepository.save(media);
    }

    @Override
    public void deleteById(int id) {
        log.debug("Delete media by id: {}", id);
        Media media = getById(id);
        mediaRepository.delete(media);
    }

    @Override
    public List<Media> getAllMediaByUserId(Integer id) {
        return mediaRepository.getAllByUserId(id);
    }
}
