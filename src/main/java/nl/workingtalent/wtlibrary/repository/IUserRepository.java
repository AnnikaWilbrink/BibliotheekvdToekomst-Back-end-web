package nl.workingtalent.wtlibrary.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import nl.workingtalent.wtlibrary.model.User;

public interface IUserRepository extends JpaRepository<User, Long>{

	Optional<User> findByEmail(String email);
	
	Optional<User> findByEmailAndPassword(String email, String password);
	
	long countByRole(String role);
	
	boolean existsByPhoneNumber(String phoneNumber);
	
}
