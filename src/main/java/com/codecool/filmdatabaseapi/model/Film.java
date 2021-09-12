package com.codecool.filmdatabaseapi.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Film {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int releaseYear;
    @ManyToOne
    private Director director;
    @ManyToMany
    private List<Tag> tags = new ArrayList<>();

    public Film(String name, int releaseYear, Director director) {
        this.name = name;
        this.releaseYear = releaseYear;
        this.director = director;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Film film = (Film) o;
        return releaseYear == film.releaseYear && name.equals(film.name) && director.equals(film.director);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, releaseYear, director);
    }
}
