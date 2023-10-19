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

@Service
public class UserService {

	@Autowired 
	private IUserRepository repository;

	public List<User> findAll(){
		return repository.findAll();
	}

	public void save(User user) {
		// Hash the password before saving the user
//		user.setPassword(passwordEncoder.encode(user.getPassword()));
		repository.save(user);
	}

	public Optional<User> findById(long id){
		return repository.findById(id);
	}

	public void delete(long id) {
		repository.deleteById(id);
	}

	public void update(User user) {
		// to-do: Only hash if it is changed
//		user.setPassword(passwordEncoder.encode(user.getPassword()));
		repository.save(user);
	}
	
	public Optional<User> authenticate(String email, String password) {
		Optional<User> optional = repository.findByEmailAndPassword(email, password);
		
		if (optional.isPresent()) {
			User user = optional.get();
			user.setToken(generateToken());
			repository.save(user);
		}

		return optional;
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

		if (!user.getPassword().equals(currentPassword)) {
			return false;
		}

		user.setPassword(newPassword);
		repository.save(user);
		return true;
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
}
