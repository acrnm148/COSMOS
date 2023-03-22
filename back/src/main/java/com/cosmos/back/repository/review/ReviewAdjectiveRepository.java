package com.cosmos.back.repository.review;

import com.cosmos.back.model.Adjective;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewAdjectiveRepository extends JpaRepository<Adjective, Long> {
}
