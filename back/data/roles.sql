INSERT INTO role (id, nom) VALUES (1, 'RH');
INSERT INTO role (id, nom) VALUES (2, 'Chef de Projet');
SELECT setval('role_id_seq', (SELECT MAX(id) FROM role) + 1);