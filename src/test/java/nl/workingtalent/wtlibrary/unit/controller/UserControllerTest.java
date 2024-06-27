package nl.workingtalent.wtlibrary.unit.controller;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import nl.workingtalent.wtlibrary.controller.UserController;
import nl.workingtalent.wtlibrary.service.UserService;

public class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    public UserControllerTest() {
        MockitoAnnotations.openMocks(this);
    }

//    @Test
//    public void testSaveUser() {
//    	SaveUserTokenDto dto = new SaveUserTokenDto();
//        dto.setEmail("test.email@example.com");
//        dto.setInvitationToken("1234");
//        ResponseEntity<?> response = userController.save(dto);
//
//        assertEquals(200, response.getStatusCode().value());
////        assert(response.getBody());
//    }
//    
////    @Test
////    public void testFindById() {
////        long userId = 1L;
////        User user = new User();
////        user.setId(userId);
////        user.setEmail("test.email@example.com");
////        // ... set other fields ...
////
////        when(userService.findById(userId)).thenReturn(Optional.of(user));
////
////        Optional<UserDto> responseDto = userController.findById(userId);
////        assertTrue(responseDto.isPresent());
////        assertEquals(userId, responseDto.get().getId());
////        assertEquals("test.email@example.com", responseDto.get().getEmail());
////
////        verify(userService, times(1)).findById(userId);
////    }
//    
//    @Test
//    public void testDeleteUser() {
//        User user = new User();
//        user.setId(1L);
//
//        doNothing().when(userService).delete(user); // Mocking the delete method of UserService
//
//        boolean response = userController.delete(1L);
//        assertTrue(response);
//
//        verify(userService, times(1)).delete(user);
//    }
}
