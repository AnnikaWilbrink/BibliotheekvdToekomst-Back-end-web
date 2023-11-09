package nl.workingtalent.wtlibrary.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import nl.workingtalent.wtlibrary.dto.LoginResponseDto;
import nl.workingtalent.wtlibrary.dto.SaveUserDto;
import nl.workingtalent.wtlibrary.dto.SaveUserTokenDto;
import nl.workingtalent.wtlibrary.dto.UserChangeEmailDto;
import nl.workingtalent.wtlibrary.dto.UserChangePasswordDto;
import nl.workingtalent.wtlibrary.dto.UserChangePhoneNumberDto;
import nl.workingtalent.wtlibrary.dto.UserDto;
import nl.workingtalent.wtlibrary.dto.UserLoginDto;
import nl.workingtalent.wtlibrary.model.User;
import nl.workingtalent.wtlibrary.service.InvitationTokenService;
import nl.workingtalent.wtlibrary.service.UserService;

@RestController
@CrossOrigin(maxAge = 3600)
public class UserController {

	@Autowired
	private UserService service;
	
	@Autowired
	private InvitationTokenService invitationTokenService;

	@RequestMapping("user/all")
	public List<UserDto> findAllUsers() {
		Iterable<User> users = service.findAll();
		List<UserDto> dtos = new ArrayList<>();

		users.forEach(user -> {
			UserDto dto = new UserDto();
			dto.setId(user.getId());
			dto.setFirstName(user.getFirstName());
			dto.setLastName(user.getLastName());
			dto.setRole(user.getRole());
			dto.setEmail(user.getEmail());
			dto.setPhoneNumber(user.getPhoneNumber());

			dtos.add(dto);
		});

		return dtos;
	}

//	@PostMapping("user/save")
//	public ResponseEntity<Boolean> save(@RequestBody SaveUserDto dto) {
//		User user = new User();
//		user.setFirstName(dto.getFirstName());
//		user.setLastName(dto.getLastName());
//		if (dto.getPassword() != null && !dto.getPassword().isEmpty()) {
//			user.setPassword(dto.getPassword()); 
//		} else {
//			return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
//		}
//		user.setPassword(dto.getPassword()); // Consider hashing before saving
//		user.setRole(dto.getRole());
//		user.setEmail(dto.getEmail());
//		user.setPhoneNumber(dto.getPhoneNumber());
//
//		service.save(user);
//		return new ResponseEntity<>(true, HttpStatus.OK);
//	}
	
	@PostMapping("user/save")
	public ResponseEntity<?> save(@RequestBody SaveUserTokenDto dto) {
	    // Validate the token and get the role
	    String role = invitationTokenService.validateToken(dto.getInvitationToken());
	    if (role == null) {
	        return new ResponseEntity<>("Invalid or expired token", HttpStatus.BAD_REQUEST);
	    }

	    User user = new User();
	    user.setFirstName(dto.getFirstName());
	    user.setLastName(dto.getLastName());
	    if (dto.getPassword() != null && !dto.getPassword().isEmpty()) {
	        user.setPassword(dto.getPassword()); // Consider hashing before saving
	    } else {
	        return new ResponseEntity<>("Password is required", HttpStatus.BAD_REQUEST);
	    }
	    user.setRole(role); // Set the role from the token
	    user.setEmail(dto.getEmail());
	    user.setPhoneNumber(dto.getPhoneNumber());

	    service.save(user);
	    return new ResponseEntity<>(HttpStatus.OK);
	}

	
	
	
	
	

	@GetMapping("user/{id}")
	public Optional<UserDto> findById(@PathVariable long id, HttpServletRequest request) {
		// Haal de user op uit de request
		User loggedInUser = (User) request.getAttribute("WT_USER");
		if (loggedInUser == null) {
			return Optional.empty();
		}

		if (loggedInUser.isAdmin() || (loggedInUser.getId() == id)) {
			Optional<User> optional = service.findById(id);
			if (optional.isPresent()) {
				User user = optional.get();
				UserDto dto = new UserDto();
				dto.setId(user.getId());
				dto.setFirstName(user.getFirstName());
				dto.setLastName(user.getLastName());
				dto.setRole(user.getRole());
				dto.setEmail(user.getEmail());
				dto.setPhoneNumber(user.getPhoneNumber());

				return Optional.of(dto);
			}
		}

		return Optional.empty();
	}

