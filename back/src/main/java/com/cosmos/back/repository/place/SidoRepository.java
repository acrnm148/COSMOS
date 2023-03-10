package com.cosmos.back.repository.place;

import com.cosmos.back.model.place.Sido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SidoRepository extends JpaRepository<Sido, String> {
    public List<Sido> findAll();

    @Query(value = "SELECT s.code, s.name FROM sido_code s", nativeQuery = true)
    public List<Sido> findSido();
}
