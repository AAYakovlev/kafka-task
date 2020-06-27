package ru.axyv.kafkatemp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnalyticsInfo {
    private BigDecimal min;
    private BigDecimal max;
    private BigDecimal sum;
}
