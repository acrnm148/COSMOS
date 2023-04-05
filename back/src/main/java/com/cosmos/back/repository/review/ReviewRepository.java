package com.cosmos.back.repository.review;

import com.cosmos.back.model.Review;
import com.cosmos.back.model.ReviewCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long>, ReviewRepositoryCustom {
}
