package com.study.soju.service;

import com.study.soju.entity.*;
import com.study.soju.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
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

    @Autowired
    AlarmRepository alarmRepository;

    //닉네임으로 emailId 가져오기
    public String returnEmailId(String nickname) {
        return memberRepository.findByNickname(nickname).getEmailId();
    }

    public int rowTotal() {
        return Long.valueOf(recruitMentorRepository.count()).intValue();
    }

    public List<RecruitMentor> recruitMentorListAll(HashMap<String, Integer> map) {
        List<RecruitMentor> recruitMentorList = recruitMentorRepository.findRecruitMentorList(map.get("start"), map.get("end"));
        return recruitMentorList;
    }

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

//    //리스트로 전체내용 뽑아오기
//    public List<RecruitMentor> recruitMentorListAll() {
//        return recruitMentorRepository.findAll();
//    }

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
    public RecruitMentor likeUpdate(RecruitMentorLike recruitMentorLike ,Alarm alarm) {
        RecruitMentorLike recruitMentorLike1 = recruitMentorLikeRepository.findByLikeIdxAndMemberIdx(recruitMentorLike.getLikeIdx(), recruitMentorLike.getMemberIdx());
        RecruitMentor afterRecruitMentor = null;
        if (recruitMentorLike1 == null) {
            recruitMentorLikeRepository.save(recruitMentorLike);
            recruitMentorRepository.updateMentorLikeCount(recruitMentorLike.getLikeIdx());
            afterRecruitMentor = recruitMentorRepository.findByIdx(recruitMentorLike.getLikeIdx());
            //좋아요를 눌렀을때 알람 생성
            alarm.setTitle(alarm.getNickname() + "님 이 좋아요를 눌렀어요");
            //알람 생성
            alarmRepository.save(alarm);
        }else {
            recruitMentorLikeRepository.delete(recruitMentorLike1);
            recruitMentorRepository.updateMentorLikeCount(recruitMentorLike1.getLikeIdx());
            afterRecruitMentor = recruitMentorRepository.findByIdx(recruitMentorLike1.getLikeIdx());
        }
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

    //멘토 신청
    public String mentorApply(Alarm alarm) {
        String res = "no";
        //신청이 두번 가지않게 하기위해서 알람 타입, 이메일, 닉네임, 스터디글 idx 를 확인해서 있으면 중복이므로 알람을 생성하지 않는다.
        Alarm alarm1 = alarmRepository.findByAlarmTypeAndEmailIdAndNicknameAndRecruitStudyIdx(alarm.getAlarmType(),
                alarm.getEmailId(),
                alarm.getNickname(),
                alarm.getRecruitStudyIdx());
        if(alarm1 != null) {
            res = "exist";
        } else {
            alarm.setTitle(alarm.getNickname() + "님 이 멘토 신청을 했습니다.");
            alarmRepository.save(alarm);
            res = "yes";
        }
        return res;
    }
}
