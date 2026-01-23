package com.nuclear.boomm.product.service;

import com.nuclear.boomm.common.error.ErrorCode;
import com.nuclear.boomm.product.domain.ProductFile;
import com.nuclear.boomm.product.error.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.util.Collections;
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
    public List<ProductFile> uploadFiles(
            Long userId,
            Long productId,
            List<MultipartFile> files
    ) throws IOException {
        // file null 체크
        if (files == null || files.isEmpty()) {
            return Collections.emptyList(); // null 처리
        }

        return files.stream()
                .map(file -> uploadSingleFile(userId, productId, file))
                .toList();
    }

    // 다중 파일 삭제
    public void deleteFiles(List<String> uuidNames) {
        if (uuidNames == null || uuidNames.isEmpty()) {
            throw new CustomException(ErrorCode.FILE_DELETE_ERROR);
        }

        for (String uuidName : uuidNames) {
            DeleteObjectRequest request = DeleteObjectRequest.builder()
                    .bucket(bucket)
                    .key(uuidName)
                    .build();
            s3Client.deleteObject(request);
        }
    }

    private ProductFile uploadSingleFile(Long userId, Long productId, MultipartFile file) {
        try {
            // 파일 정보 추출
            String extension = getFileExtension(file.getOriginalFilename());
            String uuidFileName = UUID.randomUUID() + extension;
            String url = baseUrl + uuidFileName;

            // S3 업로드
            uploadToS3(file, uuidFileName);

            // ProductFile 생성
            return ProductFile.builder()
                    .bucketName(bucket)
                    .url(url)
                    .uuidName(uuidFileName)
                    .originalFilename(file.getOriginalFilename())
                    .extension(extension)
                    .contentType(file.getContentType())
                    .fileSize(file.getSize())
                    .uploaderId(userId)
                    .productId(productId)
                    .build();

        } catch (IOException e) {
            throw new CustomException(ErrorCode.FILE_UPLOAD_ERROR);
        }
    }

    // 확장자 추출
    private String getFileExtension(String originalFilename) {
        if (originalFilename != null && originalFilename.lastIndexOf(".") != -1) {
            return originalFilename.substring(originalFilename.lastIndexOf("."));
        }
        throw new CustomException(ErrorCode.INVALID_FILE_FORMAT);
    }

    // S3 업로드 로직
    private void uploadToS3(MultipartFile file, String fileName) throws IOException {
        PutObjectRequest request = PutObjectRequest.builder()
                .bucket(bucket)
                .key(fileName)
                .contentType(file.getContentType())
                .build();

        s3Client.putObject(request, RequestBody.fromBytes(file.getBytes()));
    }
}
