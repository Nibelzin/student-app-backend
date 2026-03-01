package com.studentapp.api.infra.adapters.out.persistance.jpa;

import com.studentapp.api.domain.port.in.FocusSessionUseCase;
import com.studentapp.api.infra.adapters.out.persistance.entity.FocusSessionEntity;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class FocusSessionSpecification {

    public static Specification<FocusSessionEntity> withQuery(FocusSessionUseCase.FocusSessionQueryData query) {
        return (root, criteriaQuery, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (query.userId() != null) {
                predicates.add(cb.equal(root.get("user").get("id"), query.userId()));
            }
            if (query.subjectId() != null) {
                predicates.add(cb.equal(root.get("subject").get("id"), query.subjectId()));
            }
            if (query.activityId() != null) {
                predicates.add(cb.equal(root.get("activity").get("id"), query.activityId()));
            }
            if (query.isCompleted() != null) {
                predicates.add(cb.equal(root.get("isCompleted"), query.isCompleted()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
