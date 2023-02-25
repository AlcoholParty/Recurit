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
    public void writeRecruitStudy(RecruitStudy recruitStudy) {
        recruitStudyRepository.save(recruitStudy);
    }

    //이메일로 닉네임 검색
    public String returnNickname(String emailId) {
        Member member = memberRepository.findByEmailId(emailId);
        String nickname = member.getNickname();
        return nickname;
    }

    //리스트로 전체내용 뽑아오기
    public List<RecruitStudy> recruitStudyListAll() {
        return recruitStudyRepository.findAll();
    }

    //title 로 객체 찾기
    public RecruitStudy findRecruitStudy(Long idx) {
        RecruitStudy recruitStudy = recruitStudyRepository.findByIdx(idx);
        return recruitStudy;
    }

    //좋아요값 수정하기
    public RecruitStudy likeUpdate(RecruitStudy recruitStudy) {
        Long idx = recruitStudy.getIdx();
        int studyLike = recruitStudy.getStudyLike();
        int studyLikeCheck = recruitStudy.getStudyLikeCheck();
        RecruitStudy beforeRecruitStudy = recruitStudyRepository.findByIdx(idx);
        beforeRecruitStudy.setStudyLike(studyLike);
        beforeRecruitStudy.setStudyLikeCheck(studyLikeCheck);
        RecruitStudy afterRecruitStudy = recruitStudyRepository.save(beforeRecruitStudy);
        return afterRecruitStudy;
    }

    //업데이트 쿼리문
    public String modify(RecruitStudy recruitStudy) {
        RecruitStudy beforeRecruitStudy = recruitStudyRepository.findByIdx(recruitStudy.getIdx());
        if(beforeRecruitStudy.getRecruitingPersonnel() > recruitStudy.getPersonnel()) {
            //모집중인 인원이 바꾸려는 총 인원수보다 많으면 안된다.
            return "fail";
        } else{
            if(beforeRecruitStudy.getRecruitingPersonnel() == recruitStudy.getPersonnel()) {
                //만약 바꾸려는 인원수와 모아진 인원수가 같다면 모집중을 모집완료로 변경한다
                beforeRecruitStudy.setRecruiting(1);
            }else{
                //바꾸려는 인원이 모아진 인원수보다 많다면 모집중으로 변경
                beforeRecruitStudy.setRecruiting(0);
            }
            beforeRecruitStudy.setTitle(recruitStudy.getTitle());//제목변경
            beforeRecruitStudy.setStudyType(recruitStudy.getStudyType());//타입 변경
            beforeRecruitStudy.setPersonnel(recruitStudy.getPersonnel());//인원수 변경
            beforeRecruitStudy.setImage(recruitStudy.getImage());//이미지 변경
            beforeRecruitStudy.setStudyIntro(recruitStudy.getStudyIntro());//내용 변경
            recruitStudyRepository.save(beforeRecruitStudy);
            return "success";
        }
    }

    //내용 삭제
    public String delete(RecruitStudy recruitStudy){
        recruitStudyRepository.delete(recruitStudy);
        return "yes";
    }

}
