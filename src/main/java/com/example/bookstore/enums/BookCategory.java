package com.example.bookstore.enums;

/**
 * enum دیگر برای نمایش دسته‌بندی کتاب‌ها
 */
public enum BookCategory {
    FICTION,        // داستانی
    NON_FICTION,    // غیرداستانی
    SCIENCE,        // علمی
    TECHNOLOGY,     // فناوری
    HISTORY,        // تاریخی
    BIOGRAPHY;      // زندگینامه

    public String getPersianName() {
        switch (this) {
            case FICTION: return "داستانی";
            case NON_FICTION: return "غیرداستانی";
            case SCIENCE: return "علمی";
            case TECHNOLOGY: return "فناوری";
            case HISTORY: return "تاریخی";
            case BIOGRAPHY: return "زندگینامه";
            default: return "دسته‌بندی نامشخص";
        }
    }
}