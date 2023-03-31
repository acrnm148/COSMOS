package com.cosmos.back.repository.image;

import com.cosmos.back.dto.response.ImageResponseDto;
import com.cosmos.back.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long>, ImageRepositoryCustom {


}
