package com.study.soju.repository;

import com.study.soju.entity.RecruitStudy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface RecruitStudyRepository extends JpaRepository<RecruitStudy, Object> {
    RecruitStudy findByIdx(long idx);

    @Modifying
    @Query("UPDATE RecruitStudy rs SET rs.studyLike = (SELECT COUNT(rsl) FROM RecruitStudyLike rsl WHERE rsl.likeIdx = :idx) WHERE rs.idx = :idx")
    void updateStudyLikeCount(@Param("idx") long idx);

}
