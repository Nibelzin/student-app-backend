package com.studentapp.api.infra.adapters.out.storage;

import com.studentapp.api.domain.port.in.MaterialUseCase;
import com.studentapp.api.domain.port.out.FileStorageServicePort;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

import java.util.UUID;

@Component
@Profile("s3")
public class S3FileStorageAdapter implements FileStorageServicePort {

    private final S3Client s3Client;
    private final String bucketName;

    public S3FileStorageAdapter(S3Client s3Client, @Value("${app.aws.s3.bucket-name}") String bucketName) {
        this.s3Client = s3Client;
        this.bucketName = bucketName;
    }

    @Override
    public StorageDetails save(MaterialUseCase.FileInput file){
        try {
            String path = "materials/" + UUID.randomUUID() + "-" + file.originalFilename();

            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(path)
                    .contentType(file.contentType())
                    .contentLength(file.size())
                    .build();

            PutObjectResponse response = s3Client.putObject(putObjectRequest, RequestBody.fromInputStream(file.stream(), file.size()));

            return new StorageDetails(
                    file.originalFilename(),
                    "S3",
                    bucketName,
                    path,
                    file.contentType(),
                    file.size(),
                    response.eTag()
            );
        } catch (Exception e) {
            throw new RuntimeException("Erro ao salvar arquivo no S3.", e);
        }
    }

}
