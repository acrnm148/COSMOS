package com.cosmos.back.repository.place;

import com.cosmos.back.model.place.Place;
import com.cosmos.back.model.place.Tour;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlaceRepository extends JpaRepository<Place, Long>, PlaceRepositoryCustom {

    public List<Place> findAllByType(String type);
}
