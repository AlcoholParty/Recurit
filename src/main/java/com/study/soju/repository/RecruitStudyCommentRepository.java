package com.study.soju.repository;

import com.study.soju.entity.RecruitStudyComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecruitStudyCommentRepository extends JpaRepository<RecruitStudyComment, Object> {
    RecruitStudyComment findByIdx(long idx);

    List<RecruitStudyComment> findByCommentIdx(long commentIdx);
}
