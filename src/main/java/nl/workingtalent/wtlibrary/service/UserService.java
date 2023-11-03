package nl.workingtalent.wtlibrary.service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nl.workingtalent.wtlibrary.dto.UserChangeEmailDto;
import nl.workingtalent.wtlibrary.dto.UserChangePhoneNumberDto;
import nl.workingtalent.wtlibrary.model.User;
import nl.workingtalent.wtlibrary.repository.IUserRepository;
import nl.workingtalent.wtlibrary.service.PasswordHashingService;

@Service
public class UserService {

	@Autowired 
	private IUserRepository repository;

	@Autowired
	private PasswordHashingService passwordHashingService;

	public List<User> findAll(){
		return repository.findAll();
	}

	public void save(User user) {
		String rawPassword = user.getPassword();
        String hashedPassword = passwordHashingService.hashPassword(rawPassword);
		user.setPassword(hashedPassword);
		repository.save(user);
	}

	public Optional<User> findById(long id){
		return repository.findById(id);
	}

	public void delete(User user) {
		repository.save(user);
		// repository.deleteById(id);
	}

	public void update(User user) {
		// to-do: Only hash if it is changed
//		user.setPassword(passwordEncoder.encode(user.getPassword()));
		repository.save(user);
	}
	
	public Optional<User> authenticate(String email, String password) {
		
		Optional<User> optional = repository.findByEmail(email);
		
		if (optional.isPresent()) {
			User user = optional.get();
			String storedHashedPassword = user.getPassword();
			if (passwordHashingService.verifyPassword(password, storedHashedPassword)) {
				user.setToken(generateToken());
				repository.save(user);
				return optional;
		}
	}
	return Optional.empty();	
	}
	
	private String generateToken() {
		int leftLimit = 48; // numeral '0'
	    int rightLimit = 122; // letter 'z'
	    int targetStringLength = 90;

	    Random random = new Random();

	    return random.ints(leftLimit, rightLimit + 1)
	      .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
	      .limit(targetStringLength)
	      .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
	      .toString();
	}

	
	public boolean changePassword(User user, String currentPassword, String newPassword) {

		if (user == null) {
			return false;
		}

		String storedHashedPassword = user.getPassword();

		if (passwordHashingService.verifyPassword(currentPassword, storedHashedPassword)) {
			String newHashedPassword = passwordHashingService.hashPassword(newPassword);
			user.setPassword(newHashedPassword);
			repository.save(user);
			return true;
		}

		return false;
	}
	
	public boolean changeEmail(User user, UserChangeEmailDto dto) {
		
		if (user == null) {
			return false;
		}
		
		if(!user.getEmail().equals(dto.getCurrentEmail())) {
			return false;
		}
		
		user.setEmail(dto.getNewEmail());
		repository.save(user);
		return true;
	}
	
	public boolean changePhoneNumber(User user, UserChangePhoneNumberDto dto) {

		if (user == null) {
			return false;
		}

		if (!user.getPhoneNumber().equals(dto.getCurrentPhoneNumber())) {
			return false;
		}

		user.setPhoneNumber(dto.getNewPhoneNumber());
		repository.save(user);
		return true;
	}
	
	public List<User> findAllUsersByRole(String role) {
	    // Use a repository method to fetch users by role.
	    // If you're using Spring Data JPA, it could be a method like `findByRole(String role)`.
	    return repository.findByRole(role);
	}
}
