package com.studentapp.api.infra.adapters.in.web.mapper;

import com.studentapp.api.domain.model.FileObject;
import com.studentapp.api.infra.adapters.in.web.dto.fileObject.FileObjectResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class FileObjectDtoMapper {

    public abstract FileObjectResponse toResponse(FileObject fileObject);
}
