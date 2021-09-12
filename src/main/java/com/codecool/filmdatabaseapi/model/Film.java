package com.codecool.filmdatabaseapi.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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

}
