package com.cosmos.back.repository.place;

import com.cosmos.back.model.place.Sido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SidoRepository extends JpaRepository<Sido, String> {
}
