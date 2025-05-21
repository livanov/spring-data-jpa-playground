INSERT INTO people (id, name, tags) VALUES
(gen_random_uuid(), 'Lyubo', '["student", "immigrant"]'::jsonb),
(gen_random_uuid(), 'Vanko', '["student", "abroad", "another"]'::jsonb),
(gen_random_uuid(), 'Branko', '["abroad","another"]'::jsonb),
(gen_random_uuid(), 'Gosho', '["student"]'::jsonb);

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
((SELECT id FROM people WHERE name = 'Vanko'), (SELECT id FROM subjects WHERE code = 'M1')),
((SELECT id FROM people WHERE name = 'Vanko'), (SELECT id FROM subjects WHERE code = 'P1')),
((SELECT id FROM people WHERE name = 'Branko'), (SELECT id FROM subjects WHERE code = 'M1')),
((SELECT id FROM people WHERE name = 'Branko'), (SELECT id FROM subjects WHERE code = 'P1')),
((SELECT id FROM people WHERE name = 'Gosho'), (SELECT id FROM subjects WHERE code = 'M1'));
