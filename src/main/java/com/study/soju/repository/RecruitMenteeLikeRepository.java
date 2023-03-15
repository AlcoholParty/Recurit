package com.study.soju.repository;

import com.study.soju.entity.RecruitMenteeLike;
import com.study.soju.entity.RecruitMentorLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecruitMenteeLikeRepository extends JpaRepository<RecruitMenteeLike, Object> {
    RecruitMenteeLike findByLikeIdxAndMemberIdx(long likeIdx, long memberIdx);
}
