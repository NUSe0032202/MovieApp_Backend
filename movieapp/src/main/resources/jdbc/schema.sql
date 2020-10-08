CREATE TABLE movieDetails (
     movie_title VARCHAR(100) NOT NULL primary key,
     movie_genre VARCHAR(20) NOT NULL,
     movie_language VARCHAR(20) NOT NULL,
     movie_date DATE NOT NULL,
     movie_rating INT NOT NULL
);

CREATE TABLE movieActors (
     entry INT AUTO_INCREMENT primary key,
     movie_actor VARCHAR(40) NOT NULL,
     movie_title  VARCHAR(100) NOT NULL,
     FOREIGN KEY(movie_title) REFERENCES movieDetails(movie_title)
);