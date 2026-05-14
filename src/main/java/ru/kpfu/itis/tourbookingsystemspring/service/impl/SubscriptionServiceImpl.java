package ru.kpfu.itis.tourbookingsystemspring.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kpfu.itis.tourbookingsystemspring.dto.GuideListItemDto;
import ru.kpfu.itis.tourbookingsystemspring.entity.GuideProfile;
import ru.kpfu.itis.tourbookingsystemspring.entity.Notification;
import ru.kpfu.itis.tourbookingsystemspring.entity.Subscription;
import ru.kpfu.itis.tourbookingsystemspring.entity.User;
import ru.kpfu.itis.tourbookingsystemspring.entity.enums.NotificationType;
import ru.kpfu.itis.tourbookingsystemspring.exception.EntityNotFoundException;
import ru.kpfu.itis.tourbookingsystemspring.exception.ValidationException;
import ru.kpfu.itis.tourbookingsystemspring.repository.GuideProfileRepository;
import ru.kpfu.itis.tourbookingsystemspring.repository.NotificationRepository;
import ru.kpfu.itis.tourbookingsystemspring.repository.SubscriptionRepository;
import ru.kpfu.itis.tourbookingsystemspring.repository.UserRepository;
import ru.kpfu.itis.tourbookingsystemspring.service.SubscriptionService;

import java.util.List;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService {

    private final GuideProfileRepository guideProfileRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final UserRepository userRepository;
    private final NotificationRepository notificationRepository;

    @Override
    @Transactional(readOnly = true)
    public List<GuideListItemDto> getAllGuides(Long currentTouristId) {
        log.debug("Loading all guides for tourist={}", currentTouristId);
        List<GuideProfile> profiles = guideProfileRepository.findAllActive();
        Set<Long> subscribedIds = (currentTouristId != null)
                ? subscriptionRepository.findGuideIdsByTouristId(currentTouristId)
                : Set.of();
        return profiles.stream()
                .map(gp -> toDto(gp, subscribedIds))
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<GuideListItemDto> getMyGuides(Long touristId) {
        log.debug("Loading subscriptions for tourist={}", touristId);
        Set<Long> subscribedIds = subscriptionRepository.findGuideIdsByTouristId(touristId);
        return subscriptionRepository.findAllByTouristIdWithGuide(touristId).stream()
                .map(s -> {
                    GuideProfile gp = guideProfileRepository.findByUserId(s.getGuide().getId())
                            .orElse(null);
                    return toDto(s.getGuide(), gp, subscribedIds);
                })
                .toList();
    }

    @Override
    @Transactional
    public boolean subscribe(Long touristId, Long guideId) {
        log.info("Subscribe: tourist={}, guide={}", touristId, guideId);

        if (touristId.equals(guideId)) {
            throw new ValidationException("error.subscription.self");
        }

        if (subscriptionRepository.existsByTouristIdAndGuideId(touristId, guideId)) {
            log.debug("Already subscribed: tourist={}, guide={}", touristId, guideId);
            return false;
        }

        User tourist = userRepository.findById(touristId)
                .orElseThrow(() -> new EntityNotFoundException("error.user.not.found"));
        User guide = userRepository.findById(guideId)
                .orElseThrow(() -> new EntityNotFoundException("error.user.not.found"));

        Subscription subscription = new Subscription();
        subscription.setTourist(tourist);
        subscription.setGuide(guide);
        subscriptionRepository.save(subscription);

        Notification notification = Notification.builder()
                .recipient(guide)
                .title("Новый подписчик")
                .message(String.format("%s подписался на вас", tourist.getFullName()))
                .type(NotificationType.NEW_SUBSCRIBER)
                .isRead(false)
                .build();
        notificationRepository.save(notification);

        log.info("Subscribed: tourist={}, guide={}", touristId, guideId);
        return true;
    }

    @Override
    @Transactional
    public boolean unsubscribe(Long touristId, Long guideId) {
        log.info("Unsubscribe: tourist={}, guide={}", touristId, guideId);

        return subscriptionRepository.findByTouristIdAndGuideId(touristId, guideId)
                .map(sub -> {
                    subscriptionRepository.delete(sub);
                    log.info("Unsubscribed: tourist={}, guide={}", touristId, guideId);
                    return true;
                })
                .orElse(false);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isSubscribed(Long touristId, Long guideId) {
        return subscriptionRepository.existsByTouristIdAndGuideId(touristId, guideId);
    }

    @Override
    @Transactional(readOnly = true)
    public long getSubscriberCount(Long guideId) {
        return subscriptionRepository.countByGuideId(guideId);
    }

    private GuideListItemDto toDto(GuideProfile gp, Set<Long> subscribedIds) {
        return toDto(gp.getUser(), gp, subscribedIds);
    }

    private GuideListItemDto toDto(User guide, GuideProfile gp, Set<Long> subscribedIds) {
        return new GuideListItemDto(
                guide.getId(),
                guide.getFullName(),
                guide.getUsername(),
                gp != null ? gp.getBio() : null,
                gp != null ? gp.getRating() : null,
                subscriptionRepository.countByGuideId(guide.getId()),
                subscribedIds.contains(guide.getId())
        );
    }
}