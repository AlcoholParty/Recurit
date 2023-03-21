package com.study.soju.controller;

import com.study.soju.entity.*;
import com.study.soju.service.RecruitMenteeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/recruitmenteelist")
public class RecruitMenteeController {

    @Autowired
    RecruitMenteeService recruitMenteeService;

    //멘티 구하기 메인 페이지
    @GetMapping("")
    public String recruitMenteeList(Model model) {
        List<RecruitMentee> list = recruitMenteeService.recruitMenteeListAll();
        model.addAttribute("list", list);
        return "Recruit/RecruitMenteeList";
    }

    //멘티 구하기 글 작성 페이지
    @GetMapping("/writeform")
    public String writeForm(Model model, Principal principal) {
        String emailId = principal.getName();
        String nickname = recruitMenteeService.returnNickname(emailId);
        model.addAttribute("nickname", nickname);
        return "Recruit/RecruitMenteeWriteForm";
    }

    //멘티 구하기 글 작성
    @GetMapping("/writeform/write")
    public String write(RecruitMentee recruitMentee) {
        recruitMenteeService.writeRecruitMentee(recruitMentee);
        return "redirect:/recruitmenteelist";
    }

    //멘티 글 상세보기
    @GetMapping("/post")
    public String post(Long idx, Model model, Principal principal){
        String emailId = principal.getName();
        String nickname = recruitMenteeService.returnNickname(emailId);
        model.addAttribute("nickname", nickname);

        RecruitMentee recruitMentee = recruitMenteeService.findRecruitMentee(idx);
        long memberIdx = recruitMenteeService.returnIdx(emailId);
        model.addAttribute("memberIdx", memberIdx);
        int count = recruitMenteeService.likeCheck(idx, memberIdx);
        recruitMentee.setStudyLikeCheck(count);
        model.addAttribute("recruitMentee", recruitMentee);

        List<RecruitMenteeComment> recruitMenteeCommentList = recruitMenteeService.findCommentList(idx);
        model.addAttribute("list", recruitMenteeCommentList);
        return "Recruit/RecruitMenteePost";
    }

    //멘티 글 좋아요
    @GetMapping("/post/like")
    @ResponseBody
    public String like(RecruitMenteeLike recruitMenteeLike) {
        String res = "no";
        RecruitMentee afterRecruitMentee = recruitMenteeService.likeUpdate(recruitMenteeLike);
        if(afterRecruitMentee != null) {
            res = String.valueOf(afterRecruitMentee.getStudyLike());
        }
        return res;
    }

    //멘티 글 수정 페이지
    @GetMapping("/post/modifyform")
    public String modifyForm(RecruitMentee recruitMentee, Model model) {
        RecruitMentee recruitMentee1 = recruitMenteeService.findRecruitMentee(recruitMentee.getIdx());
        model.addAttribute("recruitMentee", recruitMentee1);
        return "Recruit/RecruitMenteeModifyForm";
    }

    //멘티 글 수정
    @GetMapping("/post/modifyform/modify")
    @ResponseBody
    public String modify(RecruitMentee recruitMentee) {
        String res = "no";
        if(recruitMentee != null) {
            res = recruitMenteeService.modify(recruitMentee);
        }
        return res;
    }

    //멘티 글 삭제
    @GetMapping("/post/delete")
    @ResponseBody
    public String delete(RecruitMentee recruitMentee){
        String res = "no";
        RecruitMentee deleteRecruitMentee = recruitMenteeService.findRecruitMentee(recruitMentee.getIdx());
        res = recruitMenteeService.delete(deleteRecruitMentee);
        return res;
    }

    //댓글 작성
    @GetMapping("/post/comment")
    @ResponseBody
    public String comment(RecruitMenteeComment recruitMenteeComment){
        String res = "no";
        res = recruitMenteeService.saveComment(recruitMenteeComment);
        return res;
    }

    //댓글 삭제
    @GetMapping("/post/comment/delete")
    @ResponseBody
    public String commentDelete(RecruitMenteeComment recruitMenteeComment){
        String res = "no";
        res = recruitMenteeService.deleteComment(recruitMenteeComment.getIdx());
        return res;
    }

    //댓글 수정
    @GetMapping("/post/comment/modify")
    @ResponseBody
    public String commentModify(RecruitMenteeComment recruitMenteeComment){
        String res = "no";
        res = recruitMenteeService.modifyComment(recruitMenteeComment);
        return res;
    }
}
