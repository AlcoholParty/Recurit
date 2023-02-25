package com.study.soju.controller;

import com.study.soju.entity.RecruitStudy;
import com.study.soju.service.RecruitStudyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
        return "Recruit/RecruitStudyWriteForm";
    }

    @GetMapping("/writeform/write")
    public String write(RecruitStudy recruitStudy) {
        recruitStudyService.writeRecruitStudy(recruitStudy);
        //다시 메인 창으로 돌아갈때 리스트를 들고 가야되기때문에 리다이렉트로 변경
        return "redirect:/recruitstudy";
    }

    @GetMapping("/post")
    public String changeForm(Long idx, Model model, Principal principal) {
        String emailId = principal.getName();
        String nickname = recruitStudyService.returnNickname(emailId);
        model.addAttribute("nickname", nickname);
        RecruitStudy recruitStudy = recruitStudyService.findRecruitStudy(idx);
        model.addAttribute("recruitStudy", recruitStudy);
        return "Recruit/RecruitStudyPost";
    }

    @GetMapping("/post/like")
    @ResponseBody
    public String like(RecruitStudy recruitStudy) {
        String res = "no";
        RecruitStudy afterRecruitStudy = recruitStudyService.likeUpdate(recruitStudy);
        if(afterRecruitStudy != null) {
            res = String.valueOf(afterRecruitStudy.getStudyLike());
        }
        return res;
    }

    @GetMapping("/post/modifyform")
    public String modifyForm(RecruitStudy recruitStudy, Model model) {
        //저기서 idx뽑고
        //그다음 글 변경 가능한 곳으로 이동
        RecruitStudy recruitStudy1 = recruitStudyService.findRecruitStudy(recruitStudy.getIdx());
        model.addAttribute("recruitStudy", recruitStudy1);
        return "Recruit/RecruitStudyModifyForm";
    }

    @GetMapping("/post/delete")
    @ResponseBody
    public String delete(RecruitStudy recruitStudy){
        String res = "no";
        RecruitStudy deleteRecruitStudy = recruitStudyService.findRecruitStudy(recruitStudy.getIdx());
        res = recruitStudyService.delete(deleteRecruitStudy);
        return res;
    }

    @GetMapping("/post/modifyform/modify")
    @ResponseBody
    public String modify(RecruitStudy recruitStudy){
        String res = "no";
        if(recruitStudy != null) {
            res = recruitStudyService.modify(recruitStudy);
        }
        return res;
    }
}
