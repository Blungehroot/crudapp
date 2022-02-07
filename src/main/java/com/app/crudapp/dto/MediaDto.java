package com.app.crudapp.dto;

import com.app.crudapp.model.Media;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class MediaDto {
    private Integer id;
    private String name;
    private String url;

    public static MediaDto fromMedia(Media media) {
        MediaDto mediaDto = new MediaDto();

        mediaDto.setId(media.getId());
        mediaDto.setName(media.getName());
        mediaDto.setUrl(media.getUrl());

        return mediaDto;
    }
}
