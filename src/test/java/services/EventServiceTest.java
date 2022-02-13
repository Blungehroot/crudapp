package services;

import com.app.crudapp.model.*;
import com.app.crudapp.repository.EventRepository;
import com.app.crudapp.repository.RoleRepository;
import com.app.crudapp.repository.UserRepository;
import com.app.crudapp.service.impl.EventServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.app.crudapp.model.EventActions.CREATE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EventServiceTest {
    @Mock
    private EventRepository eventRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private BCryptPasswordEncoder passwordEncoded;

    @InjectMocks
    private EventServiceImpl eventService;

    @Test
    void saveEvent_shouldBeSuccess() {
        Event event = new Event();
        User user = new User();
        Role role = new Role();
        List<Role> roleList = new ArrayList<>();

        role.setId(null);
        role.setName("ROLE_ADMIN");

        roleList.add(role);

        user.setId(1);
        user.setName("Test");
        user.setPassword(passwordEncoded.encode("test"));
        user.setStatus(Status.ACTIVE);
        user.setRoles(roleList);

        event.setEventName(CREATE);
        event.setId(null);
        event.setMediaName("test");
        event.setMediaUrl("/test/url");
        event.setUser(user);

        when(eventRepository.save(event)).thenReturn(event);

        Event eventActual = eventService.save(event);

        assertNotNull(eventActual);
        assertEquals(event, eventActual);

        verify(eventRepository).save(ArgumentMatchers.eq(event));
    }

    @Test
    void getEventById_shouldBeSuccess() {
        Event event = new Event();
        User user = new User();
        Role role = new Role();
        List<Role> roleList = new ArrayList<>();

        role.setId(null);
        role.setName("ROLE_ADMIN");

        roleList.add(role);

        user.setId(1);
        user.setName("Test");
        user.setPassword(passwordEncoded.encode("test"));
        user.setStatus(Status.ACTIVE);
        user.setRoles(roleList);

        event.setEventName(CREATE);
        event.setId(1);
        event.setMediaName("test");
        event.setMediaUrl("/test/url");
        event.setUser(user);

        when(eventRepository.findById(event.getId()))
                .thenReturn(Optional.of(event));

        assertNotNull(eventService.getById(event.getId()));

        verify(eventRepository).findById(event.getId());
    }

    @Test
    void getAll_shouldBeSuccess() {
        Event event = new Event();
        User user = new User();
        Role role = new Role();
        List<Role> roleList = new ArrayList<>();
        List<Event> eventList = new ArrayList<>();

        role.setId(null);
        role.setName("ROLE_ADMIN");

        roleList.add(role);

        user.setId(1);
        user.setName("Test");
        user.setPassword(passwordEncoded.encode("test"));
        user.setStatus(Status.ACTIVE);
        user.setRoles(roleList);

        event.setEventName(CREATE);
        event.setId(1);
        event.setMediaName("test");
        event.setMediaUrl("/test/url");
        event.setUser(user);

        eventList.add(event);

        when(eventRepository.findAll()).thenReturn(eventList);

        assertEquals(eventList, eventService.getAll());

        verify(eventRepository).findAll();
    }
}
