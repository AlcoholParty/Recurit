package com.study.soju.repository;

import com.study.soju.entity.RecruitStudyLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface RecruitStudyLikeRepository extends JpaRepository<RecruitStudyLike, Object> {
    RecruitStudyLike findByLikeIdxAndMemberIdx(long likeIdx, long memberIdx);

    //countBy 로 검색할 컬럼의 갯수를 반환한다.
    //countBy 는 예약어처럼 항상 앞에 작성해줘야한다.
    //마치 findBy...처럼
    int countByLikeIdx(long likeIdx);

    //SELECT * FROM RecruitStudy rs WHERE rs.studyLike = (SELECT COUNT(*) FROM RecruitStudyLike rsl WHERE rsl.likeIdx = rs.idx);
    //@Query("SELECT rs FROM RecruitStudy rs WHERE rs.studyLike = (SELECT COUNT(rsl.recruitStudyIdx) FROM RecruitStudyLike rsl WHERE rsl.recruitStudyIdx = :recruitStudyIdx)")
    //int findByRecruitStudyIdx(@Param("commentIdx") long commentIdx);
    //UPDATE RecruitStudy rs SET studyLike = (SELECT COUNT(*) FROM RecruitStudyLike rsl WHERE rsl.recruitStudyIdx = rs.idx) WHERE rs.idx = 4;
    //@Modifying
    //@Query("UPDATE RecruitStudy rs SET rs.studyLike = (SELECT COUNT(rsl) FROM RecruitStudyLike rsl WHERE rsl.recruitStudy.idx = :studyIdx) WHERE rs.idx = :studyIdx")
    //RecruitStudy updateStudyLikeCount(@Param("studyIdx") Long studyIdx);


}
