package com.cosmos.back.repository.place;

import com.cosmos.back.model.UserPlace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserPlaceRepository extends JpaRepository<UserPlace, Long>, UserPlaceRepositoryCustom {
}
