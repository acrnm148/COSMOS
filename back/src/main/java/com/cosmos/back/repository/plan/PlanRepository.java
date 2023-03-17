package com.cosmos.back.repository.plan;

import com.cosmos.back.model.Plan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PlanRepository extends JpaRepository<Plan, Long>, PlanRepositoryCustom {
    public Optional<Plan> findById(Long planId);
}
