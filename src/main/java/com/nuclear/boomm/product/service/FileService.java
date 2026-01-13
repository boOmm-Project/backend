package com.nuclear.boomm.product.service;

import com.nuclear.boomm.product.domain.ProductFile;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileService {
    private final S3Client s3Client;

    private final String bucket = "product-files";

    @Value("${app.storage.base-url}")
    private String baseUrl;

    // 다중 파일 업로드
    @Transactional(rollbackFor = Exception.class)
    public List<ProductFile> uploadFiles (
            Long userId,
            Long productId,
            List<MultipartFile> files
    ) throws IOException {
        List<ProductFile> responses = new ArrayList<>();

        for (MultipartFile file : files) {
            String originalName = file.getOriginalFilename();
            String extension = "";
            if (originalName != null && originalName.lastIndexOf(".") != -1) {
                extension = originalName.substring(originalName.lastIndexOf("."));
            }

            String uuidFileName = UUID.randomUUID() + extension;
            String url = baseUrl + uuidFileName;

            PutObjectRequest request = PutObjectRequest.builder()
                    .bucket(bucket)
                    .key(uuidFileName)
                    .contentType(file.getContentType())
                    .build();

            ProductFile productFile = ProductFile.builder()
                    .bucketName(bucket)
                    .url(url)
                    .uuidName(uuidFileName)
                    .originalFilename(originalName)
                    .extension(extension)
                    .contentType(file.getContentType())
                    .fileSize(file.getSize())
                    .uploaderId(userId)
                    .productId(productId)
                    .build();

            s3Client.putObject(request, software.amazon.awssdk.core.sync.RequestBody.fromBytes(file.getBytes()));

            responses.add(productFile);
        }

        return responses;
    }

    // 다중 파일 삭제
    @Transactional(rollbackFor = Exception.class)
    public void deleteFiles(List<String> uuidNames) {
        for (String uuidName : uuidNames) {
            DeleteObjectRequest request = DeleteObjectRequest.builder()
                    .bucket(bucket)
                    .key(uuidName)
                    .build();
            s3Client.deleteObject(request);
        }
    }
}
