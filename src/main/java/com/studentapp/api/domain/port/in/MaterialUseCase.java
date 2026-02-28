package com.studentapp.api.domain.port.in;

import com.studentapp.api.domain.model.FileObject;
import com.studentapp.api.domain.model.Material;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MaterialUseCase {

    public record FileInput(
            InputStream stream,
            String originalFilename,
            String contentType,
            long size
    ){}

    Material createMaterialWithFile(String title, String type, Boolean isFavorite, UUID subjectId, UUID activityId, FileInput fileInput);

    Material createMaterialWithUrl(String title, String type, Boolean isFavorite, UUID subjectId, UUID activityId, String url);

    Material updateMaterial(UUID id, String title, String type, Boolean isFavorite, UUID subjectId, UUID activityId, String url, FileInput fileInput);

    Optional<Material> findMaterialById(UUID id);

    Page<Material> findAllMaterials(Pageable pageable);

    Page<Material> findMaterialsBySubjectId(UUID subjectId, Pageable pageable);

    List<Material> findMaterialsByActivityId(UUID activityId);

    Optional<FileObject> findFileObjectByMaterialId(UUID id);

    void deleteMaterial(UUID id);
}
