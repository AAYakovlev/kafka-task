package ru.axyv.kafkatemp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.axyv.kafkatemp.model.StatusResponse;
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
        return ResponseEntity.ok(analyticsService.getUserAnalytics(userId));
    }

    @GetMapping("/analytic")
    public ResponseEntity<?> allUserAnalytics() {
        return ResponseEntity.ok(analyticsService.getAllUsersAnalytics());
    }
}
