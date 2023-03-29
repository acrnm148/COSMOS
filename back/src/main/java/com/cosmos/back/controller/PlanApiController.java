package com.cosmos.back.controller;

import com.cosmos.back.dto.PlanDto;
import com.cosmos.back.dto.PlanCourseDto;
import com.cosmos.back.model.Plan;
import com.cosmos.back.service.PlanService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Tag(name = "plan", description = "일정 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class PlanApiController {

    private final PlanService planService;

    @Operation(summary = "커플 일정 생성", description = "커플 일정 생성")
    @PostMapping("/plans")
    public ResponseEntity<Plan> createPlan(@RequestBody PlanDto dto) {
        Plan plan = planService.createPlan(dto);

        return new ResponseEntity<>(plan, HttpStatus.OK);
    }

    @Operation(summary = "커플 일정 상세보기", description = "커플 일정 상세보기")
    @GetMapping("/plans/{coupleId}/{planId}")
    public ResponseEntity<?> getPlanDetail(@PathVariable("coupleId") Long coupleId,
                                           @PathVariable("planId") Long planId) {
        Plan plan = planService.getPlanDetail(coupleId, planId);

        return new ResponseEntity<>(plan, HttpStatus.OK);
    }

    @Operation(summary = "커플 일정 수정", description = "커플 일정 수정")
    @PutMapping("/plans")
    public ResponseEntity<Plan> updatePlan(@RequestBody PlanDto dto) {
        Plan plan = planService.updatePlan(dto);

        return new ResponseEntity<>(plan, HttpStatus.OK);
    }

    @Operation(summary = "커플 일정 삭제", description = "커플 일정 삭제")
    @DeleteMapping("/plans/{coupleId}/{planId}")
    public ResponseEntity<?> deletePlan(@PathVariable("coupleId") Long coupleId,
                                        @PathVariable("planId") Long planId) {
        planService.deletePlan(coupleId, planId);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "커플 일정 월별 조회(ex: 202301)", description = "커플 일정 월별 조회, coupleId와 month(202303)을 넘겨야한다.")
    @GetMapping("/plans/{coupleId}/month/{month}") //202301
    public ResponseEntity<?> getPlanListByMonth(@PathVariable Long coupleId, @PathVariable String month) {

        List<PlanDto> plans = planService.getPlanListByMonth(coupleId, month);

        return new ResponseEntity<>(plans, HttpStatus.OK);
    }

    @Operation(summary = "커플 일정 일별 조회(ex: 20230130)", description = "커플 일정 일별 조회, coupleId와 day(20230329)를 주소창에 입력이 필요")
    @GetMapping("/plans/{coupleId}/day/{day}") //20230130
    public ResponseEntity<?> getPlanListByDay(@PathVariable Long coupleId, @PathVariable String day) {

        PlanDto plans = planService.getPlanListByDay(coupleId, day);

        return new ResponseEntity<>(plans, HttpStatus.OK);
    }
}
