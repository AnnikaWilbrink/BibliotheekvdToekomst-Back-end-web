//import nl.workingtalent.wtlibrary.model.User;
//import nl.workingtalent.wtlibrary.repository.IUserRepository;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.web.client.TestRestTemplate;
//import org.springframework.http.ResponseEntity;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//public class UserIntegrationTest {
//
//    @Autowired
//    private TestRestTemplate restTemplate;
//
//    @Autowired
//    IUserRepository userRepository;
//
//    @Test
//    public void testCreateUser() {
//        SaveUserDto dto = new SaveUserDto();
//        dto.setFirstName("Integration");
//        dto.setLastName("Test");
//        dto.setPassword("testPassword");
//        dto.setRole("USER");
//        dto.setEmail("integrationtest@example.com");
//        dto.setPhoneNumber("1234567890");
//
//        ResponseEntity<Boolean> response = restTemplate.postForEntity("/user/save", dto, Boolean.class);
//
//        assertEquals(200, response.getStatusCodeValue());
//        assertTrue(response.getBody());
//
//        // Assert that the user is actually saved in the database
//        assertTrue(userRepository.findByEmail("integrationtest@example.com").isPresent());
//    }
//
//    // More integration tests for other endpoints...
//}
