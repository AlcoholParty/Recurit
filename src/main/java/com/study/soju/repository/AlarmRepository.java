package com.study.soju.repository;

import com.study.soju.entity.Alarm;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AlarmRepository extends JpaRepository<Alarm, Object> {
    Alarm findByAlarmTypeAndEmailIdAndNicknameAndRecruitStudyIdx(int alarmType, String emailId, String nickname, long recruitStudyIdx);

    int countByEmailId(String emailId);

    List<Alarm> findByEmailId(String emailId);

    Alarm findByIdx(long idx);
}