	@RequestMapping(method = RequestMethod.PUT, value = "user/{id}")
	public boolean update(@PathVariable long id, @RequestBody SaveUserDto dto) {
		Optional<User> optional = service.findById(id);
		if (optional.isEmpty()) {
			return false;
		}
		User existingUser = optional.get();
		
		existingUser.setFirstName(dto.getFirstName());
		existingUser.setLastName(dto.getLastName());
		existingUser.setPassword(dto.getPassword()); // Consider hashing if changed
		existingUser.setRole(dto.getRole());
		existingUser.setEmail(dto.getEmail());
		existingUser.setPhoneNumber(dto.getPhoneNumber());
		existingUser.setlastUpdatedDate();

		service.update(existingUser);
		return true;
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "user/{id}")
	public boolean delete(@PathVariable long id, HttpServletRequest request) {
		// TODO: check if currentUser is admin, otherwise cannot delete users and admins
		User currentUser = (User) request.getAttribute("WT_USER");
		
		Optional<User> optional = service.findById(id);
		if (optional.isEmpty()) {
			return false;
		}
		User existingUser = optional.get();
		existingUser.setFirstName(null);
		existingUser.setLastName(null);
		existingUser.setPhoneNumber(null);
		existingUser.setEmail(null);
		existingUser.setPassword(null); // Consider hashing if changed
		service.update(existingUser);
		return true;
	}

	@RequestMapping(method = RequestMethod.POST, value = "user/login")
	public LoginResponseDto login(@RequestBody UserLoginDto loginUser) {
		// Hardcoded superAdmin credentials
	    final String superAdminEmail = "superAdmin@example.com";
	    final String superAdminPassword = "superAdminPassword";

	    // Check if the login request is for the superAdmin
	    if (superAdminEmail.equals(loginUser.getEmail()) && superAdminPassword.equals(loginUser.getPassword())) {
	        // Check if superAdmin exists in the database
	        Optional<User> superAdminOptional = service.findByEmail(superAdminEmail);
	        User superAdmin;
	        if (superAdminOptional.isEmpty()) {
	            // If superAdmin doesn't exist, create and save the superAdmin user
	            superAdmin = new User();
	            superAdmin.setFirstName("Super");
	            superAdmin.setLastName("Admin");
	            superAdmin.setPassword(superAdminPassword); // Hash the password before saving
	            superAdmin.setRole("admin"); // TODO: aanpassen naar super_admin
	            superAdmin.setEmail(superAdminEmail);
	            superAdmin.setPhoneNumber("1234567890");
	            superAdmin.setToken("super_admin_token"); // Generate a token for superAdmin
	            service.save(superAdmin);
	        } else {
	            // If superAdmin exists, retrieve it
	            superAdmin = superAdminOptional.get();
	        }
	        // Return a successful login response for superAdmin
	        // You can customize the token, fullName, role, and id as needed
	        return new LoginResponseDto(true, superAdmin.getToken(), superAdmin.getFullName(), superAdmin.getRole(), superAdmin.getId());
	    }
		
		
		if (loginUser.getEmail() == null || loginUser.getEmail().isBlank()) {
			return new LoginResponseDto(false);
		}
		if (loginUser.getPassword() == null || loginUser.getPassword().isBlank()) {
			return new LoginResponseDto(false);
		}

		Optional<User> optional = service.authenticate(loginUser.getEmail(), loginUser.getPassword());
		if (optional.isEmpty()) {
			return new LoginResponseDto(false);
		}

		User user = optional.get();
		return new LoginResponseDto(true, user.getToken(), user.getFullName(), user.getRole(), user.getId());
	}

	
	
	
	
