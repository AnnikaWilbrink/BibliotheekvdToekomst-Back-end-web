package nl.workingtalent.wtlibrary.repository;

import java.util.List; 
import org.springframework.data.jpa.repository.JpaRepository;

import nl.workingtalent.wtlibrary.model.Reservation;

public interface IReservationRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findByUserId(Long userId);
    Reservation findByUserIdAndBookId(Long userId, Long bookId);
    List<Reservation> findByUserIdAndDeletedFalse(Long userId);
    
}
