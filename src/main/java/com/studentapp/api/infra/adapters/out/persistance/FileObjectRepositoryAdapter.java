package com.studentapp.api.infra.adapters.out.persistance;

import com.studentapp.api.domain.model.FileObject;
import com.studentapp.api.domain.port.out.FileObjectRepositoryPort;
import com.studentapp.api.infra.adapters.out.persistance.entity.FileObjectEntity;
import com.studentapp.api.infra.adapters.out.persistance.mapper.FileObjectMapper;
import com.studentapp.api.infra.adapters.out.persistance.repository.FileObjectJpaRepository;
import com.studentapp.api.infra.config.exception.custom.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class FileObjectRepositoryAdapter implements FileObjectRepositoryPort {

    private final FileObjectJpaRepository fileObjectJpaRepository;
    private final FileObjectMapper fileObjectMapper;

    @Override
    public FileObject save(FileObject fileObject){
        FileObjectEntity fileObjectEntity = fileObjectMapper.toEntity(fileObject);

        FileObjectEntity persistedFileObjectEntity = fileObjectJpaRepository.save(fileObjectEntity);

        return fileObjectMapper.toDomain(persistedFileObjectEntity);
    }

    @Override
    public Optional<FileObject> findById(UUID id){
        Optional<FileObjectEntity> optionalFileObjectEntity = fileObjectJpaRepository.findById(id);

        return optionalFileObjectEntity.map(fileObjectMapper::toDomain);
    }

    @Override
    public FileObject update(FileObject fileObject){
        FileObjectEntity fileObjectEntity = fileObjectMapper.toEntity(fileObject);

        FileObjectEntity persistedFileObjectEntity = fileObjectJpaRepository.save(fileObjectEntity);

        return fileObjectMapper.toDomain(persistedFileObjectEntity);
    }

    @Override
    public void delete(UUID id){
        fileObjectJpaRepository.deleteById(id);
    }

}
