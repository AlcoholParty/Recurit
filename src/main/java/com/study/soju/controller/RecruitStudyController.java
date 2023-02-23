package com.study.soju.controller;

import com.study.soju.entity.Member;
import com.study.soju.entity.RecruitStudy;
import com.study.soju.service.RecruitStudyService;
import com.study.soju.service.SignUpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/recruitstudy")
public class RecruitStudyController {

    @Autowired
    RecruitStudyService recruitStudyService;

    @GetMapping("")
    public String recruitStudy(Model model) {
        //리스트 가져와서 뿌리면 될듯
        List<RecruitStudy> recruitStudyList = recruitStudyService.recruitStudyListAll();
        model.addAttribute("list", recruitStudyList);
        return "Recruit/RecruitStudy";
    }

    @GetMapping("/writeform")
    public String writeForm(Model model, Principal principal) {
        String emailId = principal.getName();
        String nickname = recruitStudyService.returnNickname(emailId);
        model.addAttribute("nickname", nickname);
        return "Recruit/RecruitWriteForm";
    }

    @GetMapping("/writeform/write")
    public String write(RecruitStudy recruitStudy) {
        recruitStudyService.writeRecruitStudy(recruitStudy);
        return "Recruit/RecruitStudy";
    }

}
