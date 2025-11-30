package com.nuclear.boomm.caraccident.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class AccidentFileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private String originalFileName;

    @Column(nullable = false)
    private String fileName;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String fileContent;

    @Column(nullable = false)
    private Long accidentIntakeId;

    @Builder
    public AccidentFileEntity(Long userId, String originalFileName, String fileName, String fileContent, Long accidentIntakeId) {
        this.userId = userId;
        this.originalFileName = originalFileName;
        this.fileName = fileName;
        this.fileContent = fileContent;
        this.accidentIntakeId = accidentIntakeId;
    }
}
