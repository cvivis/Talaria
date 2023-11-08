truncate table member;
truncate table key;

INSERT INTO key (created_date, expiration_date, key_value) values
    (CURRENT_DATE, CURRENT_DATE + INTERVAL '1 year', 'ait3Sf8mbTS2AZ0bKrB7EOt2fBZKAc4w');
INSERT INTO key (created_date, expiration_date, key_value) values
    (CURRENT_DATE, CURRENT_DATE + INTERVAL '1 year', 'berkljglkdfgjklfdkjgEOt2wehfskdf');
INSERT INTO key (created_date, expiration_date, key_value) values
    (CURRENT_DATE, CURRENT_DATE + INTERVAL '1 year', 'cerjgidfgjkeriudnfxcjkvlkdjlgdfj');

INSERT INTO member (email, password, created_time, role, key_id) values
    ('admin', 'admin', now() ,'ADMIN', 1);
INSERT INTO member (email, password, created_time, role, key_id) values
    ('developer', 'developer', now(), 'DEVELOPER', 2);
INSERT INTO member (email, password, created_time, role, key_id) values
    ('user', 'user', now(), 'USER', 3);

