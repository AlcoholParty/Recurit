package com.study.soju.repository;

import com.study.soju.entity.RecruitStudyComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecruitStudyCommentRepository extends JpaRepository<RecruitStudyComment, Object> {
    RecruitStudyComment findByCommentIdx(Long commentIdx);
}
