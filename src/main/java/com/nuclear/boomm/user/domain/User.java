package com.nuclear.boomm.user.domain;

import com.nuclear.boomm.common.BaseEntity;
import com.nuclear.boomm.user.enums.Gender;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name="user")
@NoArgsConstructor
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private int age;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String idNum;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String zipCode;

    @Column(nullable = false)
    private String job;

    @Column(nullable = false)
    private boolean wedding;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Gender gender;

    @Column(nullable = false)
    private String phone;

    @Column(nullable = false)
    private String email;

    private String companyName;


}
