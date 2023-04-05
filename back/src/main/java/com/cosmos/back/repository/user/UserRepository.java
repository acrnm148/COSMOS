package com.cosmos.back.repository.user;

import com.cosmos.back.model.User;
import com.cosmos.back.repository.review.ReviewRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    public User findByUserId(String username); //public 빼면 오류난다
    public User findByUserSeq(Long userSeq);
    public List<User> findByCoupleId(Long coupleId);
}
