package ru.kpfu.itis.tourbookingsystemspring.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kpfu.itis.tourbookingsystemspring.dto.CategoryDto;
import ru.kpfu.itis.tourbookingsystemspring.dto.ExcursionDetailsDto;
import ru.kpfu.itis.tourbookingsystemspring.dto.ExcursionListItemDto;
import ru.kpfu.itis.tourbookingsystemspring.entity.Excursion;
import ru.kpfu.itis.tourbookingsystemspring.entity.ExcursionDate;
import ru.kpfu.itis.tourbookingsystemspring.exception.EntityNotFoundException;
import ru.kpfu.itis.tourbookingsystemspring.form.ExcursionSearchForm;
import ru.kpfu.itis.tourbookingsystemspring.mapper.ExcursionMapper;
import ru.kpfu.itis.tourbookingsystemspring.repository.CategoryRepository;
import ru.kpfu.itis.tourbookingsystemspring.repository.ExcursionDateRepository;
import ru.kpfu.itis.tourbookingsystemspring.repository.ExcursionRepository;
import ru.kpfu.itis.tourbookingsystemspring.service.ExcursionService;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExcursionServiceImpl implements ExcursionService {

    private final ExcursionRepository excursionRepository;
    private final CategoryRepository categoryRepository;
    private final ExcursionDateRepository excursionDateRepository;
    private final ExcursionMapper excursionMapper;

    @Override
    @Transactional(readOnly = true)
    public Page<ExcursionListItemDto> search(ExcursionSearchForm form) {
        log.debug("Searching excursions: q='{}', city='{}', categoryId={}, maxPrice={}, page={}",
                form.getQ(), form.getCity(), form.getCategoryId(), form.getMaxPrice(), form.getPage());
        String text = nullToEmpty(form.getQ());
        String city = nullToEmpty(form.getCity());
        PageRequest pageable = PageRequest.of(
                form.getPage() == null ? 0 : form.getPage(),
                form.getSize() == null ? 12 : form.getSize()
        );
        Page<Excursion> result = excursionRepository.search(
                text, city, form.getCategoryId(), form.getMaxPrice(), pageable
        );
        log.info("Found {} excursions (total: {})",
                result.getNumberOfElements(), result.getTotalElements());
        return result.map(excursionMapper::toListItem);
    }

    @Override
    @Transactional(readOnly = true)
    public ExcursionDetailsDto getDetails(Long id) {
        log.debug("Loading excursion details: id={}", id);
        Excursion excursion = excursionRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Excursion not found: id={}", id);
                    return new EntityNotFoundException("error.excursion.not.found");
                });
        List<ExcursionDate> dates = excursionDateRepository.findAllUpcoming(id, LocalDateTime.now());
        return excursionMapper.toDetails(excursion, dates);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoryDto> getAllCategories() {
        log.debug("Loading all categories");
        return categoryRepository.findAllByOrderByNameAsc().stream()
                .map(excursionMapper::toCategoryDto)
                .toList();
    }

    private String nullToEmpty(String value) {
        return (value == null || value.isBlank()) ? "" : value;
    }
}