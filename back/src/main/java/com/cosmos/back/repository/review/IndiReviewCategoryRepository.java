package com.cosmos.back.repository.review;

import com.cosmos.back.model.IndiReviewCategory;
import com.cosmos.back.model.ReviewCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IndiReviewCategoryRepository extends JpaRepository<IndiReviewCategory, Long> {

    public List<IndiReviewCategory> findAllByReviewId(Long reviewId);
}
