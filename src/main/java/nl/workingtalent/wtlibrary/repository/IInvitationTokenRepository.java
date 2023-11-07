package nl.workingtalent.wtlibrary.repository;

import org.springframework.data.jpa.repository.JpaRepository; 

import java.util.List;
import java.util.Optional;

import nl.workingtalent.wtlibrary.model.InvitationToken; 

public interface IInvitationTokenRepository extends JpaRepository<InvitationToken,Long> {
	Optional<InvitationToken> findByToken(String token);
}
