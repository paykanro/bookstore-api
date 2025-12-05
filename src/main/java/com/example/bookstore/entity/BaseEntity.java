package com.example.bookstore.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * abstract class: نمی‌توان از آن شیء ساخت و برای ارث‌بری استفاده می‌شود
 * نکته: کلاس‌های abstract می‌توانند متدهای abstract و concrete داشته باشند
 */
@MappedSuperclass
public abstract class BaseEntity {

    // انواع متغیرها در جاوا:
    // instance variables (non-static) - هر شیء کپی خودش را دارد
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;  // protected: در کلاس و زیرکلاس‌ها قابل دسترسی

    // class variables (static) - بین همه اشیاء مشترک است
    private static final String CREATOR = "BOOKSTORE_APP"; // final: مقدار ثابت

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // متدهای getter و setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public static String getCreator() {
        return CREATOR;
    }

    // متدهای lifecycle با استفاده از JPA annotations
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    // abstract method: باید در زیرکلاس‌ها پیاده‌سازی شود
    public abstract String getDisplayName();
}