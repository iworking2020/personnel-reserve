MERGE INTO prof_field (id, name_to_system) VALUES (1, 'SALES');
MERGE INTO prof_field (id, name_to_system) VALUES (2, 'PRODUCATION');
MERGE INTO prof_field (id, name_to_system) VALUES (3, 'WORKING_STAFF');
MERGE INTO prof_field (id, name_to_system) VALUES (4, 'IT');
MERGE INTO prof_field (id, name_to_system) VALUES (5, 'CARIER_START');
MERGE INTO prof_field (id, name_to_system) VALUES (6, 'BUILDING');
MERGE INTO prof_field (id, name_to_system) VALUES (7, 'ADMINISTRATION');
MERGE INTO prof_field (id, name_to_system) VALUES (8, 'BANKS');
MERGE INTO prof_field (id, name_to_system) VALUES (9, 'TRANSPORT');
MERGE INTO prof_field (id, name_to_system) VALUES (10, 'ACCOUNTING');
MERGE INTO prof_field (id, name_to_system) VALUES (11, 'MARKETING');
MERGE INTO prof_field (id, name_to_system) VALUES (12, 'TOURISM');
MERGE INTO prof_field (id, name_to_system) VALUES (13, 'RESTAURANTS');
MERGE INTO prof_field (id, name_to_system) VALUES (14, 'MEDICINE');
MERGE INTO prof_field (id, name_to_system) VALUES (15, 'COUNSELING');
MERGE INTO prof_field (id, name_to_system) VALUES (16, 'CARS');
MERGE INTO prof_field (id, name_to_system) VALUES (17, 'TOP_MANAGEMENT');
MERGE INTO prof_field (id, name_to_system) VALUES (18, 'HUMAN_RESOURCES');
MERGE INTO prof_field (id, name_to_system) VALUES (19, 'SECURITY');
MERGE INTO prof_field (id, name_to_system) VALUES (20, 'PROCUREMENT');
MERGE INTO prof_field (id, name_to_system) VALUES (21, 'ART');
MERGE INTO prof_field (id, name_to_system) VALUES (22, 'MEDIA');
MERGE INTO prof_field (id, name_to_system) VALUES (23, 'SCIENCE');
MERGE INTO prof_field (id, name_to_system) VALUES (24, 'EDUCATION');
MERGE INTO prof_field (id, name_to_system) VALUES (25, 'JURISPRUDENCE');
MERGE INTO prof_field (id, name_to_system) VALUES (26, 'INSTALLATION');
MERGE INTO prof_field (id, name_to_system) VALUES (27, 'SPORT');
MERGE INTO prof_field (id, name_to_system) VALUES (28, 'INSURANCE');
MERGE INTO prof_field (id, name_to_system) VALUES (29, 'HOME_STAFF');
MERGE INTO prof_field (id, name_to_system) VALUES (30, 'CIVIL_SERVICE');
MERGE INTO prof_field (id, name_to_system) VALUES (31, 'MATERIAL_EXTRACTION');

