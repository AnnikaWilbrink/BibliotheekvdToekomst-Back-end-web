package nl.workingtalent.wtlibrary.filter;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import nl.workingtalent.wtlibrary.model.User;
import nl.workingtalent.wtlibrary.repository.IUserRepository;

@Component
public class WtLibrarySecurityFilter extends OncePerRequestFilter {

	@Autowired
	private IUserRepository repo;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// Haal de request header op die in de frontend is gezet
		String authorizationToken = request.getHeader("Authorization");
		
		// Is de token wel ingevuld en niet leeg
		if (authorizationToken != null && !authorizationToken.isBlank()) {
			// Vind de token in de database
			Optional<User> optionalUser = repo.findByToken(authorizationToken);
			
			// Als de user wel gevonden is
			if (optionalUser.isPresent()) {
				// Haal de user op uit de optional
				User user = optionalUser.get();

				// Plaats de user in de request
				request.setAttribute("WT_USER", user);
			}
		}

		filterChain.doFilter(request, response);
	}

}
