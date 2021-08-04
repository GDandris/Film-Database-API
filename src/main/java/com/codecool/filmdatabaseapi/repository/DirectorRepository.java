package com.codecool.filmdatabaseapi.repository;


import com.codecool.filmdatabaseapi.model.Director;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DirectorRepository extends JpaRepository<Director, Long> {
}
