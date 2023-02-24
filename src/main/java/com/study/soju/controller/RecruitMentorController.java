package com.study.soju.controller;

import com.study.soju.entity.RecruitMentor;
import com.study.soju.service.RecruitMentorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/recruitmentorlist")
public class RecruitMentorController {

    @Autowired
    RecruitMentorService recruitMentorService;

    @GetMapping("")
    public String recruitMentorList(Model model) {
        List<RecruitMentor> list = recruitMentorService.recruitMentorListAll();
        model.addAttribute("list", list);
        return "Recruit/RecruitMentorList";
    }

    @GetMapping("/writeform")
    public String writeForm(Model model, Principal principal) {
        String emailId = principal.getName();
        String nickname = recruitMentorService.returnNickname(emailId);
        model.addAttribute("nickname", nickname);
        return "Recruit/RecruitMentorWriteForm";
    }

    @GetMapping("/writeform/write")
    public String write(RecruitMentor recruitMentor) {
        recruitMentorService.writeRecruitMentor(recruitMentor);
        return "redirect:/recruitmentorlist";
    }
}
