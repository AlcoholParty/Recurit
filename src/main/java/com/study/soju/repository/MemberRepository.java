package com.study.soju.repository;

import com.study.soju.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Object> {
    //이메일로 검색
    Member findByEmailId(String emailId);
    //닉네임으로 검색
    Member findByNickname(String nickname);

}
