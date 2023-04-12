package com.study.soju.repository;

import com.study.soju.entity.RecruitMenteeLike;
import com.study.soju.entity.RecruitMentorLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecruitMenteeLikeRepository extends JpaRepository<RecruitMenteeLike, Object> {
    //멘토프로필 idx 와 멤버 idx 로 멘토프로필 검색
    RecruitMenteeLike findByLikeIdxAndMemberIdx(long likeIdx, long memberIdx);
    //멤버 idx 로 멘토프로필 리스트 검색
    List<RecruitMenteeLike> findByMemberIdx(long memberIdx);

}
