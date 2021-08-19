create table FILM
(
                        ID long identity NOT NULL PRIMARY KEY,
                        NAME varchar,
                        RELEASE_YEAR int,
                        DIRECTOR_ID long
);


create table DIRECTOR
(
                        ID long identity NOT NULL PRIMARY KEY,
                        NAME varchar
);