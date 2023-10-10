package nl.workingtalent.wtlibrary.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import nl.workingtalent.wtlibrary.dto.LoginResponseDto;
import nl.workingtalent.wtlibrary.dto.SaveUserDto;
import nl.workingtalent.wtlibrary.dto.UserDto;
import nl.workingtalent.wtlibrary.dto.UserLoginDto;
import nl.workingtalent.wtlibrary.model.User;
import nl.workingtalent.wtlibrary.service.UserService;

@RestController
@CrossOrigin(maxAge=3600)
public class UserController {
    
    @Autowired
    private UserService service;
    
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
    
    @RequestMapping(method = RequestMethod.POST, value="user/save")
    public ResponseEntity<Boolean> save(@RequestBody SaveUserDto dto) {
        User user = new User();
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setPassword(dto.getPassword());  // Consider hashing before saving
        user.setRole(dto.getRole());
        user.setEmail(dto.getEmail());
        user.setPhoneNumber(dto.getPhoneNumber());

        service.save(user);
        return new ResponseEntity<>(false, HttpStatus.OK);
    }
    
    @RequestMapping("user/{id}")
    public Optional<UserDto> findById(@PathVariable long id) {
        Optional<User> optional = service.findById(id);
        if(optional.isPresent()) {
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

        return Optional.empty();
    }
    
    @RequestMapping(method = RequestMethod.PUT, value="user/{id}")
    public boolean update(@PathVariable long id, @RequestBody SaveUserDto dto) {
        Optional<User> optional = service.findById(id);
        if(optional.isEmpty()) {
            return false;
        }
        User existingUser = optional.get();
        existingUser.setFirstName(dto.getFirstName());
        existingUser.setLastName(dto.getLastName());
        existingUser.setPassword(dto.getPassword());  // Consider hashing if changed
        existingUser.setRole(dto.getRole());
        existingUser.setEmail(dto.getEmail());
        existingUser.setPhoneNumber(dto.getPhoneNumber());
        existingUser.setlastUpdatedDate();

        service.update(existingUser);
        return true;
    }
    
    @RequestMapping(method = RequestMethod.DELETE, value="user/{id}")
    public boolean delete(@PathVariable long id) {
    	service.delete(id);
    	return true;
    }
    
	@RequestMapping(method = RequestMethod.POST, value="user/login")
	public LoginResponseDto login(@RequestBody UserLoginDto loginUser) {
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
	
}
