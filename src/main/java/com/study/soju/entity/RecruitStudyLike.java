package com.study.soju.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity(name = "RecruitStudyLike")
public class RecruitStudyLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long recruitStudyLikeIdx;

    @Column(nullable = false)
    private Long recruitStudyIdx;
    //나중에 포린키로 연결하고 지금은 그냥 저장 하는거로

    @Column(length = 50, nullable = false)
    private String nickname;
    //이것도 나중에 포린키로 연결

}
