package com.study.soju.repository;

import com.study.soju.entity.RecruitMentor;
import com.study.soju.entity.RecruitStudy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface RecruitMentorRepository extends JpaRepository<RecruitMentor, Object> {
    RecruitMentor findByIdx(Long idx);

    @Modifying
    @Query("UPDATE RecruitMentor rm SET rm.studyLike = (SELECT COUNT(rml) FROM RecruitMentorLike rml WHERE rml.likeIdx = :idx) WHERE rm.idx = :idx")
    void updateMentorLikeCount(@Param("idx") long idx);

    @Modifying
    @Query(value = "SELECT * FROM (SELECT *, RANK() OVER (ORDER BY idx DESC)AS ranking FROM RecruitMentor)AS ranking WHERE ranking BETWEEN :start AND :end", nativeQuery = true)
    List<RecruitMentor> findRecruitMentorList(@Param("start") int start, @Param("end") int end);
}
