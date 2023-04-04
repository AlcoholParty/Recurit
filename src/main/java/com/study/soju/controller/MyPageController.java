package com.study.soju.controller;

import com.study.soju.entity.*;
import com.study.soju.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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

    @Autowired
    PayService payService;

    @Autowired
    RecruitStudyService recruitStudyService;

    @Autowired
    RecruitMentorService recruitMentorService;

    @Autowired
    RecruitMenteeService recruitMenteeService;

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

    @GetMapping("/perchaselist")
    public String perchaseList(Principal principal, Model model){
        //Principal로 유저 emailId 가져오기
        String emailId = principal.getName();
        //유저 이메일로 모든 결제 내역 가져오기
        List<Pay> findPay = payService.findPay(emailId);
        //결제내역 바인딩
        model.addAttribute("payList", findPay);
        return "MyPage/PerchaseList";
    }

    @PostMapping("/perchaselist/refundcheck")
    @ResponseBody
    public String refundCheck(Pay pay){
        //환불 버튼을 눌렀을때 작업할 내용
        String res = "no";
        //가져온 impUid 로 환불할 결제 항목을 가져오기
        Pay resPay = payService.refundCheck(pay.getImpUid());
        if(resPay != null){
            //값이 잘 담겨있다면 내용을 환불진행중으로 바꾸기위해 업데이트
            payService.updatePay(resPay);
            //이후 yes 로 변경해서 값이 잘 변경 되었는지 알려주기
            res = "yes";
        }
        return res;
    }

    @GetMapping("/likelist")
    public String likeList(Model model, Principal principal) {
        long idx = myPageService.returnIdx(principal.getName());
        List<RecruitStudy> recruitStudyList = recruitStudyService.likeList(idx);
        model.addAttribute("studyList", recruitStudyList);
        List<RecruitMentor> recruitMentorList = recruitMentorService.likeList(idx);
        model.addAttribute("mentorList", recruitMentorList);
        List<RecruitMentee> recruitMenteeList = recruitMenteeService.likeList(idx);
        model.addAttribute("menteeList", recruitMenteeList);
        return "MyPage/LikeList";
    }

}
