package ru.kpfu.itis.tourbookingsystemspring.entity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "guide_profiles")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class GuideProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @Column(columnDefinition = "TEXT")
    private String bio;

    @Column(precision = 3, scale = 2)
    private BigDecimal rating;

    @Column(name = "avatar_url", length = 500)
    private String avatarUrl;
}