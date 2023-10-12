package nl.workingtalent.wtlibrary.unit.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import nl.workingtalent.wtlibrary.controller.UserController;
import nl.workingtalent.wtlibrary.dto.SaveUserDto;
import nl.workingtalent.wtlibrary.service.UserService;

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

        assertEquals(200, response.getStatusCode().value());
        assertTrue(response.getBody());
    }
}
