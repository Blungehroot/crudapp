package com.app.crudapp.controller;

import com.app.crudapp.model.Event;
import com.app.crudapp.model.Media;
import com.app.crudapp.model.User;
import com.app.crudapp.service.EventService;
import com.app.crudapp.service.MediaService;
import com.app.crudapp.service.UserService;
import com.app.crudapp.service.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.app.crudapp.model.EventActions.CREATE;

@RestController
@RequestMapping(value = "/api/v1/media")
@RequiredArgsConstructor
@Validated
public class MediaController {
    @Autowired
    private MediaService mediaService;

    @Autowired
    private final UserService userService;

    @Autowired
    private EventService eventService;

    @PostMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public Media createMedia(@RequestParam("file") MultipartFile file, @RequestHeader(value = "user_id") String userId) {
        User user = userService.getById(Integer.parseInt(userId));
        Media media;
        Event event = new Event();
        media = mediaService.save(file);
        event.setEventName(CREATE);
        event.setUser(user);
        event.setMedia(media);
        eventService.save(event);
        return media;
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
