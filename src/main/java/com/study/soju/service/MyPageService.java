package com.study.soju.service;

import com.study.soju.entity.*;
import com.study.soju.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MyPageService {
    @Autowired
    AlarmRepository alarmRepository;

    @Autowired
    RecruitStudyRepository recruitStudyRepository;

    @Autowired
    RecruitMentorRepository recruitMentorRepository;

    @Autowired
    RecruitMenteeRepository recruitMenteeRepository;

    @Autowired
    MemberRepository memberRepository;

    public List<Alarm> findEmailId(String emailId) {
        return alarmRepository.findByEmailId(emailId);
    }


    public int accept(Alarm alarm) {
        int res = 0;
        if (alarm.getRecruitStudyIdx() != null && alarm.getRecruitMentorIdx() == null && alarm.getRecruitMenteeIdx() == null) {
            //구하는 사람 추가
            RecruitStudy recruitStudy = recruitStudyRepository.findByIdx(alarm.getRecruitStudyIdx());
            //만약 아직도 인원을 구하는 중이라면 인원 수 추가 및 알람 삭제
            if(recruitStudy.getRecruiting() == 0) {
                recruitStudy.setRecruitingPersonnel(recruitStudy.getRecruitingPersonnel() + 1);
                //인월은 추가한 이후에 인원이 꽉차면 더이상 구하지 않게 하기위해서 모집완료로 변경
                if(recruitStudy.getPersonnel() <= recruitStudy.getRecruitingPersonnel()) {
                    recruitStudy.setRecruiting(1);
                }
                recruitStudyRepository.save(recruitStudy);
                //알람 삭제
                Alarm deleteAlarm = alarmRepository.findByIdx(alarm.getIdx());
                System.out.println("삭제할거 : " + deleteAlarm);
                alarmRepository.delete(deleteAlarm);
                res = 1;
            } else {
                //인원을 다 구했다면 삭제하지 않고 돌아간다.
                res = 4;
            }
        } else if(alarm.getRecruitMentorIdx() != null && alarm.getRecruitStudyIdx() == null && alarm.getRecruitMenteeIdx() == null) {
            RecruitMentor recruitMentor = recruitMentorRepository.findByIdx(alarm.getRecruitMentorIdx());
            if(recruitMentor.getRecruiting() == 0) {
                Alarm deleteAlarm = alarmRepository.findByIdx(alarm.getIdx());
                alarmRepository.delete(deleteAlarm);
                res = 2;
            } else {
                res = 4;
            }
        } else if(alarm.getRecruitMenteeIdx() != null && alarm.getRecruitStudyIdx() == null && alarm.getRecruitMentorIdx() == null) {
            RecruitMentee recruitMentee = recruitMenteeRepository.findByIdx(alarm.getRecruitMenteeIdx());
            if(recruitMentee.getRecruiting() == 0) {
                Alarm deleteAlarm = alarmRepository.findByIdx(alarm.getIdx());
                alarmRepository.delete(deleteAlarm);
                res = 3;
            } else {
                res = 4;
            }
        }
        return res;
    }

    public String refuse(Alarm alarm) {
        String res = "no";
        Alarm deleteAlarm = alarmRepository.findByIdx(alarm.getIdx());
        alarmRepository.delete(deleteAlarm);
        res = "yes";
        return res;
    }

    public String delete(Alarm alarm) {
        String res = "no";
        Alarm deleteAlarm = alarmRepository.findByIdx(alarm.getIdx());
        alarmRepository.delete(deleteAlarm);
        res = "yes";
        return res;
    }

    //나중에 다른 컨트롤러로 이동 시킬 메서드
    public int alarmCount(String emailId) {
        return alarmRepository.countByEmailId(emailId);
    }

    public Member returnMember(String emailId) {
        return memberRepository.findByEmailId(emailId);
    }

    public long returnIdx(String emailId) {
        Member member = memberRepository.findByEmailId(emailId);
        long idx = member.getIdx();
        return idx;
    }
}
