package com.cosmos.back.controller;

import com.cosmos.back.dto.request.PlanRequestDto;
import com.cosmos.back.model.Plan;
import com.cosmos.back.service.PlanService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "plan", description = "일정 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class PlanApiController {

    private final PlanService planService;

    @Operation(summary = "커플 일정 생성", description = "커플 일정 생성")
    @PostMapping("/plans")
    public ResponseEntity<Long> createPlan(@RequestBody PlanRequestDto dto) {
        Long planId = planService.createPlan(dto);
        
        return new ResponseEntity<>(planId, HttpStatus.OK);
    }

    @Operation(summary = "커플 일정 상세보기", description = "커플 일정 상세보기")
    @GetMapping("/plans/{coupleId}/{planId}")
    public ResponseEntity<?> getPlanDetail(@PathVariable("coupleId") Long coupleId,
                                           @PathVariable("planId") Long planId) {
        Plan plan = planService.getPlanDetail(coupleId, planId);

        return new ResponseEntity<>(plan, HttpStatus.OK);
    }

    @Operation(summary = "커플 일정 삭제", description = "커플 일정 삭제")
    @DeleteMapping("/plans/{coupleId}/{planId}")
    public ResponseEntity<?> deletePlan(@PathVariable("coupleId") Long coupleId,
                                           @PathVariable("planId") Long planId) {
        planService.deletePlan(coupleId, planId);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "커플 일정 월별 조회", description = "커플 일정 월별 조회")
    @GetMapping("/plans/month/{coupleId}")
    public ResponseEntity<?> getPlanListByMonth(@PathVariable("coupleId") Long coupleId) {
        List<Plan> plans = planService.getPlanListByMonth(coupleId);

        return new ResponseEntity<>(plans, HttpStatus.OK);
    }

    @Operation(summary = "커플 일정 일별 조회", description = "커플 일정 일별 조회")
    @GetMapping("/plans/day/{coupleId}")
    public ResponseEntity<?> getPlanListByDay(@PathVariable("coupleId") Long coupleId) {
        List<PlanOfDayDto> plans = planService.getPlanListByDay(coupleId);

        return new ResponseEntity<>(plans, HttpStatus.OK);
    }
}
