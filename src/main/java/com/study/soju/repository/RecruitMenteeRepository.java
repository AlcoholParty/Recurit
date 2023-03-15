package com.study.soju.repository;

import com.study.soju.entity.RecruitMentee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface RecruitMenteeRepository extends JpaRepository<RecruitMentee, Object> {
    RecruitMentee findByIdx(Long idx);

    @Modifying
    @Query("UPDATE RecruitMentee rm SET rm.studyLike = (SELECT COUNT(rml) FROM RecruitMenteeLike rml WHERE rml.likeIdx = :idx) WHERE rm.idx = :idx")
    void updateMenteeLikeCount(@Param("idx") long idx);
}
