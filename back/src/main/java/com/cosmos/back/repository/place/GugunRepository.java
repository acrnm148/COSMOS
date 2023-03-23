package com.cosmos.back.repository.place;

import com.cosmos.back.model.place.Gugun;
import com.cosmos.back.model.place.Place;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GugunRepository extends JpaRepository<Gugun, String> {
    @Query(value = "SELECT * FROM gugun_code gc WHERE gc.sido_code = :sidoCode", nativeQuery = true)
    public List<Gugun> findBysidoCode(@Param("sidoCode") String sidoCode);
}
