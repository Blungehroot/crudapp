package services;

import com.app.crudapp.model.*;
import com.app.crudapp.repository.MediaRepository;
import com.app.crudapp.repository.RoleRepository;
import com.app.crudapp.repository.UserRepository;
import com.app.crudapp.service.impl.MediaServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
        Media media = new Media();
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

        media.setId(null);
        media.setName("test");
        media.setUrl("/url/test");
        media.setUser(user);

        when(mediaRepository.save(media)).thenReturn(media);

        Media mediaActual = mediaService.save(media, user);

        assertNotNull(media);
        assertEquals(media, mediaActual);

        verify(mediaRepository).save(ArgumentMatchers.eq(media));

    }
}
