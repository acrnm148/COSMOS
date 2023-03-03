package com.cosmos.back.auth.jwt.refreshToken;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByRefreshToken(String refreshToken);
    //public int deleteRefreshTokenById(Long id);
    public int deleteByRefreshToken(String refreshToken);

    //public void deleteById(Long id);
}