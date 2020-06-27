package ru.axyv.kafkatemp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Payment {
    private String ref;
    private Integer categoryId;
    private String userId;
    private String recipientId;
    private String desc;
    private BigDecimal amount;
}
