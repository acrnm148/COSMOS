package com.cosmos.back.repository.review;

import com.cosmos.back.model.Noun;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewNounRepository extends JpaRepository<Noun, Long> {
}
