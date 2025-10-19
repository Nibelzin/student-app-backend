package com.studentapp.api.infra.adapters.in.web;

import com.studentapp.api.domain.model.Material;
import com.studentapp.api.domain.model.Subject;
import com.studentapp.api.domain.port.in.MaterialUseCase;
import com.studentapp.api.domain.port.in.SubjectUseCase;
import com.studentapp.api.infra.adapters.in.web.dto.material.MaterialCreateWithFileRequest;
import com.studentapp.api.infra.adapters.in.web.dto.material.MaterialResponse;
import com.studentapp.api.infra.adapters.in.web.mapper.MaterialDtoMapper;
import com.studentapp.api.infra.config.exception.custom.ResourceNotFoundException;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/materials")
@Tag(name = "material")
@RequiredArgsConstructor
public class MaterialController {

    private final MaterialUseCase materialUseCase;
    private final SubjectUseCase subjectUseCase;
    private final MaterialDtoMapper materialDtoMapper;

    @PostMapping(path = "/with-file", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<MaterialResponse> createMaterial(@Valid @ModelAttribute MaterialCreateWithFileRequest dto){
        try {

            MaterialUseCase.FileInput fileInput = new MaterialUseCase.FileInput(
                    dto.getFile().getInputStream(),
                    dto.getFile().getOriginalFilename(),
                    dto.getFile().getContentType(),
                    dto.getFile().getSize()
            );

            Subject foundSubject = subjectUseCase.findSubjectById(dto.getSubjectId()).orElseThrow(
                    () -> new ResourceNotFoundException("Matéria não encontrada.")
            );

            Material createdMaterial = materialUseCase.createMaterialWithFile(dto.getTitle(), dto.getType(), dto.getIsFavorite(), foundSubject.getId(), fileInput);

            MaterialResponse materialResponse = materialDtoMapper.toResponse(createdMaterial);

            return ResponseEntity.ok(materialResponse);
        } catch (IOException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

}
