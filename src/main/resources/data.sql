-- Test verisi ekleme

-- Önce enum değerlerini ekleyelim
-- MovieGenre enum değerleri
-- AwardTitle enum değerleri  
-- AwardCategory enum değerleri

-- Directors (Yönetmenler)
INSERT INTO director (id, name, image_url) VALUES 
(1, 'Christopher Nolan', 'https://example.com/nolan.jpg'),
(2, 'Quentin Tarantino', 'https://example.com/tarantino.jpg'),
(3, 'Steven Spielberg', 'https://example.com/spielberg.jpg'),
(4, 'Martin Scorsese', 'https://example.com/scorsese.jpg'),
(5, 'James Cameron', 'https://example.com/cameron.jpg');

-- Actors (Oyuncular)
INSERT INTO actor (id, name, image_url) VALUES 
(1, 'Leonardo DiCaprio', 'https://example.com/dicaprio.jpg'),
(2, 'Tom Hanks', 'https://example.com/hanks.jpg'),
(3, 'Brad Pitt', 'https://example.com/pitt.jpg'),
(4, 'Meryl Streep', 'https://example.com/streep.jpg'),
(5, 'Robert De Niro', 'https://example.com/deniro.jpg'),
(6, 'Al Pacino', 'https://example.com/pacino.jpg'),
(7, 'Kate Winslet', 'https://example.com/winslet.jpg'),
(8, 'Samuel L. Jackson', 'https://example.com/jackson.jpg');

-- Awards (Ödüller)
INSERT INTO award (id, name, award_year, category) VALUES 
(1, 'OSCAR', 2010, 'BEST_PICTURE'),
(2, 'OSCAR', 2010, 'BEST_DIRECTOR'),
(3, 'OSCAR', 2010, 'BEST_ACTOR'),
(4, 'GOLDEN_GLOBE', 2010, 'BEST_PICTURE'),
(5, 'BAFTA', 2010, 'BEST_ACTOR'),
(6, 'OSCAR', 1997, 'BEST_PICTURE'),
(7, 'OSCAR', 1997, 'BEST_DIRECTOR'),
(8, 'OSCAR', 1997, 'BEST_ACTOR');

-- Movies (Filmler)
INSERT INTO movie (id, title, release_year, synopsis, budget, box_office, imdb_rating, part_of_series, series_name, duration, image_url) VALUES 
(1, 'Inception', 2010, 'Rüya içinde rüya konseptini işleyen bilim kurgu filmi', 160000000, 836836967, 8.8, false, null, 148, 'https://example.com/inception.jpg'),
(2, 'Titanic', 1997, 'Titanic gemisinin batışını konu alan romantik drama', 200000000, 2201647264, 7.9, false, null, 194, 'https://example.com/titanic.jpg'),
(3, 'Pulp Fiction', 1994, 'Los Angeles suç dünyasını anlatan kült film', 8000000, 213928762, 8.9, false, null, 154, 'https://example.com/pulpfiction.jpg'),
(4, 'The Godfather', 1972, 'Corleone ailesinin mafya dünyasındaki hikayesi', 6000000, 245066411, 9.2, true, 'The Godfather', 175, 'https://example.com/godfather.jpg'),
(5, 'Jurassic Park', 1993, 'Dinozorların canlandırıldığı tema parkı', 63000000, 1037688000, 8.5, true, 'Jurassic Park', 127, 'https://example.com/jurassicpark.jpg');

-- Movie Genres (Film Türleri)
INSERT INTO movie_genres (movie_id, genre) VALUES 
(1, 'SCIENCE_FICTION'),
(1, 'THRILLER'),
(1, 'ACTION'),
(2, 'ROMANCE'),
(2, 'DRAMA'),
(2, 'DISASTER'),
(3, 'CRIME'),
(3, 'DRAMA'),
(4, 'CRIME'),
(4, 'DRAMA'),
(5, 'ADVENTURE'),
(5, 'SCIENCE_FICTION');

-- Movie-Director İlişkileri
INSERT INTO movie_director (movie_id, director_id) VALUES 
(1, 1), -- Inception - Christopher Nolan
(2, 5), -- Titanic - James Cameron
(3, 2), -- Pulp Fiction - Quentin Tarantino
(4, 4), -- The Godfather - Martin Scorsese
(5, 3); -- Jurassic Park - Steven Spielberg

-- Movie-Actor İlişkileri (Cast)
INSERT INTO movie_actor (movie_id, actor_id) VALUES 
(1, 1), -- Inception - Leonardo DiCaprio
(2, 1), -- Titanic - Leonardo DiCaprio
(2, 7), -- Titanic - Kate Winslet
(3, 3), -- Pulp Fiction - Brad Pitt
(3, 8), -- Pulp Fiction - Samuel L. Jackson
(4, 5), -- The Godfather - Robert De Niro
(4, 6), -- The Godfather - Al Pacino
(5, 2); -- Jurassic Park - Tom Hanks

-- Movie-Award İlişkileri
INSERT INTO movie_award (movie_id, award_id) VALUES 
(1, 1), -- Inception - Best Picture Oscar
(1, 2), -- Inception - Best Director Oscar
(1, 3), -- Inception - Best Actor Oscar
(1, 4), -- Inception - Best Picture Golden Globe
(1, 5), -- Inception - Best Actor BAFTA
(2, 6), -- Titanic - Best Picture Oscar
(2, 7), -- Titanic - Best Director Oscar
(2, 8); -- Titanic - Best Actor Oscar

-- Actor-Award İlişkileri
INSERT INTO actor_award (actor_id, award_id) VALUES 
(1, 3), -- Leonardo DiCaprio - Best Actor Oscar (Inception)
(1, 5), -- Leonardo DiCaprio - Best Actor BAFTA (Inception)
(1, 8); -- Leonardo DiCaprio - Best Actor Oscar (Titanic)

-- Director-Award İlişkileri
INSERT INTO director_award (director_id, award_id) VALUES 
(1, 2), -- Christopher Nolan - Best Director Oscar (Inception)
(5, 7); -- James Cameron - Best Director Oscar (Titanic) 