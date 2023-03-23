package com.study.soju.repository;

import com.study.soju.entity.RecruitMentee;
import com.study.soju.entity.RecruitMentor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface RecruitMenteeRepository extends JpaRepository<RecruitMentee, Object> {
    RecruitMentee findByIdx(Long idx);

    RecruitMentee findByWriter(String writer);

    @Modifying
    @Query("UPDATE RecruitMentee rm SET rm.studyLike = (SELECT COUNT(rml) FROM RecruitMenteeLike rml WHERE rml.likeIdx = :idx) WHERE rm.idx = :idx")
    void updateMenteeLikeCount(@Param("idx") long idx);

    @Modifying
    @Query(value = "SELECT * FROM (SELECT *, RANK() OVER (ORDER BY idx DESC)AS ranking FROM RecruitMentee)AS ranking WHERE ranking BETWEEN :start AND :end", nativeQuery = true)
    List<RecruitMentee> findRecruitMenteeList(@Param("start") int start, @Param("end") int end);
}