MERGE INTO prof_field_names_to_view (prof_field_id, names_to_view, names_to_view_key) VALUES (1, 'продажи', 'ru_RU');
MERGE INTO prof_field_names_to_view (prof_field_id, names_to_view, names_to_view_key) VALUES (2, 'производство', 'ru_RU');
MERGE INTO prof_field_names_to_view (prof_field_id, names_to_view, names_to_view_key) VALUES (3, 'раб. персонал', 'ru_RU');
MERGE INTO prof_field_names_to_view (prof_field_id, names_to_view, names_to_view_key) VALUES (4, 'IT, телеком', 'ru_RU');
MERGE INTO prof_field_names_to_view (prof_field_id, names_to_view, names_to_view_key) VALUES (5, 'начало карьеры', 'ru_RU');
MERGE INTO prof_field_names_to_view (prof_field_id, names_to_view, names_to_view_key) VALUES (6, 'строительство', 'ru_RU');
MERGE INTO prof_field_names_to_view (prof_field_id, names_to_view, names_to_view_key) VALUES (7, 'адм. персонал', 'ru_RU');
MERGE INTO prof_field_names_to_view (prof_field_id, names_to_view, names_to_view_key) VALUES (8, 'банки', 'ru_RU');
MERGE INTO prof_field_names_to_view (prof_field_id, names_to_view, names_to_view_key) VALUES (9, 'транспорт', 'ru_RU');
MERGE INTO prof_field_names_to_view (prof_field_id, names_to_view, names_to_view_key) VALUES (10, 'бухгалтерия', 'ru_RU');
MERGE INTO prof_field_names_to_view (prof_field_id, names_to_view, names_to_view_key) VALUES (11, 'маркетинг', 'ru_RU');
MERGE INTO prof_field_names_to_view (prof_field_id, names_to_view, names_to_view_key) VALUES (12, 'туризм', 'ru_RU');
MERGE INTO prof_field_names_to_view (prof_field_id, names_to_view, names_to_view_key) VALUES (13, 'рестораны', 'ru_RU');
MERGE INTO prof_field_names_to_view (prof_field_id, names_to_view, names_to_view_key) VALUES (14, 'медицина', 'ru_RU');
MERGE INTO prof_field_names_to_view (prof_field_id, names_to_view, names_to_view_key) VALUES (15, 'консультирование', 'ru_RU');
MERGE INTO prof_field_names_to_view (prof_field_id, names_to_view, names_to_view_key) VALUES (16, 'авто', 'ru_RU');
MERGE INTO prof_field_names_to_view (prof_field_id, names_to_view, names_to_view_key) VALUES (17, 'топ-менеджмент', 'ru_RU');
MERGE INTO prof_field_names_to_view (prof_field_id, names_to_view, names_to_view_key) VALUES (18, 'управление персоналом', 'ru_RU');
MERGE INTO prof_field_names_to_view (prof_field_id, names_to_view, names_to_view_key) VALUES (19, 'безопасность', 'ru_RU');
MERGE INTO prof_field_names_to_view (prof_field_id, names_to_view, names_to_view_key) VALUES (20, 'закупки', 'ru_RU');
MERGE INTO prof_field_names_to_view (prof_field_id, names_to_view, names_to_view_key) VALUES (21, 'искусство', 'ru_RU');
MERGE INTO prof_field_names_to_view (prof_field_id, names_to_view, names_to_view_key) VALUES (22, 'медиа', 'ru_RU');
MERGE INTO prof_field_names_to_view (prof_field_id, names_to_view, names_to_view_key) VALUES (23, 'наука', 'ru_RU');
MERGE INTO prof_field_names_to_view (prof_field_id, names_to_view, names_to_view_key) VALUES (24, 'образование', 'ru_RU');
MERGE INTO prof_field_names_to_view (prof_field_id, names_to_view, names_to_view_key) VALUES (25, 'юриспруденция', 'ru_RU');
MERGE INTO prof_field_names_to_view (prof_field_id, names_to_view, names_to_view_key) VALUES (26, 'инсталляция', 'ru_RU');
MERGE INTO prof_field_names_to_view (prof_field_id, names_to_view, names_to_view_key) VALUES (27, 'спорт', 'ru_RU');
MERGE INTO prof_field_names_to_view (prof_field_id, names_to_view, names_to_view_key) VALUES (28, 'страхование', 'ru_RU');
MERGE INTO prof_field_names_to_view (prof_field_id, names_to_view, names_to_view_key) VALUES (29, 'дом. персонал', 'ru_RU');
MERGE INTO prof_field_names_to_view (prof_field_id, names_to_view, names_to_view_key) VALUES (30, 'госслужба', 'ru_RU');
MERGE INTO prof_field_names_to_view (prof_field_id, names_to_view, names_to_view_key) VALUES (31, 'добыча сырья', 'ru_RU');

