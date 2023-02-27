package com.study.soju.repository;

import com.study.soju.entity.RecruitStudyLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecruitStudyLikeRepository extends JpaRepository<RecruitStudyLike, Object> {
    RecruitStudyLike findByRecruitStudyIdxAndNickname(Long recruitStudyIdx, String nickName);

    int countByRecruitStudyIdx(Long recruitStudyIdx);
}
