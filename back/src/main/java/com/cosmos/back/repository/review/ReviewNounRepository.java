package com.cosmos.back.repository.review;

import com.cosmos.back.model.Noun;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewNounRepository extends JpaRepository<Noun, Long> {
}
