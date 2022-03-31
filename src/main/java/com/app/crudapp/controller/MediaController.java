package com.app.crudapp.controller;


import com.app.crudapp.dto.MediaDto;
import com.app.crudapp.dto.UserDto;
import com.app.crudapp.model.Event;
import com.app.crudapp.model.Media;
import com.app.crudapp.model.User;
import com.app.crudapp.service.EventService;
import com.app.crudapp.service.MediaService;
import com.app.crudapp.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import static com.app.crudapp.model.EventActions.CREATE;
import static com.app.crudapp.model.EventActions.DELETE;

@RestController
@RequestMapping(value = "/api/v1/media")
@Slf4j
public class MediaController {
    private final MediaService mediaService;
    private final UserService userService;
    private final EventService eventService;

    @Autowired
    public MediaController(MediaService mediaService, UserService userService, EventService eventService) {
        this.mediaService = mediaService;
        this.userService = userService;
        this.eventService = eventService;
    }

    private User getUserFromToken(HttpHeaders httpHeaders) throws JsonProcessingException {
        String token = httpHeaders.getFirst("Authorization");
        String[] chunks = token.split("\\.");
        String payload = new String(Base64.getUrlDecoder().decode(chunks[1]));
        ObjectMapper mapper = new ObjectMapper();
        JsonNode actualObj = mapper.readTree(payload);

        return userService.findByName(actualObj.get("sub").asText());
    }

    @PostMapping
    public ResponseEntity<MediaDto> createMedia(@RequestHeader HttpHeaders httpHeaders, @RequestParam("file") MultipartFile file) throws JsonProcessingException {
        try {
            MediaDto mediaDto = new MediaDto();
            User user = getUserFromToken(httpHeaders);
            Event event = new Event();
            Media media = mediaService.save(file, user);
            if (media != null) {
                event.setEventName(CREATE);
                event.setMediaName(media.getName());
                event.setMediaUrl(media.getUrl());
                event.setUser(user);
                eventService.save(event);
                mediaDto = MediaDto.fromMedia(media);
            }
            return new ResponseEntity<>(mediaDto, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "File with name: " + file.getOriginalFilename() + " is exist");
        }
    }

    @Secured({"ROLE_ADMIN", "ROLE_MODERATOR"})
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseEntity<List<MediaDto>> getAllMedia() {
        List<Media> mediaList = mediaService.getAll();
        List<MediaDto> result = new ArrayList<>();
        mediaList.forEach(media ->
                result.add(MediaDto.fromMedia(media)));
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Secured({"ROLE_ADMIN", "ROLE_MODERATOR"})
    @GetMapping(value = "/{mediaId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseEntity<MediaDto> getMedia(@PathVariable Integer mediaId) {
        Media media = mediaService.getById(mediaId);

        if (media == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        MediaDto mediaDto = MediaDto.fromMedia(media);

        return new ResponseEntity<>(mediaDto, HttpStatus.OK);
    }

    @Secured({"ROLE_ADMIN", "ROLE_MODERATOR"})
    @DeleteMapping(value = "/{mediaId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteMedia(@RequestHeader HttpHeaders httpHeaders, @PathVariable Integer mediaId) throws JsonProcessingException {
        User user = getUserFromToken(httpHeaders);
        Event event = new Event();
        Media media = mediaService.getById(mediaId);

        if (media != null) {
            mediaService.deleteById(mediaId);
            event.setEventName(DELETE);
            event.setUser(user);
            event.setMediaName(media.getName());
            event.setMediaUrl(media.getUrl());
            eventService.save(event);
        }
    }

    @GetMapping(value = "/my-media", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<List<MediaDto>> getMyMedia(@RequestHeader HttpHeaders httpHeaders) throws JsonProcessingException {
        User user = getUserFromToken(httpHeaders);
        UserDto userDto = UserDto.fromUser(user);
        List<Media> mediaList = mediaService.getAllMediaByUserId(userDto.getId());
        List<MediaDto> result = new ArrayList<>();
        mediaList.forEach(media ->
                result.add(MediaDto.fromMedia(media)));

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
