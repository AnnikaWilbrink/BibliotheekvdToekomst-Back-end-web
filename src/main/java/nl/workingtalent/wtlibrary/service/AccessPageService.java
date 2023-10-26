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

    @Autowired 
    private UserService service;

    public boolean hasPermissionToAccessPage(String role, String token, long userId) {
        Optional<User> optional = repository.findByIdAndTokenAndRole(userId, token, role);
        if (optional.isPresent()) {
			return true;
		}
        return false;
    }
}
