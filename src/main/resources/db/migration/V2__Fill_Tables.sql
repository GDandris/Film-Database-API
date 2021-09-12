INSERT INTO director VALUES (1,'Stanley Kubrick');
INSERT INTO director VALUES (2,'Martin Scorsese');
INSERT INTO director VALUES (3,'Akira Kurosawa');

INSERT INTO film VALUES(1,'2001: A Space Odyssey', 1968, 1);
INSERT INTO film VALUES(2,'The Shining', 1980, 1);
INSERT INTO film VALUES(3,'The Departed', 2006, 2);
INSERT INTO film VALUES(4,'Kagemusha', 1980, 3);

INSERT INTO tag VALUES(1, 'Dark');
INSERT INTO tag VALUES(2, 'Foreign');
INSERT INTO tag VALUES(3, 'Written-Directed');

INSERT INTO film_tags VALUES(1, 3);
INSERT INTO film_tags VALUES(2, 1);
INSERT INTO film_tags VALUES(2, 3);
INSERT INTO film_tags VALUES(3, 1);
INSERT INTO film_tags VALUES(4, 2);
INSERT INTO film_tags VALUES(4, 3);