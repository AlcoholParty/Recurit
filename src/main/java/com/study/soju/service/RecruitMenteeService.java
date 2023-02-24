package com.study.soju.service;

import com.study.soju.entity.Member;
import com.study.soju.entity.RecruitMentee;
import com.study.soju.repository.MemberRepository;
import com.study.soju.repository.RecruitMenteeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecruitMenteeService {
    @Autowired
    RecruitMenteeRepository recruitMenteeRepository;
    @Autowired
    MemberRepository memberRepository;

    //글쓴내용 저장
    public void writeRecruitMentee(RecruitMentee recruitMentee) {
        recruitMenteeRepository.save(recruitMentee);
    }

    //이메일로 닉네임 검색
    public String returnNickname(String emailId) {
        Member member = memberRepository.findByEmailId(emailId);
        String nickname = member.getNickname();
        return nickname;
    }

    //리스트로 전체내용 뽑아오기
    public List<RecruitMentee> recruitMenteeListAll() {
        return recruitMenteeRepository.findAll();
    }

}
