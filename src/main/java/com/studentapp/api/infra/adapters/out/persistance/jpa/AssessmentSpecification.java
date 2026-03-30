package com.studentapp.api.infra.adapters.out.persistance.jpa;

import com.studentapp.api.domain.port.in.AssessmentUseCase;
import com.studentapp.api.infra.adapters.out.persistance.entity.AssessmentEntity;
import com.studentapp.api.infra.adapters.out.persistance.entity.SubjectEntity;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class AssessmentSpecification {

    public static Specification<AssessmentEntity> byCriteria(AssessmentUseCase.AssessmentQueryData queryData) {
        return (root, criteriaQuery, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (queryData.subjectId().isPresent()) {
                predicates.add(cb.equal(root.get("subject").get("id"), queryData.subjectId().get()));
            }
            if (queryData.userId().isPresent()) {
                Join<AssessmentEntity, SubjectEntity> subjectJoin = root.join("subject");
                predicates.add(cb.equal(subjectJoin.get("user").get("id"), queryData.userId().get()));
            }
            if (queryData.assessmentDate().isPresent()) {
                predicates.add(cb.equal(root.get("assessmentDate"), queryData.assessmentDate().get()));
            }

            if (queryData.isCompleted().isPresent()) {
                predicates.add(cb.isNotNull(root.get("grade")));
            }

            if (queryData.isCompleted().isPresent() && !queryData.isCompleted().get()) {
                predicates.add(cb.isNull(root.get("grade")));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
