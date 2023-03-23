package com.study.soju.service;

import com.study.soju.entity.*;
import com.study.soju.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
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

    @Autowired
    AlarmRepository alarmRepository;

    //닉네임으로 emailId 가져오기
    public String returnEmailId(String nickname) {
        return memberRepository.findByNickname(nickname).getEmailId();
    }

    //페이징을 위한 전체 게시물 갯수 반환하기
    public int rowTotal() {
        //전체 카운트 갯수를 반환하느것은 long 타입이기때문에 int 로 형변환을 해준다.
        return Long.valueOf(recruitStudyRepository.count()).intValue();
    }

    public List<RecruitStudy> recruitStudyListAll() {
        return recruitStudyRepository.findRecruitStudyList();
    }

    public List<RecruitStudy> recruitStudyListAll(HashMap<String, Integer> map) {
        List<RecruitStudy> recruitStudyList = recruitStudyRepository.findRecruitStudyList(map.get("start"), map.get("end"));
        return recruitStudyList;
    }

    //글쓴내용 저장
    public void writeRecruitStudy(RecruitStudy recruitStudy) {
        //스터디원을 구할때 본인도 들어가기때문에 기본 설정값을 1로 잡아준다.
        recruitStudy.setRecruitingPersonnel(1);
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

//    //리스트로 전체내용 뽑아오기
//    이건 Page 클래스를 이용하는 방식
//    public Page<RecruitStudy> recruitStudyListAll(int page) { // page 파라미터를 받아서 현재 어느 페이지에 있는지 알려줌
//        //게시물을 역순으로 보여주기 위해서 Sort.Order 객체로 이루어진 리스트를 생성
//        //List<Sort.Order> sorts = new ArrayList<>();
//        //이후 리스트에 idx 를 역순으로 정렬한다.
//        //sorts.add(Sort.Order.desc("idx"));
//        //이후 파라미터값에 Sort.by(소트 리스트) 를 추가해서 page 리스트를 받아온다.
//        //맨처음에 studyLike로 정렬
//        Pageable pageable = PageRequest.of(page, 2, Sort.by("idx")); // 총 5개의 값들을 보여줄것이고 현재 페이지 위치를 알려줌
//        Page<RecruitStudy> recruitStudyList = recruitStudyRepository.findAll(pageable);
//        return recruitStudyList; // 그 정보를 가진 pageable 객체를 가지고 findAll 로 리스트를 가져운다(Page 객체지만 리스트와 비슷함)
//    }

    //title 로 객체 찾기
    public RecruitStudy findRecruitStudy(long idx) {
        RecruitStudy recruitStudy = recruitStudyRepository.findByIdx(idx);
        return recruitStudy;
    }

    //writer 로 객체 찾기
    public RecruitStudy findRecruitStudy(String writer) {
        return recruitStudyRepository.findByWriter(writer);
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
    public RecruitStudy likeUpdate(RecruitStudyLike recruitStudyLike, Alarm alarm) {
        //idx를 가지고 어떤 글인지 확인하고
        //글의 객체를 가지고옴
        //RecruitStudy beforeRecruitStudy = recruitStudyRepository.findByIdx(recruitStudy.getIdx());
        //좋아요 db 수정
        //studyIdx 와 memberIdx 로 객체가 있는지 조회한뒤 null 값이면 값이 없는것 이므로 데이터 저장
        //만약 값이 있다면 그 저장된 정보를 삭제
        RecruitStudyLike recruitStudyLike1 = recruitStudyLikeRepository.findByLikeIdxAndMemberIdx(recruitStudyLike.getLikeIdx(), recruitStudyLike.getMemberIdx());
        RecruitStudy afterRecruitStudy = null;
        if (recruitStudyLike1 == null) {
            recruitStudyLikeRepository.save(recruitStudyLike);
            recruitStudyRepository.updateStudyLikeCount(recruitStudyLike.getLikeIdx());
            afterRecruitStudy = recruitStudyRepository.findByIdx(recruitStudyLike.getLikeIdx());
            //좋아요를 눌렀을때 알람 생성
            alarm.setTitle(alarm.getNickname() + "님 이 좋아요를 눌렀어요");
            //알람 생성
            alarmRepository.save(alarm);
        }else {
            //저장이 되어있는거니깐 데이터베이스 삭제
            recruitStudyLikeRepository.delete(recruitStudyLike1);
            recruitStudyRepository.updateStudyLikeCount(recruitStudyLike1.getLikeIdx());
            afterRecruitStudy = recruitStudyRepository.findByIdx(recruitStudyLike1.getLikeIdx());
        }
        //like 의 갯수는 studyIdx 로 저장된 정보들 갯수를 카운트 해서 like 에 저장
        //int studyLike = recruitStudyLikeRepository.countByLikeIdx(recruitStudyLike.getLikeIdx());
        //beforeRecruitStudy.setStudyLike(studyLike);
        //RecruitStudy afterRecruitStudy = recruitStudyRepository.save(beforeRecruitStudy);
        //RecruitStudy afterRecruitStudy = recruitStudyLikeRepository.updateStudyLikeCount(recruitStudyLike.getLikeIdx());
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
    public String saveComment(RecruitStudyComment recruitStudyComment, Alarm alarm) {
        String res = "no";
        if (recruitStudyComment != null) {
            recruitStudyCommentRepository.save(recruitStudyComment);
            alarm.setTitle(alarm.getNickname() + "님 이 댓글을 달았어요");
            alarmRepository.save(alarm);
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

    //스터디원 신청
    public String studyApply(Alarm alarm) {
        String res = "no";
        //신청이 두번 가지않게 하기위해서 알람 타입, 이메일, 닉네임, 스터디글 idx 를 확인해서 있으면 중복이므로 알람을 생성하지 않는다.
        Alarm alarm1 = alarmRepository.findByAlarmTypeAndEmailIdAndNicknameAndRecruitStudyIdx(alarm.getAlarmType(),
                                                                                              alarm.getEmailId(),
                                                                                              alarm.getNickname(),
                                                                                              alarm.getRecruitStudyIdx());
        if(alarm1 != null) {
            res = "exist";
        } else {
            alarm.setTitle(alarm.getNickname() + "님 이 스터디원 신청을 했습니다.");
            alarmRepository.save(alarm);
            res = "yes";
        }
        return res;
    }

}
