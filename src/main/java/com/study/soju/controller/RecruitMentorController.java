package com.study.soju.controller;

import com.study.soju.entity.*;
import com.study.soju.service.RecruitMentorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/recruitmentorlist")
public class RecruitMentorController {

    @Autowired
    RecruitMentorService recruitMentorService;

    //멘토모집 메인페이지
    @GetMapping("")
    public String recruitMentorList(Model model) {
        List<RecruitMentor> list = recruitMentorService.recruitMentorListAll();
        model.addAttribute("list", list);
        return "Recruit/RecruitMentorList";
    }

    //멘토모집 글 작성 페이지
    @GetMapping("/writeform")
    public String writeForm(Model model, Principal principal) {
        String emailId = principal.getName();
        String nickname = recruitMentorService.returnNickname(emailId);
        model.addAttribute("nickname", nickname);
        return "Recruit/RecruitMentorWriteForm";
    }

    //멘토모집 글 작성
    @GetMapping("/writeform/write")
    public String write(RecruitMentor recruitMentor) {
        recruitMentorService.writeRecruitMentor(recruitMentor);
        return "redirect:/recruitmentorlist";
    }

    //멘토모집 글 상세보기
    @GetMapping("/post")
    public String postForm(Long idx, Model model, Principal principal){
        //이메일 보내주기
        String emailId = principal.getName();
        String nickname = recruitMentorService.returnNickname(emailId);
        model.addAttribute("nickname", nickname);

        //글내용 보내주기
        RecruitMentor recruitMentor = recruitMentorService.findRecruitMentor(idx);
        long memberIdx = recruitMentorService.returnIdx(emailId);
        model.addAttribute("memberIdx", memberIdx);
        int count = recruitMentorService.likeCheck(idx, memberIdx);
        recruitMentor.setStudyLikeCheck(count);
        model.addAttribute("recruitMentor", recruitMentor);

        //댓글 리스트 보내주기
        List<RecruitMentorComment> recruitMentorCommentList = recruitMentorService.findCommentList(idx);
        model.addAttribute("list", recruitMentorCommentList);

        return "Recruit/RecruitMentorPost";
    }

    //글 상세보기에서 수정하기 페이지로 이동
    @GetMapping("/post/modifyform")
    public String postModify(Long idx, Model model) {
        RecruitMentor recruitMentor = recruitMentorService.findRecruitMentor(idx);
        model.addAttribute("recruitMentor", recruitMentor);
        return "Recruit/RecruitMentorModifyForm";
    }

    //글 수정하기
    @GetMapping("/post/modifyform/modify")
    @ResponseBody
    public String modify(RecruitMentor recruitMentor){
        String res = "no";
        res = recruitMentorService.modify(recruitMentor);
        return res;
    }

    //글 삭제하기
    @GetMapping("/post/delete")
    @ResponseBody
    public String delete(RecruitMentor recruitMentor){
        String res = "no";
        RecruitMentor deleteRecruitMentor = recruitMentorService.findRecruitMentor(recruitMentor.getIdx());
        res = recruitMentorService.delete(deleteRecruitMentor);
        return res;
    }

    //글 좋아요
    @GetMapping("/post/like")
    @ResponseBody
    public String like(RecruitMentorLike recruitMentorLike) {
        String res = "no";
        RecruitMentor afterRecruitMentor = recruitMentorService.likeUpdate(recruitMentorLike);
        if(afterRecruitMentor != null) {
            res = String.valueOf(afterRecruitMentor.getStudyLike());
        }
        return res;
    }

    //댓글작성
    @GetMapping("/post/comment")
    @ResponseBody
    public String comment(RecruitMentorComment recruitMentorComment){
        String res = "no";
        res = recruitMentorService.saveComment(recruitMentorComment);
        return res;
    }

    //댓글 삭제
    @GetMapping("/post/comment/delete")
    @ResponseBody
    public String commentDelete(RecruitMentorComment recruitMentorComment){
        String res = "no";
        res = recruitMentorService.deleteComment(recruitMentorComment.getIdx());
        return res;
    }

    //댓글 수정
    @GetMapping("/post/comment/modify")
    @ResponseBody
    public String commentModify(RecruitMentorComment recruitMentorComment){
        String res = "no";
        res = recruitMentorService.modifyComment(recruitMentorComment);
        return res;
    }
}
