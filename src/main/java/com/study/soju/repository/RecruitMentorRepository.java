package com.study.soju.repository;

import com.study.soju.entity.RecruitMentor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecruitMentorRepository extends JpaRepository<RecruitMentor, Object> {

}
