package com.study.soju.controller;

import com.study.soju.entity.RecruitStudy;
import com.study.soju.service.RecruitStudyService;
import com.study.soju.service.SignUpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
public class MainController {
    @Autowired
    SignUpService signUpService;

    @Autowired
    RecruitStudyService recruitStudyService;
    // 로그인 후 메인 페이지
    @GetMapping("/")
    public String main(Principal principal, Model model) {
        // 1. 로그인을 했는지 체크한다.
        // 1-1. 로그인을 안한 경우
        if ( principal == null ) {
            // 1-1-1. 로그인 전 메인 페이지로 리다이렉트한다.
            return "redirect:/n";
            // 1-2. 로그인을 한 경우
        } else {
            // 1-2-1. 메인 페이지로 이동한다.
            String nickname = signUpService.returnNickname(principal.getName());
            model.addAttribute("nickname", nickname);
            List<RecruitStudy> recruitStudyList = recruitStudyService.recruitStudyListAll();
            List<RecruitStudy> recruitStudies = new ArrayList<>();
            recruitStudies.add(recruitStudyList.get(0));
            recruitStudies.add(recruitStudyList.get(1));
            recruitStudies.add(recruitStudyList.get(2));
            recruitStudies.add(recruitStudyList.get(3));
            recruitStudies.add(recruitStudyList.get(4));
            model.addAttribute("list", recruitStudies);
            return "Main";
        }
    }

    // 로그인 전 메인 페이지
    @GetMapping("/n")
    public String nmain() {
        // 1. 메인 페이지로 이동한다.
        return "Main";
    }

    // 로그인 페이지
    @GetMapping("/loginform") // required = false - 해당 필드가 URL 파라미터에 존재하지 않아도 에러가 발생하지 않는다.
    public String loginform(@RequestParam(value = "error", required = false) String error, // URL 파라미터로 넘어오는 에러 체크값이 있을 경우 받는다.
                            @RequestParam(value = "errorMsg", required = false) String errorMsg, // URL 파라미터로 넘어오는 에러 메세지가 있을 경우 받는다.
                            Model model) {
        // 에러 체크값을 모델로 바인딩 한다.
        model.addAttribute("error", error);
        // 에러 메세지를 모델로 바인딩 한다.
        model.addAttribute("errorMsg", errorMsg);
        return "SignUp/LoginForm";
    }
}
