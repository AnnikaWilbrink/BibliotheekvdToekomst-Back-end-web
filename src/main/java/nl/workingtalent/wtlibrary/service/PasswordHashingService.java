package nl.workingtalent.wtlibrary.service;
import org.springframework.stereotype.Service;
import org.mindrot.jbcrypt.BCrypt;

@Service
public class PasswordHashingService {

    public String hashPassword(String password) {
    String salt = BCrypt.gensalt(); // Generate a random salt
        return BCrypt.hashpw(password, salt); // Hash the password with the salt
    }

    public boolean verifyPassword(String password, String hashedPassword) {
        return BCrypt.checkpw(password, hashedPassword); // Verify the password against the hashed password
    } 
}
