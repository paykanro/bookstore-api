package com.example.bookstore.enums;

/**
 * enum: یک نوع داده خاص که مجموعه‌ای از مقادیر ثابت را تعریف می‌کند
 * نکته: enumها برای تعریف مقادیر محدود و مشخص استفاده می‌شوند
 */
public enum BookStatus {
    AVAILABLE,      // کتاب موجود است
    BORROWED,       // کتاب امانت داده شده
    RESERVED,       // کتاب رزرو شده
    UNAVAILABLE;    // کتاب موجود نیست

    // متد instance در enum - نمایش polymorphism
    public String getStatusDescription() {
        switch (this) {
            case AVAILABLE:
                return "کتاب برای امانت موجود است";
            case BORROWED:
                return "کتاب در حال حاضر امانت داده شده";
            case RESERVED:
                return "کتاب رزرو شده است";
            case UNAVAILABLE:
                return "کتاب موجود نیست";
            default:
                return "وضعیت نامشخص";
        }
    }
}