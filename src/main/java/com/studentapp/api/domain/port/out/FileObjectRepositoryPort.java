package com.studentapp.api.domain.port.out;

import com.studentapp.api.domain.model.FileObject;

import java.util.Optional;
import java.util.UUID;

public interface FileObjectRepositoryPort {
    FileObject save(FileObject fileObject);
    Optional<FileObject> findById(UUID id);
    FileObject update(FileObject fileObject);
    void delete(UUID id);
}
