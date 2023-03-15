package com.study.soju.service;

import com.study.soju.entity.*;
import com.study.soju.repository.MemberRepository;
import com.study.soju.repository.RecruitMentorCommentRepository;
import com.study.soju.repository.RecruitMentorLikeRepository;
import com.study.soju.repository.RecruitMentorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecruitMentorService {
    @Autowired
    RecruitMentorRepository recruitMentorRepository;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    RecruitMentorCommentRepository recruitMentorCommentRepository;
    @Autowired
    RecruitMentorLikeRepository recruitMentorLikeRepository;

    //이메일로 닉네임 검색
    public String returnNickname(String emailId){
        Member member = memberRepository.findByEmailId(emailId);
        String nickname = member.getNickname();
        return nickname;
    }

    //글쓴내용 저장
    public void writeRecruitMentor(RecruitMentor recruitMentor){
        recruitMentorRepository.save(recruitMentor);
    }

    //리스트로 전체내용 뽑아오기
    public List<RecruitMentor> recruitMentorListAll() {
        return recruitMentorRepository.findAll();
    }

    //idx로 필요한 recruitMentor 객체 보내주기
    public RecruitMentor findRecruitMentor(Long idx) {
        return recruitMentorRepository.findByIdx(idx);
    }

    //이메일을 가지고 유저 idx 반환하기
    public long returnIdx(String emailId) {
        Member member = memberRepository.findByEmailId(emailId);
        return member.getIdx();
    }

    //내용 변경
    public String modify(RecruitMentor recruitMentor){
        String res = "no";
        if(recruitMentor != null){
            recruitMentorRepository.save(recruitMentor);
            res = "yes";
        }
        return res;
    }

    //내용 삭제
    public String delete(RecruitMentor recruitMentor) {
        String res = "no";
        recruitMentorRepository.delete(recruitMentor);
        res = "yes";
        return res;
    }

    //댓글 전체 리스트
    public List<RecruitMentorComment> findCommentList(long commentIdx) {
        List<RecruitMentorComment> recruitMentorCommentList = recruitMentorCommentRepository.findByCommentIdx(commentIdx);
        return recruitMentorCommentList;
    }

    //댓글 저장
    public String saveComment(RecruitMentorComment recruitMentorComment) {
        String res = "no";
        if(recruitMentorComment != null) {
            recruitMentorCommentRepository.save(recruitMentorComment);
            res = "yes";
        }
        return res;
    }

    //댓글 삭제
    public String deleteComment(long idx) {
        String res = "no";
        RecruitMentorComment deleteComment = recruitMentorCommentRepository.findByIdx(idx);
        if(deleteComment != null) {
            deleteComment.setDeleteCheck(1);
            recruitMentorCommentRepository.save(deleteComment);
            res = "yes";
        }
        return res;
    }

    //댓글 수정
    public String modifyComment(RecruitMentorComment recruitMentorComment) {
        String res = "no";
        RecruitMentorComment beforeModify = recruitMentorCommentRepository.findByIdx(recruitMentorComment.getIdx());
        if(beforeModify != null) {
            beforeModify.setComment(recruitMentorComment.getComment());
            beforeModify.setWriteDate(recruitMentorComment.getWriteDate());
            recruitMentorCommentRepository.save(beforeModify);
            res = "yes";
        }
        return res;
    }

    //좋아요값 수정하기
    public RecruitMentor likeUpdate(RecruitMentor recruitMentor, RecruitMentorLike recruitMentorLike) {
        RecruitMentorLike recruitMentorLike1 = recruitMentorLikeRepository.findByLikeIdxAndMemberIdx(recruitMentorLike.getLikeIdx(), recruitMentorLike.getMemberIdx());
        if (recruitMentorLike1 == null) {
            recruitMentorLikeRepository.save(recruitMentorLike);
        }else {
            recruitMentorLikeRepository.delete(recruitMentorLike1);
        }
        recruitMentorRepository.updateMentorLikeCount(recruitMentor.getIdx());
        RecruitMentor afterRecruitMentor = recruitMentorRepository.findByIdx(recruitMentor.getIdx());
        return afterRecruitMentor;
    }

    //check 값을 확인해서 변경하기위한 메서드
    public int likeCheck(long likeIdx, long memberIdx) {
        int count = 0;
        RecruitMentorLike recruitMentorLike = recruitMentorLikeRepository.findByLikeIdxAndMemberIdx(likeIdx, memberIdx);
        if(recruitMentorLike != null) {
            count = 1;
        }
        return count;
    }
}
