package com.studentapp.api.infra.adapters.out.storage;

import com.studentapp.api.domain.port.in.MaterialUseCase;
import com.studentapp.api.domain.port.out.FileStorageServicePort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.util.UUID;

@Component
@Profile("s3")
public class S3FileStorageAdapter implements FileStorageServicePort {

    private static final Logger log = LoggerFactory.getLogger(S3FileStorageAdapter.class);

    private final S3Client s3Client;
    private final String bucketName;

    public S3FileStorageAdapter(S3Client s3Client, @Value("${app.aws.s3.bucket-name}") String bucketName) {
        this.s3Client = s3Client;
        this.bucketName = bucketName;
    }

    @Override
    public StorageDetails save(MaterialUseCase.FileInput file, String path){

        log.info("Salvando arquivo no S3. Bucket: {}, Key: {}", bucketName, path);

        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(path)
                .contentType(file.contentType())
                .contentLength(file.size())
                .build();

        try {
            PutObjectResponse response = s3Client.putObject(putObjectRequest, RequestBody.fromInputStream(file.stream(), file.size()));

            log.info("Arquivo salvo no S3. Bucket: {}, Key: {}, ETag: {}", bucketName, path, response.eTag());

            return new StorageDetails(
                    file.originalFilename(),
                    "S3",
                    bucketName,
                    path,
                    file.contentType(),
                    file.size(),
                    response.eTag()
            );
        } catch (S3Exception e) {

            log.error("Erro ao salvar arquivo no S3. Key: {}. Erro: {}", path, e.awsErrorDetails().errorMessage(), e);

            throw new RuntimeException("Erro ao salvar arquivo no S3.", e);
        }
    }

    @Override
    public void delete(String filePath){

        log.info("Deletando arquivo no S3. Bucket: {}, Key: {}", bucketName, filePath);

        DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                .bucket(bucketName)
                .key(filePath)
                .build();

        try {
            s3Client.deleteObject(deleteObjectRequest);

            log.info("Arquivo deletado com sucesso do S3. Bucket: {}, Key: {}", bucketName, filePath);

        } catch (S3Exception e) {

            log.error("Erro ao deletar arquivo no S3. Key: {}. Erro: {}", filePath, e.awsErrorDetails().errorMessage(), e);

            throw new RuntimeException("Erro ao deletar arquivo no S3.", e);
        }
    }

}
