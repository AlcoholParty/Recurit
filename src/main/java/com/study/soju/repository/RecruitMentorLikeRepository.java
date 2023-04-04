package com.study.soju.repository;

import com.study.soju.entity.RecruitMenteeLike;
import com.study.soju.entity.RecruitMentorLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface RecruitMentorLikeRepository extends JpaRepository<RecruitMentorLike, Object> {
    RecruitMentorLike findByLikeIdxAndMemberIdx(long likeIdx, long memberIdx);

    List<RecruitMentorLike> findByMemberIdx(long memberIdx);
}
