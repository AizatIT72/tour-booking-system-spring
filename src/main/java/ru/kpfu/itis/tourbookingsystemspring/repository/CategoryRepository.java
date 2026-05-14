package ru.kpfu.itis.tourbookingsystemspring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kpfu.itis.tourbookingsystemspring.entity.Category;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    List<Category> findAllByOrderByNameAsc();
}