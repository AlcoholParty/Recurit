package com.study.soju.repository;

import com.study.soju.entity.RecruitStudy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface RecruitStudyRepository extends JpaRepository<RecruitStudy, Object> {
    RecruitStudy findByIdx(long idx);

    //서비스에서 받아온 pageable 정보로 리스트(Page 객체) 를 리턴한다.
    Page<RecruitStudy> findAll(Pageable pageable);

    @Modifying
    @Query("UPDATE RecruitStudy rs SET rs.studyLike = (SELECT COUNT(rsl) FROM RecruitStudyLike rsl WHERE rsl.likeIdx = :idx) WHERE rs.idx = :idx")
    void updateStudyLikeCount(@Param("idx") long idx);

    //idx 를 역순으로 정렬한 이후 거기서 페이징 처리 하기
    @Modifying
    @Query(value = "SELECT * FROM (SELECT *, RANK() OVER (ORDER BY idx DESC)AS ranking FROM RecruitStudy)AS ranking WHERE ranking BETWEEN :start AND :end", nativeQuery = true)
    List<RecruitStudy> findRecruitStudyList(@Param("start") int start, @Param("end") int end);

}
