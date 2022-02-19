package services;

import com.app.crudapp.model.Media;
import com.app.crudapp.model.Role;
import com.app.crudapp.model.Status;
import com.app.crudapp.model.User;
import com.app.crudapp.repository.MediaRepository;
import com.app.crudapp.repository.RoleRepository;
import com.app.crudapp.repository.UserRepository;
import com.app.crudapp.service.impl.MediaServiceImpl;
import net.bytebuddy.utility.RandomString;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MediaServiceTest {
    @Mock
    private MediaRepository mediaRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private BCryptPasswordEncoder passwordEncoded;

    @InjectMocks
    private MediaServiceImpl mediaService;

    @Test
    void saveMedia_shouldBeSuccess() {
        String name = RandomString.make(6) + ".json";
        MockMultipartFile jsonFile = new MockMultipartFile(name, name, "application/json", "{\"key1\": \"value1\"}".getBytes());

        Media media = new Media();
        User user = new User();
        Role role = new Role();
        List<Role> roleList = new ArrayList<>();

        role.setId(1);
        role.setName("ROLE_ADMIN");

        roleList.add(role);

        user.setId(1);
        user.setName("Test");
        user.setPassword(passwordEncoded.encode("test"));
        user.setStatus(Status.ACTIVE);
        user.setRoles(roleList);

        media.setId(null);
        media.setName(jsonFile.getOriginalFilename());
        media.setUrl("src/main/resources/uploads/" + jsonFile.getOriginalFilename());
        media.setUser(user);

        when(mediaRepository.save(media)).thenReturn(media);

        Media mediaActual = mediaService.save(jsonFile, user);

        assertNotNull(mediaActual);

        verify(mediaRepository).save(ArgumentMatchers.eq(media));
    }

    @Test
    void getMediaById_shouldBeSuccess() {
        Media media = new Media();
        User user = new User();
        Role role = new Role();
        List<Role> roleList = new ArrayList<>();

        role.setId(1);
        role.setName("ROLE_ADMIN");

        roleList.add(role);

        user.setId(1);
        user.setName("Test");
        user.setPassword(passwordEncoded.encode("test"));
        user.setStatus(Status.ACTIVE);
        user.setRoles(roleList);

        media.setId(1);
        media.setName("Test");
        media.setUrl("src/main/resources/uploads/test");
        media.setUser(user);

        when(mediaRepository.findById(media.getId())).thenReturn(Optional.of(media));

        assertNotNull(mediaService.getById(media.getId()));

        verify(mediaRepository).findById(media.getId());
    }

    @Test
    void getAllMedia_shouldBeSuccess() {
        Media media = new Media();
        User user = new User();
        Role role = new Role();
        List<Role> roleList = new ArrayList<>();
        List<Media> mediaList = new ArrayList<>();

        role.setId(1);
        role.setName("ROLE_ADMIN");

        roleList.add(role);

        user.setId(1);
        user.setName("Test");
        user.setPassword(passwordEncoded.encode("test"));
        user.setStatus(Status.ACTIVE);
        user.setRoles(roleList);

        media.setId(1);
        media.setName("Test");
        media.setUrl("src/main/resources/uploads/test");
        media.setUser(user);

        mediaList.add(media);

        when(mediaRepository.findAll()).thenReturn(mediaList);

        assertEquals(mediaList, mediaService.getAll());

        verify(mediaRepository).findAll();
    }

    @Test
    void deleteMedia_shouldBeSuccess() {
        Media media = new Media();
        User user = new User();
        Role role = new Role();
        List<Role> roleList = new ArrayList<>();

        role.setId(1);
        role.setName("ROLE_ADMIN");

        roleList.add(role);

        user.setId(1);
        user.setName("Test");
        user.setPassword(passwordEncoded.encode("test"));
        user.setStatus(Status.ACTIVE);
        user.setRoles(roleList);

        media.setId(1);
        media.setName("Test");
        media.setUrl("src/main/resources/uploads/test");
        media.setUser(user);

        when(mediaRepository.findById(media.getId())).thenReturn(Optional.of(media));
        doNothing().when(mediaRepository).delete(media);

        mediaService.deleteById(media.getId());

        verify(mediaRepository).delete(media);
    }
}
