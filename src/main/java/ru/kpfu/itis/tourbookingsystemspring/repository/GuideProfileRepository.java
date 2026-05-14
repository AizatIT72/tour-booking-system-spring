package ru.kpfu.itis.tourbookingsystemspring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.kpfu.itis.tourbookingsystemspring.entity.GuideProfile;

import java.util.List;
import java.util.Optional;

public interface GuideProfileRepository extends JpaRepository<GuideProfile, Long> {

    @Query("""
            SELECT gp FROM GuideProfile gp
            JOIN FETCH gp.user u
            WHERE u.role = ru.kpfu.itis.tourbookingsystemspring.entity.enums.Role.GUIDE
              AND u.enabled = true
            ORDER BY gp.rating DESC NULLS LAST, u.fullName ASC
            """)
    List<GuideProfile> findAllActive();

    Optional<GuideProfile> findByUserId(Long userId);
}