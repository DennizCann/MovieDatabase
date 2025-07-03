-- Veritabanı şeması oluşturma

-- Director tablosu
CREATE TABLE IF NOT EXISTS director (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    image_url VARCHAR(500)
);

-- Actor tablosu
CREATE TABLE IF NOT EXISTS actor (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    image_url VARCHAR(500)
);

-- Award tablosu
CREATE TABLE IF NOT EXISTS award (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    award_year INT NOT NULL,
    category VARCHAR(50) NOT NULL
);

-- Movie tablosu
CREATE TABLE IF NOT EXISTS movie (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    release_year INT NOT NULL,
    synopsis TEXT,
    budget DECIMAL(15,2),
    box_office DECIMAL(15,2),
    imdb_rating DOUBLE,
    part_of_series BOOLEAN DEFAULT FALSE,
    series_name VARCHAR(255),
    duration INT,
    image_url VARCHAR(500)
);

-- Movie genres tablosu
CREATE TABLE IF NOT EXISTS movie_genres (
    movie_id BIGINT NOT NULL,
    genre VARCHAR(50) NOT NULL,
    PRIMARY KEY (movie_id, genre),
    FOREIGN KEY (movie_id) REFERENCES movie(id)
);

-- Movie-Director ilişki tablosu
CREATE TABLE IF NOT EXISTS movie_director (
    movie_id BIGINT NOT NULL,
    director_id BIGINT NOT NULL,
    PRIMARY KEY (movie_id, director_id),
    FOREIGN KEY (movie_id) REFERENCES movie(id),
    FOREIGN KEY (director_id) REFERENCES director(id)
);

-- Movie-Actor ilişki tablosu
CREATE TABLE IF NOT EXISTS movie_actor (
    movie_id BIGINT NOT NULL,
    actor_id BIGINT NOT NULL,
    PRIMARY KEY (movie_id, actor_id),
    FOREIGN KEY (movie_id) REFERENCES movie(id),
    FOREIGN KEY (actor_id) REFERENCES actor(id)
);

-- Movie-Award ilişki tablosu
CREATE TABLE IF NOT EXISTS movie_award (
    movie_id BIGINT NOT NULL,
    award_id BIGINT NOT NULL,
    PRIMARY KEY (movie_id, award_id),
    FOREIGN KEY (movie_id) REFERENCES movie(id),
    FOREIGN KEY (award_id) REFERENCES award(id)
);

-- Actor-Award ilişki tablosu
CREATE TABLE IF NOT EXISTS actor_award (
    actor_id BIGINT NOT NULL,
    award_id BIGINT NOT NULL,
    PRIMARY KEY (actor_id, award_id),
    FOREIGN KEY (actor_id) REFERENCES actor(id),
    FOREIGN KEY (award_id) REFERENCES award(id)
);

-- Director-Award ilişki tablosu
CREATE TABLE IF NOT EXISTS director_award (
    director_id BIGINT NOT NULL,
    award_id BIGINT NOT NULL,
    PRIMARY KEY (director_id, award_id),
    FOREIGN KEY (director_id) REFERENCES director(id),
    FOREIGN KEY (award_id) REFERENCES award(id)
); 