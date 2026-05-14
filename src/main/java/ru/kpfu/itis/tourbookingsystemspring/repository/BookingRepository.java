package ru.kpfu.itis.tourbookingsystemspring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.kpfu.itis.tourbookingsystemspring.entity.Booking;

import java.util.List;

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
}