MERGE INTO prof_field_names_to_view (prof_field_id, names_to_view, names_to_view_key) VALUES (1, 'sales', 'en');
MERGE INTO prof_field_names_to_view (prof_field_id, names_to_view, names_to_view_key) VALUES (2, 'production', 'en');
MERGE INTO prof_field_names_to_view (prof_field_id, names_to_view, names_to_view_key) VALUES (3, 'working staff', 'en');
MERGE INTO prof_field_names_to_view (prof_field_id, names_to_view, names_to_view_key) VALUES (4, 'IT', 'en');
MERGE INTO prof_field_names_to_view (prof_field_id, names_to_view, names_to_view_key) VALUES (5, 'carier start', 'en');
MERGE INTO prof_field_names_to_view (prof_field_id, names_to_view, names_to_view_key) VALUES (6, 'building', 'en');
MERGE INTO prof_field_names_to_view (prof_field_id, names_to_view, names_to_view_key) VALUES (7, 'administration', 'en');
MERGE INTO prof_field_names_to_view (prof_field_id, names_to_view, names_to_view_key) VALUES (8, 'banks', 'en');
MERGE INTO prof_field_names_to_view (prof_field_id, names_to_view, names_to_view_key) VALUES (9, 'transport', 'en');
MERGE INTO prof_field_names_to_view (prof_field_id, names_to_view, names_to_view_key) VALUES (10, 'accounting', 'en');
MERGE INTO prof_field_names_to_view (prof_field_id, names_to_view, names_to_view_key) VALUES (11, 'marketing', 'en');
MERGE INTO prof_field_names_to_view (prof_field_id, names_to_view, names_to_view_key) VALUES (12, 'tourism', 'en');
MERGE INTO prof_field_names_to_view (prof_field_id, names_to_view, names_to_view_key) VALUES (13, 'restaurants', 'en');
MERGE INTO prof_field_names_to_view (prof_field_id, names_to_view, names_to_view_key) VALUES (14, 'medicine', 'en');
MERGE INTO prof_field_names_to_view (prof_field_id, names_to_view, names_to_view_key) VALUES (15, 'counseling', 'en');
MERGE INTO prof_field_names_to_view (prof_field_id, names_to_view, names_to_view_key) VALUES (16, 'cars', 'en');
MERGE INTO prof_field_names_to_view (prof_field_id, names_to_view, names_to_view_key) VALUES (17, 'top management', 'en');
MERGE INTO prof_field_names_to_view (prof_field_id, names_to_view, names_to_view_key) VALUES (18, 'human resources', 'en');
MERGE INTO prof_field_names_to_view (prof_field_id, names_to_view, names_to_view_key) VALUES (19, 'security', 'en');
MERGE INTO prof_field_names_to_view (prof_field_id, names_to_view, names_to_view_key) VALUES (20, 'procurement', 'en');
MERGE INTO prof_field_names_to_view (prof_field_id, names_to_view, names_to_view_key) VALUES (21, 'art', 'en');
MERGE INTO prof_field_names_to_view (prof_field_id, names_to_view, names_to_view_key) VALUES (22, 'media', 'en');
MERGE INTO prof_field_names_to_view (prof_field_id, names_to_view, names_to_view_key) VALUES (23, 'science', 'en');
MERGE INTO prof_field_names_to_view (prof_field_id, names_to_view, names_to_view_key) VALUES (24, 'education', 'en');
MERGE INTO prof_field_names_to_view (prof_field_id, names_to_view, names_to_view_key) VALUES (25, 'jurisprudence', 'en');
MERGE INTO prof_field_names_to_view (prof_field_id, names_to_view, names_to_view_key) VALUES (26, 'installation', 'en');
MERGE INTO prof_field_names_to_view (prof_field_id, names_to_view, names_to_view_key) VALUES (27, 'sport', 'en');
MERGE INTO prof_field_names_to_view (prof_field_id, names_to_view, names_to_view_key) VALUES (28, 'insurance', 'en');
MERGE INTO prof_field_names_to_view (prof_field_id, names_to_view, names_to_view_key) VALUES (29, 'home staff', 'en');
MERGE INTO prof_field_names_to_view (prof_field_id, names_to_view, names_to_view_key) VALUES (30, 'civil service', 'en');
MERGE INTO prof_field_names_to_view (prof_field_id, names_to_view, names_to_view_key) VALUES (31, 'material extraction', 'en');

MERGE INTO work_type (id, name_to_system) VALUES (1, 'FULL_DAY');
MERGE INTO work_type (id, name_to_system) VALUES (2, 'SWIFT_WORK');
MERGE INTO work_type (id, name_to_system) VALUES (3, 'TOUR_WORK');
MERGE INTO work_type (id, name_to_system) VALUES (4, 'FLEX_WORK');
MERGE INTO work_type (id, name_to_system) VALUES (5, 'REMOTE_WORK');

MERGE INTO work_type_names_to_view (work_type_id, names_to_view, names_to_view_key) VALUES (1, 'полный день', 'ru_RU');
MERGE INTO work_type_names_to_view (work_type_id, names_to_view, names_to_view_key) VALUES (2, 'сменный график', 'ru_RU');
MERGE INTO work_type_names_to_view (work_type_id, names_to_view, names_to_view_key) VALUES (3, 'вахтовый метод', 'ru_RU');
MERGE INTO work_type_names_to_view (work_type_id, names_to_view, names_to_view_key) VALUES (4, 'гибкий график', 'ru_RU');
MERGE INTO work_type_names_to_view (work_type_id, names_to_view, names_to_view_key) VALUES (5, 'удаленная работа', 'ru_RU');

