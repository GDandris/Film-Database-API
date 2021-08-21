drop table if exists FILM CASCADE;
drop table if exists DIRECTOR CASCADE;
drop sequence if exists hibernate_sequence;
create sequence hibernate_sequence start with 1 increment by 1;

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

alter table film add constraint FK4pqpakiyka8wwbiicmc0rgtwi foreign key (director_id) references DIRECTOR;