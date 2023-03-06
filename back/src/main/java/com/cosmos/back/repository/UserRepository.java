package com.cosmos.back.repository;

import com.cosmos.back.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    public User findByUserId(String username); //public 빼면 오류난다
    public User findByUserSeq(Long userSeq);
}
