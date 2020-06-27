package ru.axyv.kafkatemp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.axyv.kafkatemp.model.AnalyticsInfo;
import ru.axyv.kafkatemp.model.Payment;
import ru.axyv.kafkatemp.model.UserAnalytics;

import java.util.Collection;
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
            } else {
                userAnalytics.getAnalyticInfo().put(paymentCategory, updatedAnalyticsInfo(analyticsInfo, payment));
            }
        }
    }

    private AnalyticsInfo updatedAnalyticsInfo(AnalyticsInfo analyticsInfo, Payment payment) {
        AnalyticsInfo updatedAnalyticsInfo = new AnalyticsInfo();
        updatedAnalyticsInfo.setSum(analyticsInfo.getSum().add(payment.getAmount()));
        if (payment.getAmount().compareTo(analyticsInfo.getMin()) < 0) {
            updatedAnalyticsInfo.setMin(payment.getAmount());
        } else {
            updatedAnalyticsInfo.setMin(analyticsInfo.getMin());
        }
        if (payment.getAmount().compareTo(analyticsInfo.getMax()) > 0) {
            updatedAnalyticsInfo.setMax(payment.getAmount());
        } else {
            updatedAnalyticsInfo.setMax(analyticsInfo.getMax());
        }
        return updatedAnalyticsInfo;
    }

    private AnalyticsInfo newAnalyticsInfo(Payment payment) {
        AnalyticsInfo newAnalyticsInfo = new AnalyticsInfo(payment.getAmount(), payment.getAmount(), payment.getAmount());
        return newAnalyticsInfo;
    }

    private UserAnalytics prepareNewUserAnalytics(Payment payment) {
        UserAnalytics newUserAnalytics = new UserAnalytics(payment.getUserId(), payment.getAmount());
        newUserAnalytics.getAnalyticInfo().put(payment.getCategoryId().toString(), newAnalyticsInfo(payment));
        return newUserAnalytics;
    }

}
