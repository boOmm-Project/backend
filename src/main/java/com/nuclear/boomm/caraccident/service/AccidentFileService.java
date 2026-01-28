package com.nuclear.boomm.caraccident.service;


import com.nuclear.boomm.caraccident.domain.AccidentFileEntity;
import com.nuclear.boomm.caraccident.dto.response.AccidentIntakeFileDTO;
import com.nuclear.boomm.caraccident.exception.IntakeFileNotFoundException;
import com.nuclear.boomm.caraccident.repository.AccidentFileRepository;
import com.nuclear.boomm.common.error.ErrorCode;
import com.nuclear.boomm.product.error.CustomException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class AccidentFileService {
    private final S3Client s3Client;
    private final String bucketName;
    private final AccidentFileRepository accidentFileRepository;
    private final String url;

    public AccidentFileService(S3Client s3Client, @Value("${s3.intake-bucket}") String bucketName, AccidentFileRepository accidentFileRepository, @Value("${app.storage.intake-url}") String url) {
        this.s3Client = s3Client;
        this.bucketName = bucketName;
        this.accidentFileRepository = accidentFileRepository;
        this.url = url;
    }

    @Transactional
    public Long uploadImage(Long userId,Long intakeId, MultipartFile file){
        String uuidName = UUID.randomUUID().toString()+file.getOriginalFilename();
        try{
            PutObjectRequest request =
            PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(uuidName)
                    .contentType(file.getContentType())
                    .build();
            s3Client.putObject(
                    request, RequestBody.fromBytes(file.getBytes())
            );
            AccidentFileEntity accidentFileEntity = AccidentFileEntity.builder()
                    .userId(userId)
                    .originalFileName(file.getOriginalFilename())
                    .fileName(uuidName)
                    .fileContent(url+uuidName)
                    .accidentIntakeId(intakeId)
                    .build();
            return accidentFileRepository.save(accidentFileEntity).getId();
        }
        catch(IOException e){
            throw new RuntimeException(e);
        }
    }

    /**
     * 단일 파일에 대한 반환
     */
    public AccidentIntakeFileDTO getIntakeFile(Long userId, Long fileId){
        AccidentFileEntity entity = accidentFileRepository.findById(fileId).orElseThrow(()-> new IntakeFileNotFoundException(fileId + "의 아이디를 가지고있는 파일이 존재하지 않습니다."));
        return new AccidentIntakeFileDTO(entity.getId(), entity.getFileContent());
    }

    public List<AccidentIntakeFileDTO> getIntakeAllFile(Long userId, Long intakeId){
        List<AccidentFileEntity> entities = accidentFileRepository.findAllByUserIdAndAccidentIntakeId(userId, intakeId);
        List<AccidentIntakeFileDTO> dtos = new ArrayList<>();
        for (AccidentFileEntity entity: entities) {
            dtos.add(new AccidentIntakeFileDTO(entity.getId(), entity.getFileContent()));
        }

        return dtos;
    }

   @Transactional
    public void deleteFiles(Long userId,Long fileId) {
        AccidentFileEntity entity = accidentFileRepository.findById(fileId).orElseThrow(()-> new IntakeFileNotFoundException(fileId + "의 아이디를 가지고있는 파일이 존재하지 않습니다."));


        String uuidName = entity.getFileName();
        if (uuidName == null || uuidName.isEmpty()) {
            throw new CustomException(ErrorCode.FILE_DELETE_ERROR);
        }

            DeleteObjectRequest request = DeleteObjectRequest.builder()
                    .bucket(bucketName)
                    .key(uuidName)
                    .build();
            s3Client.deleteObject(request);
    }


}
