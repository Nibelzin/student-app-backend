package com.studentapp.api.domain.service;

import com.studentapp.api.domain.model.FileObject;
import com.studentapp.api.domain.model.Material;
import com.studentapp.api.domain.model.Subject;
import com.studentapp.api.domain.port.in.MaterialUseCase;
import com.studentapp.api.domain.port.out.FileObjectRepositoryPort;
import com.studentapp.api.domain.port.out.FileStorageServicePort;
import com.studentapp.api.domain.port.out.MaterialRepositoryPort;
import com.studentapp.api.domain.port.out.SubjectRepositoryPort;
import com.studentapp.api.infra.config.exception.custom.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MaterialServiceImpl implements MaterialUseCase {

    private final MaterialRepositoryPort materialRepository;
    private final FileObjectRepositoryPort fileObjectRepository;
    private final FileStorageServicePort fileStorageService;
    private final SubjectRepositoryPort subjectRepository;

    @Override
    @Transactional
    public Material createMaterialWithFile(String title, String type, Boolean isFavorite, UUID subjectId, FileInput file) {

        Subject subject = subjectRepository.findById(subjectId).orElseThrow(
                () -> new ResourceNotFoundException("Matéria não encontrada.")
        );

        String path = createFilePath(subject, file.originalFilename());

        FileStorageServicePort.StorageDetails uploadedFileDetails = fileStorageService.save(file, path);

        FileObject fileObject = FileObject.create(uploadedFileDetails.name(),uploadedFileDetails.provider(), uploadedFileDetails.bucket(), uploadedFileDetails.path(), uploadedFileDetails.mimeType(), uploadedFileDetails.size(), uploadedFileDetails.checksum());

        FileObject savedFileObject = fileObjectRepository.save(fileObject);

        Material material = Material.create(title, type, null, isFavorite, subject, savedFileObject);

        return materialRepository.save(material);
    }

    @Override
    @Transactional
    public Material createMaterialWithUrl(String title, String type, Boolean isFavorite, UUID subjectId, String url) {

        Subject subject = subjectRepository.findById(subjectId).orElseThrow(
                () -> new ResourceNotFoundException("Matéria não encontrada.")
        );

        Material material = Material.create(title, type, url, isFavorite, subject, null);

        return materialRepository.save(material);
    }

    @Override
    @Transactional
    public Material updateMaterial(UUID id, String title, String type, Boolean isFavorite, String url, FileInput file){

        Material existingMaterial = materialRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Material não encontrado.")
        );

        if(title != null && !title.isBlank()){
            existingMaterial.setTitle(title);
        }

        if(type != null && !type.isBlank()){
            existingMaterial.setType(type);
        }

        if(isFavorite != null){
            existingMaterial.setFavorite(isFavorite);
        }

        if(url != null){
            existingMaterial.setExternalUrl(url);
        }

        if(file != null){

            FileObject existingFileObject = existingMaterial.getFile();
            if(existingFileObject != null){
                fileStorageService.delete(existingFileObject.getStoragePath());
            }

            String path = createFilePath(existingMaterial.getSubject(), file.originalFilename());

            FileStorageServicePort.StorageDetails uploadedFileDetails = fileStorageService.save(file, path);
            FileObject fileObject = FileObject.create(uploadedFileDetails.name(), uploadedFileDetails.provider(), uploadedFileDetails.bucket(), uploadedFileDetails.path(), uploadedFileDetails.mimeType(), uploadedFileDetails.size(), uploadedFileDetails.checksum());
            FileObject savedFileObject = fileObjectRepository.save(fileObject);

            existingMaterial.setFile(savedFileObject);
        }

        return materialRepository.update(existingMaterial);
    }

    @Override
    public Optional<Material> findMaterialById(UUID id){
        return materialRepository.findById(id);
    }

    @Override
    public Page<Material> findAllMaterials(Pageable pageable){
        return materialRepository.findAll(pageable);
    };

    @Override
    public Page<Material> findMaterialsBySubjectId(UUID subjectId, Pageable pageable){
        return materialRepository.findBySubjectId(subjectId, pageable);
    }

    @Override
    public Optional<FileObject> findFileObjectByMaterialId(UUID id){
        Material foundMaterial = materialRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Material não encontrado.")
        );

        return Optional.ofNullable(foundMaterial.getFile());
    }

    @Override
    public void deleteMaterial(UUID id){
        Material foundMaterial = materialRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Material não encontrado.")
        );

        if(foundMaterial.getFile() != null) {
            fileStorageService.delete(foundMaterial.getFile().getStoragePath());
        }

        materialRepository.delete(foundMaterial.getId());
    }

    private String createFilePath(Subject subject, String filename){
        String safeFilename = filename.replaceAll("[^a-zA-Z0-9._-]", "_");
        return "user/" + subject.getUser().getId() + "/subject/" + subject.getId() + "/materials/" + safeFilename;
    }
}
