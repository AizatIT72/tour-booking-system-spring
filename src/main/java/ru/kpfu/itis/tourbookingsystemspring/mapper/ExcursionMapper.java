package ru.kpfu.itis.tourbookingsystemspring.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import ru.kpfu.itis.tourbookingsystemspring.dto.CategoryDto;
import ru.kpfu.itis.tourbookingsystemspring.dto.ExcursionDateDto;
import ru.kpfu.itis.tourbookingsystemspring.dto.ExcursionDetailsDto;
import ru.kpfu.itis.tourbookingsystemspring.dto.ExcursionListItemDto;
import ru.kpfu.itis.tourbookingsystemspring.entity.Category;
import ru.kpfu.itis.tourbookingsystemspring.entity.Excursion;
import ru.kpfu.itis.tourbookingsystemspring.entity.ExcursionDate;

import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface ExcursionMapper {

    @Mapping(target = "shortDescription", source = "description", qualifiedByName = "shortDescription")
    @Mapping(target = "guideId", source = "guide.id")
    @Mapping(target = "guideName", source = "guide.fullName")
    @Mapping(target = "categories", source = "categories", qualifiedByName = "categoryNames")
    ExcursionListItemDto toListItem(Excursion excursion);

    @Mapping(target = "guideId", source = "excursion.guide.id")
    @Mapping(target = "guideName", source = "excursion.guide.fullName")
    @Mapping(target = "guideUsername", source = "excursion.guide.username")
    @Mapping(target = "categories", source = "excursion.categories", qualifiedByName = "categoryNames")
    @Mapping(target = "dates", source = "dates")
    ExcursionDetailsDto toDetails(Excursion excursion, List<ExcursionDate> dates);

    ExcursionDateDto toDateDto(ExcursionDate date);

    CategoryDto toCategoryDto(Category category);

    @Named("categoryNames")
    default List<String> categoryNames(Set<Category> categories) {
        if (categories == null) {
            return List.of();
        }
        return categories.stream()
                .map(Category::getName)
                .sorted()
                .toList();
    }

    @Named("shortDescription")
    default String shortDescription(String text) {
        if (text == null || text.isBlank()) {
            return "";
        }
        return text.length() > 150 ? text.substring(0, 150) + "..." : text;
    }
}