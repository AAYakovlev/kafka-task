package ru.axyv.kafkatemp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserStats {

    private Integer oftenCategoryId;
    private Integer rareCategoryId;
    private Integer maxAmountCategoryId;
    private Integer minAmountCategoryId;

    @JsonIgnore
    private Map<String, Integer> categoriesCount = new HashMap<>();
}
