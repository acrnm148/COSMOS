package com.cosmos.back.repository.place;

import com.cosmos.back.model.place.Place;
import com.cosmos.back.model.place.Tour;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaceRepository extends JpaRepository<Place, Long>, PlaceRepositoryCustom {

}
