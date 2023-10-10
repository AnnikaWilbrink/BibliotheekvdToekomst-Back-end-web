package nl.workingtalent.wtlibrary.unit.controller;

import nl.workingtalent.wtlibrary.controller.UserController;
import nl.workingtalent.wtlibrary.dto.SaveUserDto;
import nl.workingtalent.wtlibrary.dto.UserDto;
import nl.workingtalent.wtlibrary.model.User;
import nl.workingtalent.wtlibrary.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

public class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    public UserControllerTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSaveUser() {
        SaveUserDto dto = new SaveUserDto();
        dto.setEmail("test.email@example.com");
        ResponseEntity<Boolean> response = userController.save(dto);

        assertEquals(200, response.getStatusCodeValue());
        assertTrue(response.getBody());
    }
    
    @Test
    public void testFindById() {
        long userId = 1L;
        User user = new User();
        user.setId(userId);
        user.setEmail("test.email@example.com");
        // ... set other fields ...

        when(userService.findById(userId)).thenReturn(Optional.of(user));

        Optional<UserDto> responseDto = userController.findById(userId);
        assertTrue(responseDto.isPresent());
        assertEquals(userId, responseDto.get().getId());
        assertEquals("test.email@example.com", responseDto.get().getEmail());

        verify(userService, times(1)).findById(userId);
    }
    
    @Test
    public void testDeleteUser() {
        long userId = 1L;

        doNothing().when(userService).delete(userId); // Mocking the delete method of UserService

        boolean response = userController.delete(userId);
        assertTrue(response);

        verify(userService, times(1)).delete(userId);
        assertFalse(true);
    }
}
