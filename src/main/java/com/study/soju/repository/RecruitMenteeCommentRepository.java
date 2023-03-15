package com.study.soju.repository;

import com.study.soju.entity.RecruitMenteeComment;
import com.study.soju.entity.RecruitMentorComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecruitMenteeCommentRepository extends JpaRepository<RecruitMenteeComment, Object> {
    RecruitMenteeComment findByIdx(long idx);
    List<RecruitMenteeComment> findByCommentIdx(long commentIdx);
}
