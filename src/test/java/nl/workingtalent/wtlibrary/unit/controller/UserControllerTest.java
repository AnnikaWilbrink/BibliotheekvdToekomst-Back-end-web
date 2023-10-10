package nl.workingtalent.wtlibrary.unit.controller;

import nl.workingtalent.wtlibrary.controller.UserController;
import nl.workingtalent.wtlibrary.dto.SaveUserDto;
import nl.workingtalent.wtlibrary.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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
}
