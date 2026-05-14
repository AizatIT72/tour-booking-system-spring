package ru.kpfu.itis.tourbookingsystemspring.entity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "excursion_dates")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class ExcursionDate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "excursion_id", nullable = false)
    private Excursion excursion;

    @Column(name = "date_time", nullable = false)
    private LocalDateTime dateTime;

    @Column(name = "available_slots", nullable = false)
    private Integer availableSlots;

    @Column(name = "is_available", nullable = false)
    private boolean isAvailable = true;
}