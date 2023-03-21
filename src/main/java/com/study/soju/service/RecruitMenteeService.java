package com.study.soju.service;

import com.study.soju.entity.*;
import com.study.soju.repository.MemberRepository;
import com.study.soju.repository.RecruitMenteeCommentRepository;
import com.study.soju.repository.RecruitMenteeLikeRepository;
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

    @Autowired
    RecruitMenteeCommentRepository recruitMenteeCommentRepository;

    @Autowired
    RecruitMenteeLikeRepository recruitMenteeLikeRepository;

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

    //idx 로 글 뽑아가기
    public RecruitMentee findRecruitMentee(Long idx) {
        RecruitMentee recruitMentee = recruitMenteeRepository.findByIdx(idx);
        return recruitMentee;
    }

    //아이디로 유저 idx 보내주기
    public long returnIdx(String emailId) {
        Member member = memberRepository.findByEmailId(emailId);
        return member.getIdx();
    }

    //글 수정하기
    public String modify(RecruitMentee recruitMentee){
        String res = "no";
        if(recruitMentee != null){
            recruitMenteeRepository.save(recruitMentee);
            res = "yes";
        }
        return res;
    }

    //글 삭제하기
    public String delete(RecruitMentee recruitMentee) {
        String res = "no";
        recruitMenteeRepository.delete(recruitMentee);
        res = "yes";
        return res;
    }

    //댓글 전체 리스트
    public List<RecruitMenteeComment> findCommentList(long commentIdx) {
        List<RecruitMenteeComment> recruitMenteeCommentList = recruitMenteeCommentRepository.findByCommentIdx(commentIdx);
        return recruitMenteeCommentList;
    }

    //댓글 저장
    public String saveComment(RecruitMenteeComment recruitMenteeComment) {
        String res = "no";
        if(recruitMenteeComment != null) {
            recruitMenteeCommentRepository.save(recruitMenteeComment);
            res = "yes";
        }
        return res;
    }

    //댓글 삭제
    public String deleteComment(long idx) {
        String res = "no";
        RecruitMenteeComment deleteComment = recruitMenteeCommentRepository.findByIdx(idx);
        if(deleteComment != null) {
            deleteComment.setDeleteCheck(1);
            recruitMenteeCommentRepository.save(deleteComment);
            res = "yes";
        }
        return res;
    }

    //댓글 수정
    public String modifyComment(RecruitMenteeComment recruitMenteeComment) {
        String res = "no";
        RecruitMenteeComment beforeModify = recruitMenteeCommentRepository.findByIdx(recruitMenteeComment.getIdx());
        if(beforeModify != null) {
            beforeModify.setComment(recruitMenteeComment.getComment());
            beforeModify.setWriteDate(recruitMenteeComment.getWriteDate());
            recruitMenteeCommentRepository.save(beforeModify);
            res = "yes";
        }
        return res;
    }

    //글 좋아요 눌렀는지 확인하기
    public RecruitMentee likeUpdate(RecruitMenteeLike recruitMenteeLike) {
        RecruitMenteeLike recruitMenteeLike1 = recruitMenteeLikeRepository.findByLikeIdxAndMemberIdx(recruitMenteeLike.getLikeIdx(), recruitMenteeLike.getMemberIdx());
        RecruitMentee afterRecruitMentee = null;
        if (recruitMenteeLike1 == null) {
            recruitMenteeLikeRepository.save(recruitMenteeLike);
            recruitMenteeRepository.updateMenteeLikeCount(recruitMenteeLike.getLikeIdx());
            afterRecruitMentee = recruitMenteeRepository.findByIdx(recruitMenteeLike.getLikeIdx());
        }else {
            recruitMenteeLikeRepository.delete(recruitMenteeLike1);
            recruitMenteeRepository.updateMenteeLikeCount(recruitMenteeLike1.getLikeIdx());
            afterRecruitMentee = recruitMenteeRepository.findByIdx(recruitMenteeLike1.getLikeIdx());
        }
        return afterRecruitMentee;
    }

    //check 값을 확인해서 변경하기위한 메서드
    public int likeCheck(long likeIdx, long memberIdx) {
        int count = 0;
        RecruitMenteeLike recruitMenteeLike = recruitMenteeLikeRepository.findByLikeIdxAndMemberIdx(likeIdx, memberIdx);
        if(recruitMenteeLike != null) {
            count = 1;
        }
        return count;
    }
}
