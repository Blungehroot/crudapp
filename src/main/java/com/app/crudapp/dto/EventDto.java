package com.app.crudapp.dto;

import com.app.crudapp.model.Event;
import com.app.crudapp.model.EventActions;
import com.app.crudapp.model.Media;
import com.app.crudapp.model.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class EventDto {
    private Integer id;
    private EventActions eventName;
    private String mediaName;
    private String mediaUrl;
    private User user;

    @JsonIgnoreProperties
    public static EventDto fromEvent(Event event) {
        EventDto eventDto = new EventDto();
        UserDto userDto = UserDto.fromUser(event.getUser());

        eventDto.setId(event.getId());
        eventDto.setEventName(event.getEventName());
        eventDto.setMediaName(event.getMediaName());
        eventDto.setMediaUrl(event.getMediaUrl());
        eventDto.setUser(userDto.toUser(userDto));

        return eventDto;
    }
}
