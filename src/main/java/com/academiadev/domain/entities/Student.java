package com.academiadev.domain.entities;

import com.academiadev.domain.enums.SubscriptionPlan;

public class Student extends User {
    private SubscriptionPlan subscriptionPlan;
    private int activeEnrollmentsCount = 0; 

    public Student(String name, String email, SubscriptionPlan subscriptionPlan) {
        super(name, email);
        this.subscriptionPlan = subscriptionPlan;
    }

    public boolean canEnroll() {
        if (this.subscriptionPlan == SubscriptionPlan.PREMIUM) return true;
        return this.activeEnrollmentsCount < 3;
    }

    public void incrementEnrollments() { this.activeEnrollmentsCount++; }
    public void decrementEnrollments() { if (activeEnrollmentsCount > 0) activeEnrollmentsCount--; }
    
    public SubscriptionPlan getSubscriptionPlan() { return subscriptionPlan; }
}