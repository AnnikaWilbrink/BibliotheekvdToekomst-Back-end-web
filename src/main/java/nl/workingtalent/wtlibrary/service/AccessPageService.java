package nl.workingtalent.wtlibrary.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nl.workingtalent.wtlibrary.model.User;
import nl.workingtalent.wtlibrary.repository.IUserRepository;

@Service
public class AccessPageService {

    @Autowired
    private IUserRepository repository;

    public User adminHasPermissionToAccessPage(String role, String token, long userId) {
        Optional<User> optional = repository.findByIdAndTokenAndRole(userId, token, role);
        User user = optional.get();
        return user;
        /*
        if (user.getRole() == "admin"){
            return true;
        }
        else{
            return false;
        }
        */
    }

    



    public boolean frontdeskHasPermissionToAccessPage(String role, String token, long userId) {
        Optional<User> optional = repository.findByIdAndTokenAndRole(userId, token, role);
        if (optional.isPresent() && (role == "front-desk")) {
			return true;
		}
        return false;
    }
}
