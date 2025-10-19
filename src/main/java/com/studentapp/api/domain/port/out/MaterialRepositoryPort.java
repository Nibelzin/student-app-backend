package com.studentapp.api.domain.port.out;

import com.studentapp.api.domain.model.Material;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface MaterialRepositoryPort {
    Material save(Material material);
    Optional<Material> findById(UUID id);
    Page<Material> findAll(Pageable pageable);
    Page<Material> findBySubjectId(UUID subjectId, Pageable pageable);
    Material update(Material material);
    void delete(UUID id);
}
