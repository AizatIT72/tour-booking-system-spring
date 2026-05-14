package ru.kpfu.itis.tourbookingsystemspring.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.kpfu.itis.tourbookingsystemspring.entity.Excursion;

import java.math.BigDecimal;
import java.util.List;

public interface ExcursionRepository extends JpaRepository<Excursion, Long> {

    @Query("""
            SELECT DISTINCT e FROM Excursion e
            LEFT JOIN e.categories c
            WHERE (:text = '' OR LOWER(e.title) LIKE LOWER(CONCAT('%', :text, '%'))
                              OR LOWER(e.description) LIKE LOWER(CONCAT('%', :text, '%')))
              AND (:city = '' OR LOWER(e.city) LIKE LOWER(CONCAT('%', :city, '%')))
              AND (:categoryId IS NULL OR c.id = :categoryId)
              AND (:maxPrice IS NULL OR e.price <= :maxPrice)
            ORDER BY e.createdAt DESC
            """)
    Page<Excursion> search(@Param("text") String text,
                           @Param("city") String city,
                           @Param("categoryId") Long categoryId,
                           @Param("maxPrice") BigDecimal maxPrice,
                           Pageable pageable);

    List<Excursion> findAllByGuideIdOrderByCreatedAtDesc(Long guideId);
}