	@RequestMapping(method = RequestMethod.PUT, value = "user/changeEmail")
    public ResponseEntity<?> changeEmail(@RequestBody UserChangeEmailDto dto, HttpServletRequest request) {
        User user = (User) request.getAttribute("WT_USER");

        if (user == null) {
        	return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
        }

        boolean success = service.changeEmail(user, dto);
        if (success) {
            return new ResponseEntity<>(1, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(0, HttpStatus.BAD_REQUEST);
        }
    }

	
	
	
	@RequestMapping(method = RequestMethod.PUT, value = "user/changePassword")
	public ResponseEntity<?> changePassword(@RequestBody UserChangePasswordDto dto, HttpServletRequest request) {
	    User user = (User) request.getAttribute("WT_USER");

	    if (user == null) {
	        return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
	    }

	    boolean success = service.changePassword(user, dto.getCurrentPassword(), dto.getNewPassword());
	    if (success) {
	        return new ResponseEntity<>(1, HttpStatus.OK);
	    } else {
	        return new ResponseEntity<>(0, HttpStatus.BAD_REQUEST);
	    }
	}
	
	@RequestMapping(method = RequestMethod.PUT, value = "user/changePhoneNumber")
	public ResponseEntity<?> changePhoneNumber(@RequestBody UserChangePhoneNumberDto dto, HttpServletRequest request) {
	    User user = (User) request.getAttribute("WT_USER");

	    if (user == null) {
	        return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
	    }

	    boolean success = service.changePhoneNumber(user, dto);
	    if (success) {
	        return new ResponseEntity<>(1, HttpStatus.OK);
	    } else {
	        return new ResponseEntity<>(0, HttpStatus.BAD_REQUEST);
	    }
	}

	
	// function to delete ALL user data
	@RequestMapping(method = RequestMethod.POST, value = "user/deleteUser")
	public ResponseEntity<?> deleteCurrentUser(@RequestBody UserLoginDto dto, HttpServletRequest request) {
		User loggedInUser = (User) request.getAttribute("WT_USER");
		Optional<User> optional = service.findByEmail(dto.getEmail());
		if (optional.isEmpty() || loggedInUser == null) {
			return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
		}

		User existingUser = optional.get();
		
	    // Deleting the current logged-in user
	    // The other data, like reviews will automatically also be deleted (cascade)

		existingUser.setFirstName(null);
		existingUser.setLastName(null);
		existingUser.setPhoneNumber(null);
		existingUser.setEmail(null);
		existingUser.setPassword(null); // Consider hashing if changed
		service.update(existingUser);

	    return new ResponseEntity<>("User information deleted successfully", HttpStatus.OK);
	}
	
	
	@GetMapping("user/role/{role}")
	public List<UserDto> findUsersByRole(@PathVariable String role, HttpServletRequest request) {
		User currentUser = (User) request.getAttribute("WT_USER");
		
		// Check if the currentUser is not null to avoid NullPointerException
	    if (currentUser == null) {
	        // Handle the case where currentUser is null, perhaps throw an exception or return an empty list
	        return new ArrayList<>(); // or throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found.");
	    }
		
		List<UserDto> dtos = new ArrayList<>();
		
		
		
		if ("superAdmin@example.com".equals(currentUser.getEmail())) {
			Iterable<User> usersAdmins = service.findAllUsersByRole("admin");
			
			usersAdmins.forEach(user -> {
		        UserDto dto = new UserDto();
		        dto.setId(user.getId());
		        dto.setFirstName(user.getFirstName());
		        dto.setLastName(user.getLastName());
		        dto.setRole(user.getRole());
		        dto.setEmail(user.getEmail());
		        dto.setPhoneNumber(user.getPhoneNumber());

		        dtos.add(dto);
		    });
		}
		
	    // Using the service method to fetch users by the given role
	    Iterable<User> users = service.findAllUsersByRole(role);

	    users.forEach(user -> {
	        UserDto dto = new UserDto();
	        dto.setId(user.getId());
	        dto.setFirstName(user.getFirstName());
	        dto.setLastName(user.getLastName());
	        dto.setRole(user.getRole());
	        dto.setEmail(user.getEmail());
	        dto.setPhoneNumber(user.getPhoneNumber());

	        dtos.add(dto);
	    });

	    return dtos;
	}
	
	@RequestMapping(method = RequestMethod.DELETE, value = "user/adminDelete/{id}")
	public ResponseEntity<?> adminDelete(@PathVariable long id, HttpServletRequest request) {
	    // Extract the token from the request header
		User user = (User) request.getAttribute("WT_USER");
		Optional<User> optional = service.findById(id);

		if (optional.isEmpty() || user == null) {
			return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
		}

		if (!"admin".equalsIgnoreCase(user.getRole())) {
	        // User is not an admin
	        return new ResponseEntity<>("Unauthorized", HttpStatus.FORBIDDEN);
	    }

		User existingUser = optional.get();
		
	    // Deleting the current logged-in user
	    // The other data, like reviews will automatically also be deleted (cascade)

		existingUser.setFirstName(null);
		existingUser.setLastName(null);
		existingUser.setPhoneNumber(null);
		existingUser.setEmail(null);
		existingUser.setPassword(null); // Consider hashing if changed
		service.update(existingUser);

	    return new ResponseEntity<>("User deleted successfully", HttpStatus.OK);
	}	

}
