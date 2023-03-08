package com.cosmos.back.repository.review;

import com.cosmos.back.model.Review;
import com.cosmos.back.model.ReviewCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewCategoryRepository extends JpaRepository<ReviewCategory, Long>{
}
