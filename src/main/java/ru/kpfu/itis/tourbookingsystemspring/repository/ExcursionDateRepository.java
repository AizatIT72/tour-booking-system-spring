package ru.kpfu.itis.tourbookingsystemspring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.kpfu.itis.tourbookingsystemspring.entity.ExcursionDate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ExcursionDateRepository extends JpaRepository<ExcursionDate, Long> {

    @Query("""
            SELECT d FROM ExcursionDate d
            WHERE d.excursion.id = :excursionId
              AND d.dateTime > :now
              AND d.isAvailable = true
              AND d.availableSlots > 0
            ORDER BY d.dateTime ASC
            """)
    List<ExcursionDate> findUpcomingAvailable(@Param("excursionId") Long excursionId,
                                              @Param("now") LocalDateTime now);

    @Query("""
            SELECT d FROM ExcursionDate d
            WHERE d.excursion.id = :excursionId
              AND d.dateTime > :now
            ORDER BY d.dateTime ASC
            """)
    List<ExcursionDate> findAllUpcoming(@Param("excursionId") Long excursionId,
                                        @Param("now") LocalDateTime now);

    Optional<ExcursionDate> findById(Long id);
}