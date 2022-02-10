package services;

import com.app.crudapp.model.Role;
import com.app.crudapp.model.Status;
import com.app.crudapp.model.User;
import com.app.crudapp.repository.RoleRepository;
import com.app.crudapp.repository.UserRepository;
import com.app.crudapp.service.impl.UserServiceImpl;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private BCryptPasswordEncoder passwordEncoded;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void saveUser_shouldBeSuccess() {
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

        when(userRepository.save(user)).thenReturn(user);

        User userActual = userService.save(user);

        assertNotNull(userActual);
        assertEquals(user, userActual);

        verify(userRepository).save(ArgumentMatchers.eq(user));
    }

    @Test
    void deleteUser_shouldBeSuccess() {
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

        when(userRepository.findById(user.getId()))
                .thenReturn(Optional.of(user));
        doNothing().when(userRepository).delete(user);

        userService.deleteById(user.getId());

        verify(userRepository).delete(user);
    }

    /*@Test
    void updateUser_shouldBeSuccess() {
        User user = new User();
        User userUpdate = new User();

        user.setName("Tester");
        user.setId(1);

        userUpdate.setId(1);
        userUpdate.setName("Tester update");

        lenient().when(userRepository.save(user)).thenReturn(user);
        lenient().when(userRepository.update(userUpdate)).thenReturn(userUpdate);

        User result = userService.update(userUpdate);

        assertNotNull(result);
        assertNotEquals(user, result);

        verify(userRepository).update(userUpdate);
    }*/

    @Test
    void getUserById_shouldBeSuccess() {
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

        when(userRepository.findById(user.getId()))
                .thenReturn(Optional.of(user));

        assertNotNull(userService.getById(user.getId()));

        verify(userRepository).findById(user.getId());

    }

    @Test
    void getAll_shouldBeSuccess() {
        User user = new User();
        Role role = new Role();
        List<Role> roleList = new ArrayList<>();
        List<User> users = new ArrayList<>();

        role.setId(null);
        role.setName("ROLE_ADMIN");

        roleList.add(role);

        user.setId(1);
        user.setName("Test");
        user.setPassword(passwordEncoded.encode("test"));
        user.setStatus(Status.ACTIVE);
        user.setRoles(roleList);

        users.add(user);

        when(userRepository.findAll()).thenReturn(users);

        assertEquals(users, userService.getAll());

        verify(userRepository).findAll();
    }
}
