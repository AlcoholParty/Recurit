package com.study.soju.service;

import com.study.soju.entity.Member;
import com.study.soju.entity.RecruitStudy;
import com.study.soju.repository.MemberRepository;
import com.study.soju.repository.RecruitStudyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecruitStudyService {
    @Autowired
    RecruitStudyRepository recruitStudyRepository;

    @Autowired
    MemberRepository memberRepository;

    //글쓴내용 저장
    public void writeRecruitStudy(RecruitStudy recruitStudy){
        recruitStudyRepository.save(recruitStudy);
    }

    //이메일로 닉네임 검색
    public String returnNickname(String emailId){
        Member member = memberRepository.findByEmailId(emailId);
        String nickname = member.getNickname();
        return nickname;
    }

    //리스트로 전체내용 뽑아오기
    public List<RecruitStudy> recruitStudyListAll() {
        return recruitStudyRepository.findAll();
    }
}