MERGE INTO work_type_names_to_view (work_type_id, names_to_view, names_to_view_key) VALUES (1, 'full day', 'en');
MERGE INTO work_type_names_to_view (work_type_id, names_to_view, names_to_view_key) VALUES (2, 'shift work', 'en');
MERGE INTO work_type_names_to_view (work_type_id, names_to_view, names_to_view_key) VALUES (3, 'tour work', 'en');
MERGE INTO work_type_names_to_view (work_type_id, names_to_view, names_to_view_key) VALUES (4, 'flexible working', 'en');
MERGE INTO work_type_names_to_view (work_type_id, names_to_view, names_to_view_key) VALUES (5, 'remote work', 'en');

MERGE INTO education (id, name_to_system) VALUES (1, 'WITHOUT');
MERGE INTO education (id, name_to_system) VALUES (2, 'SECONDARY');
MERGE INTO education (id, name_to_system) VALUES (3, 'HIGHER');
MERGE INTO education (id, name_to_system) VALUES (4, 'POSTGRADUATE');

MERGE INTO education_names_to_view (education_id, names_to_view, names_to_view_key) VALUES (1, 'без образования', 'ru_RU');
MERGE INTO education_names_to_view (education_id, names_to_view, names_to_view_key) VALUES (2, 'среднее', 'ru_RU');
MERGE INTO education_names_to_view (education_id, names_to_view, names_to_view_key) VALUES (3, 'высшее', 'ru_RU');
MERGE INTO education_names_to_view (education_id, names_to_view, names_to_view_key) VALUES (4, 'послевузовское', 'ru_RU');

MERGE INTO education_names_to_view (education_id, names_to_view, names_to_view_key) VALUES ('1', 'without education', 'en');
MERGE INTO education_names_to_view (education_id, names_to_view, names_to_view_key) VALUES ('2', 'secondary', 'en');
MERGE INTO education_names_to_view (education_id, names_to_view, names_to_view_key) VALUES ('3', 'heigher', 'en');
MERGE INTO education_names_to_view (education_id, names_to_view, names_to_view_key) VALUES ('4', 'postgraduate', 'en');

MERGE INTO currency (id, name_to_system) VALUES (1, 'RUB');
MERGE INTO currency (id, name_to_system) VALUES (2, 'EURO');
MERGE INTO currency (id, name_to_system) VALUES (3, 'USD');

MERGE INTO currency_names_to_view (currency_id, names_to_view, names_to_view_key) VALUES (1, 'руб.', 'ru_RU');
MERGE INTO currency_names_to_view (currency_id, names_to_view, names_to_view_key) VALUES (2, 'евро', 'ru_RU');
MERGE INTO currency_names_to_view (currency_id, names_to_view, names_to_view_key) VALUES (3, 'долл.', 'ru_RU');

MERGE INTO currency_names_to_view (currency_id, names_to_view, names_to_view_key) VALUES (1, 'rub', 'en');
MERGE INTO currency_names_to_view (currency_id, names_to_view, names_to_view_key) VALUES (2, 'euro', 'en');
MERGE INTO currency_names_to_view (currency_id, names_to_view, names_to_view_key) VALUES (3, 'usd', 'en');

MERGE INTO period (id, name_to_system) VALUES (1, 'MONTH');
MERGE INTO period (id, name_to_system) VALUES (2, 'DAY');
MERGE INTO period (id, name_to_system) VALUES (3, 'HOUR');

MERGE INTO period_names_to_view (period_id, names_to_view, names_to_view_key) VALUES (1, 'в месяц', 'ru_RU');
MERGE INTO period_names_to_view (period_id, names_to_view, names_to_view_key) VALUES (2, 'в день', 'ru_RU');
MERGE INTO period_names_to_view (period_id, names_to_view, names_to_view_key) VALUES (3, 'в час', 'ru_RU');

MERGE INTO period_names_to_view (period_id, names_to_view, names_to_view_key) VALUES (1, 'in month', 'en');
MERGE INTO period_names_to_view (period_id, names_to_view, names_to_view_key) VALUES (2, 'in day', 'en');
MERGE INTO period_names_to_view (period_id, names_to_view, names_to_view_key) VALUES (3, 'in hour', 'en');

MERGE INTO gender (id, name_to_system) VALUES (1, 'MALE');
MERGE INTO gender (id, name_to_system) VALUES (2, 'FEMALE');

MERGE INTO gender_names_to_view (gender_id, names_to_view, names_to_view_key) VALUES (1, 'мужской', 'ru_RU');
MERGE INTO gender_names_to_view (gender_id, names_to_view, names_to_view_key) VALUES (2, 'женский', 'ru_RU');

MERGE INTO gender_names_to_view (gender_id, names_to_view, names_to_view_key) VALUES (1, 'male', 'en');
MERGE INTO gender_names_to_view (gender_id, names_to_view, names_to_view_key) VALUES (2, 'female', 'en');