package ru.kpfu.itis.tourbookingsystemspring.service;

import org.springframework.data.domain.Page;
import ru.kpfu.itis.tourbookingsystemspring.dto.CategoryDto;
import ru.kpfu.itis.tourbookingsystemspring.dto.ExcursionDetailsDto;
import ru.kpfu.itis.tourbookingsystemspring.dto.ExcursionListItemDto;
import ru.kpfu.itis.tourbookingsystemspring.form.ExcursionSearchForm;

import java.util.List;

public interface ExcursionService {

    Page<ExcursionListItemDto> search(ExcursionSearchForm form);

    ExcursionDetailsDto getDetails(Long id);

    List<CategoryDto> getAllCategories();
}