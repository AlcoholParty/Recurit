package com.study.soju.controller;

import com.study.soju.entity.Alarm;
import com.study.soju.entity.Member;
import com.study.soju.service.MyPageService;
import com.study.soju.service.SignUpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/mypage")
public class MyPageController {

    @Autowired
    MyPageService myPageService;

    @Autowired
    SignUpService signUpService;

    @GetMapping("")
    public String myPage(Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/n";
        } else {
            String nickname = signUpService.returnNickname(principal.getName());
            model.addAttribute("nickname", nickname);
            int alarmCount = myPageService.alarmCount(principal.getName());
            model.addAttribute("alarmCount", alarmCount);
            List<Alarm> alarmList = myPageService.findEmailId(principal.getName());
            model.addAttribute("alarmList", alarmList);
            Member member = myPageService.returnMember(principal.getName());
            model.addAttribute("member", member);
            return "MyPage/MyPage";
        }
    }

    @GetMapping("/alarm")
    public String alarm(Principal principal, Model model) {
        List<Alarm> alarmList = myPageService.findEmailId(principal.getName());
        model.addAttribute("list", alarmList);
        return "MyPage/Alarm";
    }

    @GetMapping("/alarm/accept")
    @ResponseBody
    public String alarmAccept(Alarm alarm) {
        String res = "no";
        res = myPageService.accept(alarm);
        return res;
    }

    @GetMapping("/alarm/refuse")
    @ResponseBody
    public String alarmRefuse(Alarm alarm) {
        String res = "no";
        res = myPageService.refuse(alarm);
        return res;
    }

    @GetMapping("/alarm/delete")
    @ResponseBody
    public String alarmDelete(Alarm alarm) {
        String res = "no";
        res = myPageService.delete(alarm);
        return res;
    }

}
