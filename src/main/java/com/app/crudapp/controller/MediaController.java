package com.app.crudapp.controller;

import com.app.crudapp.model.Event;
import com.app.crudapp.model.Media;
import com.app.crudapp.model.User;
import com.app.crudapp.service.EventService;
import com.app.crudapp.service.MediaService;
import com.app.crudapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static com.app.crudapp.model.EventActions.CREATE;

@RestController
@RequestMapping(value = "/api/v1/media")
@Validated
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

    @PostMapping
    public ResponseEntity<Media> createMedia(@RequestParam("file") MultipartFile file, @RequestHeader(value = "user_id") String userId) {
        try {
            User user = userService.getById(Integer.parseInt(userId));
            Event event = new Event();
            Media media = mediaService.save(file);
            event.setEventName(CREATE);
            event.setUser(user);
            event.setMedia(media);
            eventService.save(event);
            media.setEvent(event);

            return new ResponseEntity<>(media, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "File with name: " + file.getOriginalFilename() + " is exist");
        }
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<Media> getAllMedia() {
        return mediaService.getAll();
    }

    @GetMapping(value = "/{mediaId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Media getMedia(@PathVariable Integer mediaId) {
        return mediaService.getById(mediaId);
    }
}
