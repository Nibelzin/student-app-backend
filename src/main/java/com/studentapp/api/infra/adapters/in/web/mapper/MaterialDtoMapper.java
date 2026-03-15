package com.studentapp.api.infra.adapters.in.web.mapper;

import com.studentapp.api.domain.model.activity.Activity;
import com.studentapp.api.domain.model.material.FileObject;
import com.studentapp.api.domain.model.material.Material;
import com.studentapp.api.domain.model.subject.Subject;
import com.studentapp.api.domain.port.out.FileStorageServicePort;
import com.studentapp.api.infra.adapters.in.web.dto.material.MaterialCreateRequest;
import com.studentapp.api.infra.adapters.in.web.dto.material.MaterialCreateWithFileRequest;
import com.studentapp.api.infra.adapters.in.web.dto.material.MaterialResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class MaterialDtoMapper {

    private final FileStorageServicePort fileStorageServicePort;

    public Material toDomain(MaterialCreateRequest materialCreateRequest, Subject subject, Activity activity){
        return Material.create(
                materialCreateRequest.getTitle(),
                materialCreateRequest.getType(),
                materialCreateRequest.getExternalUrl(),
                materialCreateRequest.getIsFavorite(),
                subject,
                activity,
                null
        );
    }

    public Material withFileToDomain (MaterialCreateWithFileRequest materialCreateWithFileRequest, Subject subject, Activity activity, FileObject fileObject) throws IOException {

        return Material.create(
                materialCreateWithFileRequest.getTitle(),
                materialCreateWithFileRequest.getType(),
                null,
                materialCreateWithFileRequest.getIsFavorite(),
                subject,
                activity,
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
        response.setExternalUrl(material.getExternalUrl());

        if(material.getSubject() != null) {
            response.setSubjectId(material.getSubject().getId());
            response.setSubjectName(material.getSubject().getName());
        }

        if(material.getActivity() != null) {
            response.setActivityId(material.getActivity().getId());
            response.setActivityName(material.getActivity().getTitle());
        }

        if(material.getFile() != null) {
            String url = fileStorageServicePort.generatePresignedUrl(material.getFile().getStoragePath());
            response.setFileUrl(url);
        }

        return response;
    }

}
