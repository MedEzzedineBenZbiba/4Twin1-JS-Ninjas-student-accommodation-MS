package tn.esprit.tpfoyer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tn.esprit.tpfoyer.Reservation;

import java.util.Date;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Integer> {
    List<Reservation> findAllByEstValide(boolean b);

    @Query("SELECT count(r) FROM Reservation r WHERE r.anneeUniversitaire BETWEEN :startDate AND :endDate")
    Integer ReservationsNumberBetweenDates(@Param("startDate") Date startDate, @Param("endDate") Date endDate);


    @Query("SELECT r FROM Reservation r ORDER BY r.anneeUniversitaire DESC")
    List<Reservation> findAllOrderedByDateDesc();





}
