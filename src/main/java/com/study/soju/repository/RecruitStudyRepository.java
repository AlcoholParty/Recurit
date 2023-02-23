package com.study.soju.repository;

import com.study.soju.entity.RecruitStudy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecruitStudyRepository extends JpaRepository<RecruitStudy, Object> {

}
