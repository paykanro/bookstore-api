-- داده‌های اولیه برای testing
INSERT INTO authors (first_name, last_name, email, age, created_at, updated_at) VALUES
('احمد', 'محمدی', 'ahmad.mohammadi@example.com', 35, NOW(), NOW()),
('فاطمه', 'کریمی', 'fatemeh.karimi@example.com', 28, NOW(), NOW()),
('محمد', 'جعفری', 'mohammad.jafari@example.com', 42, NOW(), NOW()),
('زهرا', 'حسینی', 'zahra.hosseini@example.com', 31, NOW(), NOW());

INSERT INTO books (title, isbn, category, status, price, page_count, publication_date, author_id, created_at, updated_at) VALUES
('آموزش Spring Boot', '9781234567890', 'TECHNOLOGY', 'AVAILABLE', 45.50, 320, '2023-01-15', 1, NOW(), NOW()),
('داستان‌های کوتاه', '9781234567891', 'FICTION', 'BORROWED', 25.00, 200, '2022-05-20', 2, NOW(), NOW()),
('تاریخ ایران باستان', '9781234567892', 'HISTORY', 'AVAILABLE', 60.00, 450, '2021-11-30', 3, NOW(), NOW()),
('زندگینامه آلبرت انیشتین', '9781234567893', 'BIOGRAPHY', 'RESERVED', 35.75, 280, '2023-03-10', 4, NOW(), NOW()),
('برنامه‌نویسی جاوا', '9781234567894', 'TECHNOLOGY', 'AVAILABLE', 55.25, 400, '2022-08-25', 1, NOW(), NOW()),
('رمان عاشقانه', '9781234567895', 'FICTION', 'AVAILABLE', 30.00, 350, '2023-02-14', 2, NOW(), NOW());