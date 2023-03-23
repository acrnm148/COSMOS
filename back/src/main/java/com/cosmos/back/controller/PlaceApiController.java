package com.cosmos.back.controller;

import com.cosmos.back.annotation.RedisCached;
import com.cosmos.back.dto.GugunDto;
import com.cosmos.back.dto.request.AutoCompleteRequestDto;
import com.cosmos.back.dto.response.place.*;
import com.cosmos.back.model.place.Gugun;
import com.cosmos.back.model.place.Sido;
import com.cosmos.back.service.PlaceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Tag(name = "place", description = "장소 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class PlaceApiController {

    private final PlaceService placeService;

    @Operation(summary = "장소 찜하기", description = "장소를 찜 함")
    @GetMapping("/places/{placeId}/users/{userSeq}")
    public ResponseEntity<Long> likePlace(@PathVariable Long placeId, @PathVariable Long userSeq) {
        Long id = placeService.likePlace(placeId, userSeq);

        return new ResponseEntity<>(id, HttpStatus.OK);
    }

    @Operation(summary = "장소 찜 삭제", description = "장소를 찜 해제함")
    @DeleteMapping("/places/{placeId}/users/{userSeq}")
    public ResponseEntity<Long> deleteLikePlace(@PathVariable Long placeId, @PathVariable Long userSeq) {
        Long execute = placeService.deleteLikePlace(placeId, userSeq);

        return new ResponseEntity<>(execute, HttpStatus.OK);
    }

//    @Operation(summary = "이름으로 장소 검색", description = "이름으로 장소 검색")
//    @GetMapping("/places/{name}")
//    public ResponseEntity<List> search(@PathVariable String name, @RequestParam Integer limit, @RequestParam Integer offset) {
//        List<PlaceListResponseDto> list = placeService.searchPlacesByName(name, limit, offset);
//
//        return new ResponseEntity<>(list, HttpStatus.OK);
//    }
//
//
//    @Operation(summary = "시도구군으로 장소 검색", description = "시도구군으로 장소 검색")
//    @GetMapping("/places/search/sido/{sido}/gugun/{gugun}")
//    public ResponseEntity<List> search(@PathVariable String sido, @PathVariable String gugun, @RequestParam Integer limit, @RequestParam Integer offset) {
//        List<PlaceListResponseDto> list = placeService.searchPlacesBySidoGugun(sido, gugun, limit, offset);
//
//        return new ResponseEntity<>(list, HttpStatus.OK);
//    }


    @Operation(summary = "검색어 자동완성", description = "검색어의 낱말을 포함한 가게 이름을 나열함")
    @GetMapping(value = {"/places/auto/{searchWord}",
            "/places/auto/"
    })
    public ResponseEntity<?> search(@PathVariable(required = false) String searchWord) {
        if (searchWord != null) {
            List<AutoCompleteResponseDto> autoCompleteResponseDto = placeService.autoCompleteSearchPlacesByName(searchWord);
            return new ResponseEntity<>(autoCompleteResponseDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }
    }

    /////////////////////////////////완성////////////////////////////////////////

    // 시도 리스트 가져오기
    @Operation(summary = "시도 리스트 가져오기", description = "시/도 코드, 시/도 이름을 반환한다")
    @GetMapping("/sido")
    public ResponseEntity<List> sidoList() {
        List<Sido> list = placeService.listSido();

        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    // 시도에 따른 구군 리스트 가져오기
    @Operation(summary = "구군 리스트 가져오기", description = "시/도의 code 번호를 queryString형식으로 보내주어야 한다.(ex: /gugun/11)")
    @GetMapping("/gugun/{code}")
    public ResponseEntity<List> gugunList(@PathVariable Integer code) {
        List<GugunDto> list = placeService.listGugun(code);

        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @Operation(summary = "장소 상세 정보 가져오기", description = "장소의 고유 placeId와 type(\"tour\", \"festival\", \"accommodation\", \"restaurant\", \"cafe\", \"shopping\", \"leisure\", \"culture\")을 요청해야함")
    @GetMapping("/places/detail/users/{userSeq}/placeId/{placeId}/type/{type}")
    public ResponseEntity<?> detailPlace(@PathVariable Long userSeq, @PathVariable Long placeId, @PathVariable String type) {
        switch (type) {
            case "tour":
                TourResponseDto tourResponseDto = placeService.detailTour(placeId, userSeq);
                if (tourResponseDto == null) {
                    return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
                } else {
                    return new ResponseEntity<>(tourResponseDto, HttpStatus.OK);
                }
            case "festival":
                FestivalResponseDto festivalResponseDto = placeService.detailFestival(placeId, userSeq);
                if (festivalResponseDto == null) {
                    return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
                } else {
                    return new ResponseEntity<>(festivalResponseDto, HttpStatus.OK);
                }
            case "accommodation":
                AccommodationResponseDto accommodationResponseDto = placeService.detailAccommodation(placeId, userSeq);
                if (accommodationResponseDto == null) {
                    return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
                } else {
                    return new ResponseEntity<>(accommodationResponseDto, HttpStatus.OK);
                }
            case "restaurant":
                RestaurantResponseDto restaurantResponseDto = placeService.detailRestaurant(placeId, userSeq);
                if (restaurantResponseDto == null) {
                    return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
                } else {
                    return new ResponseEntity<>(restaurantResponseDto, HttpStatus.OK);
                }
            case "cafe":
                CafeResponseDto cafeResponseDto = placeService.detailCafe(placeId, userSeq);
                if (cafeResponseDto == null) {
                    return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
                } else {
                    return new ResponseEntity<>(cafeResponseDto, HttpStatus.OK);
                }
            case "shopping":
                ShoppingResponseDto shoppingResponseDto = placeService.detailShopping(placeId, userSeq);
                if (shoppingResponseDto == null) {
                    return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
                } else {
                    return new ResponseEntity<>(shoppingResponseDto, HttpStatus.OK);
                }
            case "leisure":
                LeisureResponseDto leisureResponseDto = placeService.detailLeisure(placeId, userSeq);
                if (leisureResponseDto == null) {
                    return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
                } else {
                    return new ResponseEntity<>(leisureResponseDto, HttpStatus.OK);
                }
            case "culture":
                CultureResponseDto cultureResponseDto = placeService.detailCulture(placeId, userSeq);
                if (cultureResponseDto == null) {
                    return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
                } else {
                    return new ResponseEntity<>(cultureResponseDto, HttpStatus.OK);
                }
            default: return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @Operation(summary = "시/도, 구/군, 검색어, 검색 필터으로 검색", description = "유저Id, 시/도(필수x), 구/군(필수x), 검색어(필수x), 검색 필터(필수x)를 자유롭게 입력 후 요청(limit과 offset 설정 필요)")
    @GetMapping( value = {"/places/search/users/{userSeq}/sido/{sido}/gugun/{gugun}/text/{text}/filter/{filter}",
            "/places/search/users/{userSeq}/sido/{sido}/gugun/{gugun}/text/filter/{filter}",
            "/places/search/users/{userSeq}/sido/{sido}/gugun/{gugun}/text/{text}/filter/",
            "/places/search/users/{userSeq}/sido/{sido}/gugun/{gugun}/text/filter/",
            "/places/search/users/{userSeq}/sido/gugun/text/{text}/filter/{filter}",
            "/places/search/users/{userSeq}/sido/gugun/text/{text}/filter/",
            "/places/search/users/{userSeq}/sido/gugun/text/filter/{filter}"
    })
    public ResponseEntity<Map> searchPlace(@PathVariable Long userSeq, @PathVariable(required = false) String sido, @PathVariable(required = false) String gugun, @PathVariable(required = false) String text, @PathVariable(required = false) String filter, @RequestParam Integer limit, @RequestParam Integer offset) {
        if (sido == null) {sido = "";}
        if (gugun == null) {gugun = "";}
        if (text == null) {text = "";}
        if (filter == null) {filter = "";}

        Map<String, Object> list = placeService.searchPlacesBySidoGugunTextFilter(userSeq, sido, gugun, text, filter, limit, offset);

        return new ResponseEntity<>(list, HttpStatus.OK);
    }
}
