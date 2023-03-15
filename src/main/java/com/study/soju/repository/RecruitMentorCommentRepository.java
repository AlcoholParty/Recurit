package com.study.soju.repository;

import com.study.soju.entity.RecruitMentorComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecruitMentorCommentRepository extends JpaRepository<RecruitMentorComment, Object> {
    RecruitMentorComment findByIdx(long idx);

    List<RecruitMentorComment> findByCommentIdx(long commentIdx);
}
