package com.studentapp.api.domain.service;

import com.studentapp.api.domain.model.Activity;
import com.studentapp.api.domain.model.FileObject;
import com.studentapp.api.domain.model.Material;
import com.studentapp.api.domain.model.Subject;
import com.studentapp.api.domain.port.in.MaterialUseCase;
import com.studentapp.api.domain.port.out.*;
import com.studentapp.api.infra.config.exception.custom.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MaterialServiceImpl implements MaterialUseCase {

    private final MaterialRepositoryPort materialRepository;
    private final FileObjectRepositoryPort fileObjectRepository;
    private final FileStorageServicePort fileStorageService;
    private final SubjectRepositoryPort subjectRepository;
    private final ActivityRepositoryPort activityRepository;

    private record MaterialContext(Subject subject, Activity activity){};

    @Override
    @Transactional
    public Material createMaterialWithFile(String title, String type, Boolean isFavorite, UUID subjectId, UUID activityId, FileInput file) {

        MaterialContext ctx = resolveContext(subjectId, activityId);

        String path = createFilePath(ctx.subject, file.originalFilename());

        FileStorageServicePort.StorageDetails uploadedFileDetails = fileStorageService.save(file, path);

        FileObject fileObject = FileObject.create(uploadedFileDetails.name(),uploadedFileDetails.provider(), uploadedFileDetails.bucket(), uploadedFileDetails.path(), uploadedFileDetails.mimeType(), uploadedFileDetails.size(), uploadedFileDetails.checksum());

        FileObject savedFileObject = fileObjectRepository.save(fileObject);

        Material material = Material.create(title, type, null, isFavorite, ctx.subject, ctx.activity, savedFileObject);

        return materialRepository.save(material);
    }

    @Override
    @Transactional
    public Material createMaterialWithUrl(String title, String type, Boolean isFavorite, UUID subjectId, UUID activityId, String url) {

        MaterialContext ctx = resolveContext(subjectId, activityId);

        Material material = Material.create(title, type, url, isFavorite, ctx.subject, ctx.activity, null);

        return materialRepository.save(material);
    }

    @Override
    @Transactional
    public Material updateMaterial(UUID id, String title, String type, Boolean isFavorite, UUID subjectId, UUID activityId, String url, FileInput file){

        Material existingMaterial = materialRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Material não encontrado.")
        );

        if(subjectId != null){
            Subject exitstingSubject = subjectRepository.findById(subjectId).orElseThrow(
                    () -> new ResourceNotFoundException("Matéria não encontrada.")
            );

            existingMaterial.setSubject(exitstingSubject);
        }

        if(activityId != null){
            Activity existingActivity = activityRepository.findById(activityId).orElseThrow(
                    () -> new ResourceNotFoundException("Atividade não encontrada.")
            );

            existingMaterial.setActivity(existingActivity);
        }

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
    public List<Material> findMaterialsByActivityId(UUID activityId){
        return materialRepository.findByActivityId(activityId);
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

    private MaterialContext resolveContext(UUID subjectId, UUID activityId){
        Activity activity = null;
        Subject subject = null;

        if (activityId != null) {
            activity = activityRepository.findById(activityId).orElseThrow(
                    () -> new ResourceNotFoundException("Atividade não encontrada.")
            );
            subject = activity.getSubject();
        }

        if (subject == null) {
            if (subjectId == null) {
                throw new IllegalArgumentException("É necessário informar uma Atividade ou uma Matéria para criar o material.");
            }

            subject = subjectRepository.findById(subjectId).orElseThrow(
                    () -> new ResourceNotFoundException("Matéria não encontrada.")
            );
        } else {
            if (subjectId != null && !subject.getId().equals(subjectId)) {
                throw new IllegalArgumentException("A atividade informada não pertence à matéria informada.");
            }
        }

        return new MaterialContext(subject, activity);
    }

    private String createFilePath(Subject subject, String filename){
        String safeFilename = filename.replaceAll("[^a-zA-Z0-9._-]", "_");
        return "user/" + subject.getUser().getId() + "/subject/" + subject.getId() + "/materials/" + safeFilename;
    }
}
