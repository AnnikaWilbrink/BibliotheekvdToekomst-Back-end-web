package nl.workingtalent.wtlibrary.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import nl.workingtalent.wtlibrary.model.User;

public interface IUserRepository extends JpaRepository<User, Long>{

	Optional<User> findByEmail(String email);
	
	Optional<User> findByEmailAndPassword(String email, String password);
	
	long countByRole(String role);
	
	boolean existsByPhoneNumber(String phoneNumber);
	
	Optional<User> findByToken(String token);

	Optional<User> findByIdAndTokenAndRole(long id, String token, String role);
	
	List<User> findByRole(String role);
	
}
