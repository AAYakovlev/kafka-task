package ru.axyv.kafkatemp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.axyv.kafkatemp.model.StatusResponse;
import ru.axyv.kafkatemp.model.UserAnalytics;
import ru.axyv.kafkatemp.model.UserStats;
import ru.axyv.kafkatemp.service.AnalyticsService;

@RestController
@RequiredArgsConstructor
public class AnalyticsController {
    private final AnalyticsService analyticsService;


    @GetMapping("/admin/health")
    public ResponseEntity<?> getHeath() {
        return ResponseEntity.ok(new StatusResponse("UP"));
    }

    @GetMapping("/analytic/{userId}")
    public ResponseEntity<?> userAnalytics(@PathVariable String userId) {
        UserAnalytics userAnalytics = analyticsService.getUserAnalytics(userId);
        if (userAnalytics != null) {
            return ResponseEntity.ok(userAnalytics);
        } else {
            return new ResponseEntity<>(new StatusResponse("user not found"), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/analytic/{userId}/stats")
    public ResponseEntity<?> userOftenAnalytics(@PathVariable String userId) {
        UserStats stats = analyticsService.getUserStats(userId);
        if (stats != null) {
            return ResponseEntity.ok(stats);
        } else {
            return new ResponseEntity<>(new StatusResponse("user not found"), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/analytic")
    public ResponseEntity<?> allUserAnalytics() {
        return ResponseEntity.ok(analyticsService.getAllUsersAnalytics());
    }
}
