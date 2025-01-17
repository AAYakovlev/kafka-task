package ru.axyv.kafkatemp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserAnalytics {
    private String userId;
    private BigDecimal totalSum;
    private final Map<String, AnalyticsInfo> analyticInfo = new HashMap<>();
    @JsonIgnore
    private final UserStats userStats = new UserStats();
}
