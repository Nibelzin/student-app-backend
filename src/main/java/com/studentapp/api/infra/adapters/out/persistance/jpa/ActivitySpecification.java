package com.studentapp.api.infra.adapters.out.persistance.jpa;

import com.studentapp.api.domain.port.in.ActivityUseCase;
import com.studentapp.api.infra.adapters.out.persistance.entity.ActivityEntity;
import com.studentapp.api.infra.adapters.out.persistance.entity.SubjectEntity;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ActivitySpecification {

    public static Specification<ActivityEntity> byCriteria(ActivityUseCase.ActivityQueryData queryData){
        return (root, criteriaQuery, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(queryData.subjectId().isPresent()){
                predicates.add(cb.equal(root.get("subject").get("id"), queryData.subjectId().get()));
            }
            if(queryData.userId().isPresent()){
                Join<ActivityEntity, SubjectEntity> subjectJoin = root.join("subject");
                predicates.add(cb.equal(subjectJoin.get("user").get("id"), queryData.userId().get()));
            }
            if(queryData.type().isPresent()){
                predicates.add(cb.equal(root.get("type"), queryData.type().get()));
            }
            if(queryData.dueDate().isPresent()){
                predicates.add(cb.equal(root.get("dueDate"), queryData.dueDate().get()));
            }
            if(queryData.isCompleted().isPresent()){
                predicates.add(cb.equal(root.get("isCompleted"), queryData.isCompleted().get()));
            }
            if(queryData.isOverdue().isPresent()){
                if(queryData.isOverdue().get()){
                    predicates.add(cb.and(
                            cb.lessThan(root.get("dueDate"), LocalDate.now()),
                            cb.equal(root.get("isCompleted"), false)
                    ));
                } else {
                    predicates.add(cb.or(
                            cb.greaterThan(root.get("dueDate"), LocalDate.now()),
                            cb.equal(root.get("isCompleted"), true)
                    ));
                }
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

}
