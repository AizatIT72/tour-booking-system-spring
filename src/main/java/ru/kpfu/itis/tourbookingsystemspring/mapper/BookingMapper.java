package ru.kpfu.itis.tourbookingsystemspring.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.kpfu.itis.tourbookingsystemspring.dto.BookingDto;
import ru.kpfu.itis.tourbookingsystemspring.entity.Booking;

@Mapper(componentModel = "spring")
public interface BookingMapper {

    @Mapping(target = "excursionId", source = "excursionDate.excursion.id")
    @Mapping(target = "excursionTitle", source = "excursionDate.excursion.title")
    @Mapping(target = "guideName", source = "excursionDate.excursion.guide.fullName")
    @Mapping(target = "city", source = "excursionDate.excursion.city")
    @Mapping(target = "dateTime", source = "excursionDate.dateTime")
    BookingDto toDto(Booking booking);
}