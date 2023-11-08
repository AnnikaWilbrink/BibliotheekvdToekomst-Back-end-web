package nl.workingtalent.wtlibrary.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nl.workingtalent.wtlibrary.model.InvitationToken;
import nl.workingtalent.wtlibrary.repository.IInvitationTokenRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class InvitationTokenService {

    @Autowired
    private IInvitationTokenRepository repository;

    public InvitationToken generateToken(String role) {
    	// Generate a unique token using UUID
        String token = UUID.randomUUID().toString();
        InvitationToken invitationToken = new InvitationToken();
        invitationToken.setToken(token);
        invitationToken.setRole(role);
        invitationToken.setDateUsed(null);

        // Set the expiration date, e.g., 30 days from now
        LocalDateTime expirationDate = LocalDateTime.now().plusDays(30);
        invitationToken.setExpirationDate(expirationDate);

        repository.save(invitationToken);
        return invitationToken;
    }

//    public String validateToken(String token) {
//        Optional<InvitationToken> invitationTokenOpt = repository.findByToken(token);
//        if (invitationTokenOpt.isPresent()) {
//            InvitationToken invitationToken = invitationTokenOpt.get();
//            LocalDateTime now = LocalDateTime.now();
//            if (invitationToken.getDateUsed() == null && now.isBefore(invitationToken.getExpirationDate())) {
//                invitationToken.setDateUsed(now); // Set the current time as the used time
//                repository.save(invitationToken);
//                return invitationToken.getRole();
//            }
//        }
//        return null;
//    }
    
    public String validateToken(String token) {
        Optional<InvitationToken> invitationTokenOpt = repository.findByToken(token);
        if (invitationTokenOpt.isPresent()) {
            InvitationToken invitationToken = invitationTokenOpt.get();
            if (invitationToken.getExpirationDate().isAfter(LocalDateTime.now()) && invitationToken.getDateUsed() == null) {
                invitationToken.setDateUsed(LocalDateTime.now());
                repository.save(invitationToken);
                return invitationToken.getRole();
            }
        }
        return null;
    }

    
    public List<InvitationToken> getAllTokens() {
        // This should interact with your repository to fetch all tokens
        // For example, if you're using Spring Data JPA, it might look something like this:
        return repository.findAll();
    }
    
    public boolean deleteToken(String token) {
        Optional<InvitationToken> invitationTokenOptional = repository.findByToken(token);
        if (invitationTokenOptional.isPresent()) {
            repository.delete(invitationTokenOptional.get());
            return true;
        }
        return false;
    }
}
