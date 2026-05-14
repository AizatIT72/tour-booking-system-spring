package ru.kpfu.itis.tourbookingsystemspring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.kpfu.itis.tourbookingsystemspring.entity.Subscription;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

    Optional<Subscription> findByTouristIdAndGuideId(Long touristId, Long guideId);

    boolean existsByTouristIdAndGuideId(Long touristId, Long guideId);

    @Query("""
            SELECT s.guide.id FROM Subscription s
            WHERE s.tourist.id = :touristId
            """)
    Set<Long> findGuideIdsByTouristId(@Param("touristId") Long touristId);

    @Query("""
            SELECT s FROM Subscription s
            JOIN FETCH s.guide g
            WHERE s.tourist.id = :touristId
            ORDER BY s.subscribedAt DESC
            """)
    List<Subscription> findAllByTouristIdWithGuide(@Param("touristId") Long touristId);

    long countByGuideId(Long guideId);
}