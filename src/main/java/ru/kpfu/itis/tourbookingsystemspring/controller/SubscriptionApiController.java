package ru.kpfu.itis.tourbookingsystemspring.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.kpfu.itis.tourbookingsystemspring.security.CustomUserDetails;
import ru.kpfu.itis.tourbookingsystemspring.service.SubscriptionService;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/subscriptions")
@RequiredArgsConstructor
public class SubscriptionApiController {

    private final SubscriptionService subscriptionService;

    @PostMapping("/{guideId}")
    @PreAuthorize("hasRole('TOURIST')")
    public ResponseEntity<Map<String, Object>> subscribe(
            @PathVariable Long guideId,
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        log.debug("POST /api/subscriptions/{} by={}", guideId, userDetails.getUsername());

        subscriptionService.subscribe(userDetails.getId(), guideId);

        return ResponseEntity.ok(Map.of(
                "subscribed", true,
                "subscriberCount", subscriptionService.getSubscriberCount(guideId)
        ));
    }

    @DeleteMapping("/{guideId}")
    @PreAuthorize("hasRole('TOURIST')")
    public ResponseEntity<Map<String, Object>> unsubscribe(
            @PathVariable Long guideId,
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        log.debug("DELETE /api/subscriptions/{} by={}", guideId, userDetails.getUsername());

        subscriptionService.unsubscribe(userDetails.getId(), guideId);

        return ResponseEntity.ok(Map.of(
                "subscribed", false,
                "subscriberCount", subscriptionService.getSubscriberCount(guideId)
        ));
    }
}