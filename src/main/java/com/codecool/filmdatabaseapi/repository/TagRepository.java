package com.codecool.filmdatabaseapi.repository;

import com.codecool.filmdatabaseapi.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
}
