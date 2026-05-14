package ru.kpfu.itis.tourbookingsystemspring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.kpfu.itis.tourbookingsystemspring.entity.Booking;
import ru.kpfu.itis.tourbookingsystemspring.entity.enums.BookingStatus;

import java.util.List;
import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    @Query("""
            SELECT b FROM Booking b
            JOIN FETCH b.excursionDate ed
            JOIN FETCH ed.excursion e
            JOIN FETCH e.guide
            WHERE b.tourist.id = :touristId
            ORDER BY b.bookingDate DESC
            """)
    List<Booking> findAllByTouristId(@Param("touristId") Long touristId);

    @Query("""
        SELECT b FROM Booking b
        JOIN FETCH b.excursionDate ed
        JOIN FETCH ed.excursion e
        JOIN FETCH e.guide
        WHERE b.tourist.id = :touristId
          AND b.status = :status
        ORDER BY b.bookingDate DESC
        """)
    List<Booking> findAllByTouristIdAndStatus(@Param("touristId") Long touristId,
                                              @Param("status") BookingStatus status);

    @Query("""
        SELECT b FROM Booking b
        JOIN FETCH b.tourist
        JOIN FETCH b.excursionDate ed
        JOIN FETCH ed.excursion e
        JOIN FETCH e.guide
        WHERE b.id = :id
        """)
    Optional<Booking> findByIdWithDetails(@Param("id") Long id);
}