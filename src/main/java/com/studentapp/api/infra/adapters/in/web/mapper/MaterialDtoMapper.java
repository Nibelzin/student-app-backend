package com.studentapp.api.infra.adapters.in.web.mapper;

import com.studentapp.api.domain.model.FileObject;
import com.studentapp.api.domain.model.Material;
import com.studentapp.api.domain.model.Subject;
import com.studentapp.api.domain.port.in.MaterialUseCase;
import com.studentapp.api.infra.adapters.in.web.dto.material.MaterialCreateWithFileRequest;
import com.studentapp.api.infra.adapters.in.web.dto.material.MaterialResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Component
public class MaterialDtoMapper {

    public Material withFileToDomain (MaterialCreateWithFileRequest materialCreateWithFileRequest, Subject subject, FileObject fileObject) throws IOException {

        return Material.create(
                materialCreateWithFileRequest.getTitle(),
                materialCreateWithFileRequest.getType(),
                null,
                materialCreateWithFileRequest.getIsFavorite(),
                subject,
                fileObject
        );
    }

    public MaterialResponse toResponse(Material material) {
        if (material == null) {
            return null;
        }

        MaterialResponse response = new MaterialResponse();
        response.setId(material.getId());
        response.setTitle(material.getTitle());
        response.setType(material.getType());
        response.setCreatedAt(material.getCreatedAt());
        response.setIsFavorite(material.getFavorite());
        response.setSubjectId(material.getSubject().getId());

        return response;
    }

}
