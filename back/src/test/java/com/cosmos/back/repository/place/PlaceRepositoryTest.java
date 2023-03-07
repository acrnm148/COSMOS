package com.cosmos.back.repository.place;

import com.cosmos.back.config.TestConfig;
import com.cosmos.back.dto.response.place.ShoppingResponseDto;
import com.cosmos.back.model.place.Shopping;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import({TestConfig.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class PlaceRepositoryTest {

    @Autowired
    private PlaceRepository placeRepository;

    @Test
    @DisplayName("PlaceRepository 쇼핑상세정보가져오기")
    @WithMockUser(username = "테스트_최고관리자", roles = {"SUPER"})
    public void 쇼핑상세정보가져오기() throws Exception {
        Shopping shopping = Shopping.builder().shoppingList("판매품목1").build();
        placeRepository.save(shopping);

        ShoppingResponseDto dto = placeRepository.findShoppingByPlaceIdQueryDsl(shopping.getId());
        assertEquals(dto.getPlaceId(), shopping.getId());
        assertEquals(dto.getShoppingList(), "판매품목1");
    }
}