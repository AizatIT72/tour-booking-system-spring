package ru.kpfu.itis.tourbookingsystemspring.service;

import ru.kpfu.itis.tourbookingsystemspring.dto.GuideListItemDto;

import java.util.List;

public interface SubscriptionService {

    List<GuideListItemDto> getAllGuides(Long currentTouristId);

    List<GuideListItemDto> getMyGuides(Long touristId);

    boolean subscribe(Long touristId, Long guideId);

    boolean unsubscribe(Long touristId, Long guideId);

    boolean isSubscribed(Long touristId, Long guideId);

    long getSubscriberCount(Long guideId);
}