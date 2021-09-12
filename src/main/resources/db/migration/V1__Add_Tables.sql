drop table if exists FILM CASCADE;
drop table if exists DIRECTOR CASCADE;
drop table if exists film_tags CASCADE
drop table if exists tag CASCADE
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
create table FILM_TAGS
(
                        FILM_ID bigint not null,
                        TAGS_ID bigint not null
)
create table TAG
(
                        id bigint identity NOT NULL PRIMARY KEY,
                        name varchar(255)
)

alter table film add constraint FK4pqpakiyka8wwbiicmc0rgtwi foreign key (director_id) references DIRECTOR;
alter table film_tags add constraint FKjiqv95gpr7og84un3iaxvigd3 foreign key (tags_id) references TAG;
alter table film_tags add constraint FKdfkbdk423gcenypv9nf94tjsf foreign key (film_id) references FILM;