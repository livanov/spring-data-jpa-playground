INSERT INTO people (id, name) VALUES
(gen_random_uuid(), 'Lyubo'),
(gen_random_uuid(), 'Gosho');

INSERT INTO subjects (id, code) VALUES
(gen_random_uuid(), 'M1'),
(gen_random_uuid(), 'P1');

INSERT INTO subject_names (subject_id, value, language_iso_alpha2) VALUES
((SELECT id FROM subjects WHERE code = 'M1'), 'Mathematics', 'en'),
((SELECT id FROM subjects WHERE code = 'M1'), 'Математика', 'bg'),
((SELECT id FROM subjects WHERE code = 'P1'), 'Physics', 'en'),
((SELECT id FROM subjects WHERE code = 'P1'), 'Физика', 'bg');

INSERT INTO people_subjects (person_id, subject_id) VALUES
((SELECT id FROM people WHERE name = 'Lyubo'), (SELECT id FROM subjects WHERE code = 'M1')),
((SELECT id FROM people WHERE name = 'Lyubo'), (SELECT id FROM subjects WHERE code = 'P1')),
((SELECT id FROM people WHERE name = 'Gosho'), (SELECT id FROM subjects WHERE code = 'M1'));
