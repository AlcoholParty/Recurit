package com.study.soju.service;

import com.study.soju.entity.*;
import com.study.soju.repository.MemberRepository;
import com.study.soju.repository.RecruitStudyCommentRepository;
import com.study.soju.repository.RecruitStudyLikeRepository;
import com.study.soju.repository.RecruitStudyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecruitStudyService {
    @Autowired
    RecruitStudyRepository recruitStudyRepository;

    @Autowired
    RecruitStudyLikeRepository recruitStudyLikeRepository;

    @Autowired
    RecruitStudyCommentRepository recruitStudyCommentRepository;

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

    public long returnIdx(String emailId) {
        Member member = memberRepository.findByEmailId(emailId);
        return member.getIdx();
    }

    //리스트로 전체내용 뽑아오기
    public List<RecruitStudy> recruitStudyListAll() {
        return recruitStudyRepository.findAll();
    }

    //title 로 객체 찾기
    public RecruitStudy findRecruitStudy(long idx) {
        RecruitStudy recruitStudy = recruitStudyRepository.findByIdx(idx);
        return recruitStudy;
    }

    //check 값을 확인해서 변경하기위한 메서드
    public int likeCheck(long likeIdx, long memberIdx) {
        int count = 0;
        RecruitStudyLike recruitStudyLike = recruitStudyLikeRepository.findByLikeIdxAndMemberIdx(likeIdx, memberIdx);
        if(recruitStudyLike != null) {
            count = 1;
        }
        return count;
    }

    //좋아요값 수정하기
    public RecruitStudy likeUpdate(RecruitStudy recruitStudy, RecruitStudyLike recruitStudyLike) {
        //idx를 가지고 어떤 글인지 확인하고
        //글의 객체를 가지고옴
        //RecruitStudy beforeRecruitStudy = recruitStudyRepository.findByIdx(recruitStudy.getIdx());
        //좋아요 db 수정
        //studyIdx 와 memberIdx 로 객체가 있는지 조회한뒤 null 값이면 값이 없는것 이므로 데이터 저장
        //만약 값이 있다면 그 저장된 정보를 삭제
        RecruitStudyLike recruitStudyLike1 = recruitStudyLikeRepository.findByLikeIdxAndMemberIdx(recruitStudyLike.getLikeIdx(), recruitStudyLike.getMemberIdx());
        if (recruitStudyLike1 == null) {
            recruitStudyLikeRepository.save(recruitStudyLike);
        }else {
            //저장이 되어있는거니깐 데이터베이스 삭제
            recruitStudyLikeRepository.delete(recruitStudyLike1);
        }
        //like 의 갯수는 studyIdx 로 저장된 정보들 갯수를 카운트 해서 like 에 저장
        //int studyLike = recruitStudyLikeRepository.countByLikeIdx(recruitStudyLike.getLikeIdx());
        //beforeRecruitStudy.setStudyLike(studyLike);
        //RecruitStudy afterRecruitStudy = recruitStudyRepository.save(beforeRecruitStudy);
        //RecruitStudy afterRecruitStudy = recruitStudyLikeRepository.updateStudyLikeCount(recruitStudyLike.getLikeIdx());
        recruitStudyLikeRepository.updateStudyLikeCount(recruitStudy.getIdx());
        RecruitStudy afterRecruitStudy = recruitStudyRepository.findByIdx(recruitStudy.getIdx());
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
    public String delete(RecruitStudy recruitStudy) {
        recruitStudyRepository.delete(recruitStudy);
        return "yes";
    }

    //전체 댓글 리스트
    public List<RecruitStudyComment> findCommentList(long commentIdx) {
        List<RecruitStudyComment> recruitStudyCommentList = recruitStudyCommentRepository.findByCommentIdx(commentIdx);
        return recruitStudyCommentList;
    }

    //댓글 저장
    public String saveComment(RecruitStudyComment recruitStudyComment) {
        String res = "no";
        if (recruitStudyComment != null) {
            recruitStudyCommentRepository.save(recruitStudyComment);
            res = "yes";
        }
        return res;
    }

    //댓글 삭제
    public String deleteComment(long idx) {
        String res = "no";
        RecruitStudyComment deleteComment = recruitStudyCommentRepository.findByIdx(idx);
        if(deleteComment != null) {
            deleteComment.setDeleteCheck(1);
            recruitStudyCommentRepository.save(deleteComment);
            res = "yes";
        }
        return res;
    }

    //댓글 수정
    public String modifyComment(RecruitStudyComment recruitStudyComment) {
        String res = "no";
        RecruitStudyComment beforeModify = recruitStudyCommentRepository.findByIdx(recruitStudyComment.getIdx());
        if(beforeModify != null) {
            beforeModify.setComment(recruitStudyComment.getComment());
            beforeModify.setWriteDate(recruitStudyComment.getWriteDate());
            recruitStudyCommentRepository.save(beforeModify);
            res = "yes";
        }
        return res;
    }

}
