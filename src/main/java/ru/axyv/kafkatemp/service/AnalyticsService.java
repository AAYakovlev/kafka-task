package ru.axyv.kafkatemp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.axyv.kafkatemp.model.AnalyticsInfo;
import ru.axyv.kafkatemp.model.Payment;
import ru.axyv.kafkatemp.model.UserAnalytics;
import ru.axyv.kafkatemp.model.UserStats;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AnalyticsService {
    private final Map<String, UserAnalytics> allUserAnalytics = new HashMap<>();

    public Collection<UserAnalytics> getAllUsersAnalytics() {
        return allUserAnalytics.values();
    }

    public UserAnalytics getUserAnalytics(String userId) {
        return allUserAnalytics.get(userId);
    }

    public UserStats getUserStats(String userId) {
        UserAnalytics userAnalytics = allUserAnalytics.get(userId);
        if (userAnalytics == null) return null;
        UserStats userStats = userAnalytics.getUserStats();
        userStats.setMinAmountCategoryId(userAnalytics.getAnalyticInfo().entrySet().stream()
                .min(Comparator.comparing(e -> e.getValue().getMin()))
                .map(Map.Entry::getKey)
                .map(Integer::valueOf).orElse(null));

        userStats.setMaxAmountCategoryId(userAnalytics.getAnalyticInfo().entrySet().stream()
                .max(Comparator.comparing(e -> e.getValue().getMax()))
                .map(Map.Entry::getKey)
                .map(Integer::valueOf).orElse(null));

        userStats.setOftenCategoryId(userStats.getCategoriesCount().entrySet().stream()
                .max(Comparator.comparingInt(Map.Entry::getValue))
                .map(Map.Entry::getKey)
                .map(Integer::valueOf).orElse(null));
        userStats.setRareCategoryId(userStats.getCategoriesCount().entrySet().stream()
                .min(Comparator.comparingInt(Map.Entry::getValue))
                .map(Map.Entry::getKey)
                .map(Integer::valueOf).orElse(null));
        return userStats;
    }

    public void addPayment(Payment payment) {
        UserAnalytics userAnalytics = allUserAnalytics.get(payment.getUserId());
        if (userAnalytics == null) {
            allUserAnalytics.put(payment.getUserId(), prepareNewUserAnalytics(payment));
        } else {
            String paymentCategory = payment.getCategoryId().toString();
            userAnalytics.setTotalSum(userAnalytics.getTotalSum().add(payment.getAmount()));
            AnalyticsInfo analyticsInfo = userAnalytics.getAnalyticInfo().get(paymentCategory);
            if (analyticsInfo == null) {
                userAnalytics.getAnalyticInfo().put(paymentCategory, newAnalyticsInfo(payment));
                updateUserStats(userAnalytics.getUserStats(), payment);
            } else {
                userAnalytics.getAnalyticInfo().put(paymentCategory, updatedAnalyticsInfo(analyticsInfo, payment, userAnalytics.getUserStats()));
            }
        }
    }

    private AnalyticsInfo updatedAnalyticsInfo(AnalyticsInfo analyticsInfo, Payment payment, UserStats userStats) {
        AnalyticsInfo updatedAnalyticsInfo = new AnalyticsInfo();
        updatedAnalyticsInfo.setSum(analyticsInfo.getSum().add(payment.getAmount()));
        if (payment.getAmount().compareTo(analyticsInfo.getMin()) < 0) {
            updatedAnalyticsInfo.setMin(payment.getAmount());
            updateMinCat(userStats, payment);
        } else {
            updatedAnalyticsInfo.setMin(analyticsInfo.getMin());
        }
        if (payment.getAmount().compareTo(analyticsInfo.getMax()) > 0) {
            updatedAnalyticsInfo.setMax(payment.getAmount());
            updateMaxCat(userStats, payment);
        } else {
            updatedAnalyticsInfo.setMax(analyticsInfo.getMax());
        }
        updateUserStats(userStats, payment);
        return updatedAnalyticsInfo;
    }

    private AnalyticsInfo newAnalyticsInfo(Payment payment) {
        AnalyticsInfo newAnalyticsInfo = new AnalyticsInfo(payment.getAmount(), payment.getAmount(), payment.getAmount());
        return newAnalyticsInfo;
    }

    private UserAnalytics prepareNewUserAnalytics(Payment payment) {
        UserAnalytics newUserAnalytics = new UserAnalytics(payment.getUserId(), payment.getAmount());
        updateMaxCat(newUserAnalytics.getUserStats(), payment);
        updateMinCat(newUserAnalytics.getUserStats(), payment);
        updateUserStats(newUserAnalytics.getUserStats(), payment);
        newUserAnalytics.getAnalyticInfo().put(payment.getCategoryId().toString(), newAnalyticsInfo(payment));
        return newUserAnalytics;
    }

    private void updateMaxCat(UserStats userStats, Payment payment) {
        userStats.setMaxAmountCategoryId(payment.getCategoryId());
    }

    private void updateMinCat(UserStats userStats, Payment payment) {
        userStats.setMinAmountCategoryId(payment.getCategoryId());
    }

    private void updateUserStats(UserStats userStats, Payment payment) {
        Integer catCount = userStats.getCategoriesCount().get(payment.getCategoryId().toString());
        if (catCount != null) {
            userStats.getCategoriesCount().put(payment.getCategoryId().toString(), catCount + 1);
        } else {
            userStats.getCategoriesCount().put(payment.getCategoryId().toString(), 1);
        }
    }
}
