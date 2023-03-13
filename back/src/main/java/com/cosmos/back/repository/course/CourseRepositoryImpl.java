package com.cosmos.back.repository.course;

import com.cosmos.back.model.Course;
import com.cosmos.back.model.Plan;
import com.cosmos.back.model.QCourse;
import com.cosmos.back.model.QPlan;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CourseRepositoryImpl implements CourseRepositoryCustom {

    private final JPAQueryFactory queryFactory;
}
