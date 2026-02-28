package com.studentapp.api.infra.adapters.out.persistance;

import com.studentapp.api.domain.model.Material;
import com.studentapp.api.domain.port.out.MaterialRepositoryPort;
import com.studentapp.api.infra.adapters.out.persistance.entity.ActivityEntity;
import com.studentapp.api.infra.adapters.out.persistance.entity.MaterialEntity;
import com.studentapp.api.infra.adapters.out.persistance.entity.SubjectEntity;
import com.studentapp.api.infra.adapters.out.persistance.mapper.MaterialMapper;
import com.studentapp.api.infra.adapters.out.persistance.repository.ActivityJpaRepository;
import com.studentapp.api.infra.adapters.out.persistance.repository.MaterialJpaRepository;
import com.studentapp.api.infra.adapters.out.persistance.repository.SubjectJpaRepository;
import com.studentapp.api.infra.config.exception.custom.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class MaterialRepositoryAdapter implements MaterialRepositoryPort {

    private final MaterialJpaRepository materialJpaRepository;
    private final SubjectJpaRepository subjectJpaRepository;
    private final ActivityJpaRepository activityJpaRepository;
    private final MaterialMapper materialMapper;

    @Override
    public Material save(Material material){
        MaterialEntity materialEntity = materialMapper.toEntity(material);

        MaterialEntity persistedMaterialEntity = materialJpaRepository.save(materialEntity);

        return materialMapper.toDomain(persistedMaterialEntity);
    }

    @Override
    public Optional<Material> findById(UUID id){
        Optional<MaterialEntity> optionalMaterialEntity = materialJpaRepository.findById(id);

        return optionalMaterialEntity.map(materialMapper::toDomain);
    }

    @Override
    public Page<Material> findAll(Pageable pageable){
        Page<MaterialEntity> materialEntityPage = materialJpaRepository.findAll(pageable);

        return materialEntityPage.map(materialMapper::toDomain);
    }

    @Override
    public Page<Material> findBySubjectId(UUID subjectId, Pageable pageable){
        SubjectEntity subjectEntity = subjectJpaRepository.findById(subjectId).orElseThrow(
                () -> new ResourceNotFoundException("Matéria não encontrada.")
        );

        Page<MaterialEntity> materialEntityPage = materialJpaRepository.findBySubject(subjectEntity, pageable);

        return materialEntityPage.map(materialMapper::toDomain);
    }

    @Override
    public List<Material> findByActivityId(UUID activityId){
        ActivityEntity activityEntity = activityJpaRepository.findById(activityId).orElseThrow(
                () -> new ResourceNotFoundException("Atividade não encontrada.")
        );

        List<MaterialEntity> foundActivities = materialJpaRepository.findByActivity(activityEntity);

        return foundActivities.stream().map(materialMapper::toDomain).toList();
    }

    @Override
    public Material update(Material material){
        MaterialEntity materialEntity = materialMapper.toEntity(material);

        MaterialEntity persistedMaterialEntity = materialJpaRepository.save(materialEntity);

        return materialMapper.toDomain(persistedMaterialEntity);
    }

    @Override
    public void delete(UUID id){
        materialJpaRepository.deleteById(id);
    }
}
