package com.example.springbootcommunityweb.domain;


import com.example.springbootcommunityweb.domain.enums.SocialType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;


@Getter
@NoArgsConstructor
@Entity
@Table
public class User implements Serializable {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Column
    private String name;

    @Column
    private String password;

    @Column
    private String email;

    @Column
    private String principal; // oauth 인증으로 제공받는 키 값

    @Column
    @Enumerated(value = EnumType.STRING)
    private SocialType socialType;

    @Column
    private LocalDateTime createDate;

    @Column
    private LocalDateTime updatedDate;

    @Builder
    public User(String name, String password, String email, String principal, SocialType socialType, LocalDateTime createDate, LocalDateTime updatedDate) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.principal = principal;
        this.socialType = socialType;
        this.createDate = createDate;
        this.updatedDate = updatedDate;
    }
}
