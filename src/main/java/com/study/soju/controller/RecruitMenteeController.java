package com.study.soju.controller;

import com.study.soju.entity.RecruitMentee;
import com.study.soju.service.RecruitMenteeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/recruitmenteelist")
public class RecruitMenteeController {

    @Autowired
    RecruitMenteeService recruitMenteeService;

    @GetMapping("")
    public String recruitMenteeList(Model model) {
        List<RecruitMentee> list = recruitMenteeService.recruitMenteeListAll();
        model.addAttribute("list", list);
        return "Recruit/RecruitMenteeList";
    }

    @GetMapping("/writeform")
    public String writeForm(Model model, Principal principal) {
        String emailId = principal.getName();
        String nickname = recruitMenteeService.returnNickname(emailId);
        model.addAttribute("nickname", nickname);
        return "Recruit/RecruitMenteeWriteForm";
    }

    @GetMapping("/writeform/write")
    public String write(RecruitMentee recruitMentee) {
        recruitMenteeService.writeRecruitMentee(recruitMentee);
        return "redirect:/recruitmenteelist";
    }

}
