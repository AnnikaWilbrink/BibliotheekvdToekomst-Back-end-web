package nl.workingtalent.wtlibrary.repository;

import java.util.List;
//import org.springframework.data.jpa.repository.Query;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import nl.workingtalent.wtlibrary.model.Favorite;

@Repository
public interface IFavoriteRepository extends JpaRepository<Favorite, Long> {
	
    List<Favorite> findByUserId(Long userId);
    
}
