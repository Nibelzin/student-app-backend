package com.studentapp.api.infra.adapters.in.web;

import com.studentapp.api.domain.model.Activity;
import com.studentapp.api.domain.model.FileObject;
import com.studentapp.api.domain.model.Material;
import com.studentapp.api.domain.model.Subject;
import com.studentapp.api.domain.port.in.ActivityUseCase;
import com.studentapp.api.domain.port.in.MaterialUseCase;
import com.studentapp.api.domain.port.in.SubjectUseCase;
import com.studentapp.api.infra.adapters.in.web.dto.fileObject.FileObjectResponse;
import com.studentapp.api.infra.adapters.in.web.dto.material.MaterialCreateRequest;
import com.studentapp.api.infra.adapters.in.web.dto.material.MaterialCreateWithFileRequest;
import com.studentapp.api.infra.adapters.in.web.dto.material.MaterialResponse;
import com.studentapp.api.infra.adapters.in.web.dto.material.MaterialUpdateRequest;
import com.studentapp.api.infra.adapters.in.web.mapper.FileObjectDtoMapper;
import com.studentapp.api.infra.adapters.in.web.mapper.MaterialDtoMapper;
import com.studentapp.api.infra.config.exception.custom.ResourceNotFoundException;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/materials")
@Tag(name = "material")
@RequiredArgsConstructor
public class MaterialController {

    private final MaterialUseCase materialUseCase;
    private final SubjectUseCase subjectUseCase;
    private final ActivityUseCase activityUseCase;
    private final MaterialDtoMapper materialDtoMapper;
    private final FileObjectDtoMapper fileObjectDtoMapper;

    @GetMapping
    public ResponseEntity<Page<MaterialResponse>> getAllMaterials(Pageable pageable){
        Page<Material> materialPage = materialUseCase.findAllMaterials(pageable);

        Page<MaterialResponse> materialResponsePage = materialPage.map(materialDtoMapper::toResponse);

        return ResponseEntity.ok(materialResponsePage);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MaterialResponse> getMaterialById(@PathVariable UUID id){

        Optional<Material> foundMaterial = materialUseCase.findMaterialById(id);

        return foundMaterial
                .map(materialDtoMapper::toResponse)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/file")
    public ResponseEntity<FileObjectResponse> getMaterialFileById(@PathVariable UUID id){
        Optional<FileObject> foundFileObject = materialUseCase.findFileObjectByMaterialId(id);

        return foundFileObject
                .map(fileObjectDtoMapper::toResponse)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());

    }

    @PostMapping(path = "/with-file", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<MaterialResponse> createMaterialWithFile(@Valid @ModelAttribute MaterialCreateWithFileRequest dto){
        try {

            MaterialUseCase.FileInput fileInput = new MaterialUseCase.FileInput(
                    dto.getFile().getInputStream(),
                    dto.getFile().getOriginalFilename(),
                    dto.getFile().getContentType(),
                    dto.getFile().getSize()
            );

            Material createdMaterial = materialUseCase.createMaterialWithFile(dto.getTitle(), dto.getType(), dto.getIsFavorite(), dto.getSubjectId(), dto.getActivityId(), fileInput);

            MaterialResponse materialResponse = materialDtoMapper.toResponse(createdMaterial);

            return ResponseEntity.ok(materialResponse);
        } catch (IOException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping()
    public ResponseEntity<MaterialResponse> createMaterial(@Valid @RequestBody MaterialCreateRequest dto){

        Material createdMaterial = materialUseCase.createMaterialWithUrl(dto.getTitle(), dto.getType(), dto.getIsFavorite(), dto.getSubjectId(), dto.getActivityId(), dto.getExternalUrl());

        MaterialResponse materialResponse = materialDtoMapper.toResponse(createdMaterial);

        return ResponseEntity.ok(materialResponse);
    }

    @PutMapping(path = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<MaterialResponse> updateMaterial(@PathVariable UUID id, @Valid @ModelAttribute MaterialUpdateRequest dto){

        Material foundMaterial = materialUseCase.findMaterialById(id).orElseThrow(
                () -> new ResourceNotFoundException("Material não encontrado.")
        );

        try {

            if (dto.getFile() != null && !dto.getFile().isEmpty()) {
                MaterialUseCase.FileInput fileInput = new MaterialUseCase.FileInput(
                        dto.getFile().getInputStream(),
                        dto.getFile().getOriginalFilename(),
                        dto.getFile().getContentType(),
                        dto.getFile().getSize()
                );

                Material updatedMaterial = materialUseCase.updateMaterial(foundMaterial.getId(), dto.getTitle(), dto.getType(), dto.getIsFavorite(), dto.getSubjectId(), dto.getActivityId(), null, fileInput);

                MaterialResponse materialResponse = materialDtoMapper.toResponse(updatedMaterial);

                return ResponseEntity.ok(materialResponse);
            } else {
                Material updatedMaterial = materialUseCase.updateMaterial(foundMaterial.getId(), dto.getTitle(), dto.getType(), dto.getIsFavorite(), dto.getSubjectId(), dto.getActivityId(), dto.getExternalUrl(), null);

                MaterialResponse materialResponse = materialDtoMapper.toResponse(updatedMaterial);

                return ResponseEntity.ok(materialResponse);
            }

        } catch (IOException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MaterialResponse> deleteMaterial(@PathVariable UUID id){
        materialUseCase.deleteMaterial(id);
        return ResponseEntity.noContent().build();
    }

}
