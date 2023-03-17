package com.cosmos.back.repository.reviewplace;

import com.cosmos.back.model.ReviewPlace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewPlaceRepository extends JpaRepository<ReviewPlace, Long> {
}
