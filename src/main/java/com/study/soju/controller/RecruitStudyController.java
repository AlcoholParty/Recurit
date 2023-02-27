package com.study.soju.controller;

import com.study.soju.entity.RecruitStudy;
import com.study.soju.entity.RecruitStudyComment;
import com.study.soju.entity.RecruitStudyLike;
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

    //스터디원 모집 메인페이지
    @GetMapping("")
    public String recruitStudy(Model model) {
        List<RecruitStudy> recruitStudyList = recruitStudyService.recruitStudyListAll();
        model.addAttribute("list", recruitStudyList);
        return "Recruit/RecruitStudy";
    }

    //스터디원 모집 작성페이지
    @GetMapping("/writeform")
    public String writeForm(Model model, Principal principal) {
        String emailId = principal.getName();
        String nickname = recruitStudyService.returnNickname(emailId);
        model.addAttribute("nickname", nickname);
        return "Recruit/RecruitStudyWriteForm";
    }

    //스터디원 모집 글 저장
    @GetMapping("/writeform/write")
    public String write(RecruitStudy recruitStudy) {
        recruitStudyService.writeRecruitStudy(recruitStudy);
        //다시 메인 창으로 돌아갈때 리스트를 들고 가야되기때문에 리다이렉트로 변경
        return "redirect:/recruitstudy";
    }

    //스터디원 모집 글 상세보기
    @GetMapping("/post")
    public String changeForm(Long idx, Model model, Principal principal) {
        //닉네임 보내주기
        String emailId = principal.getName();
        String nickname = recruitStudyService.returnNickname(emailId);
        model.addAttribute("nickname", nickname);

        //스터디 객체 보내주기
        RecruitStudy recruitStudy = recruitStudyService.findRecruitStudy(idx);
        //카운트 변수를 닉네임과 idx 가 일치하는 내용이 있으면 본인이 좋아요를 한것과 같으니깐 1을 보내주고
        //내용이 없으면 0을 보내준다
        int count = recruitStudyService.likeCheck(nickname, idx);
        recruitStudy.setStudyLikeCheck(count);
        model.addAttribute("recruitStudy", recruitStudy);

        //댓글 리스트 보내주기
        List<RecruitStudyComment> recruitStudyCommentList = recruitStudyService.findCommentList();
        model.addAttribute("list", recruitStudyCommentList);

        return "Recruit/RecruitStudyPost";
    }

    //스터디원 모집 글 상세보기 좋아요기능
    @GetMapping("/post/like")
    @ResponseBody
    public String like(RecruitStudy recruitStudy, RecruitStudyLike recruitStudyLike) {
        String res = "no";
        RecruitStudy afterRecruitStudy = recruitStudyService.likeUpdate(recruitStudy, recruitStudyLike);
        if(afterRecruitStudy != null) {
            res = String.valueOf(afterRecruitStudy.getStudyLike());
        }
        return res;
    }

    //스터디원 모집글 상세보기 내용 변경 페이지
    @GetMapping("/post/modifyform")
    public String modifyForm(RecruitStudy recruitStudy, Model model) {
        //저기서 idx뽑고
        //그다음 글 변경 가능한 곳으로 이동
        RecruitStudy recruitStudy1 = recruitStudyService.findRecruitStudy(recruitStudy.getIdx());
        model.addAttribute("recruitStudy", recruitStudy1);
        return "Recruit/RecruitStudyModifyForm";
    }

    //스터디원 모집글 상세보기 내용 변경 기능
    @GetMapping("/post/modifyform/modify")
    @ResponseBody
    public String modify(RecruitStudy recruitStudy){
        String res = "no";
        if(recruitStudy != null) {
            res = recruitStudyService.modify(recruitStudy);
        }
        return res;
    }

    //스터디원 모집글 상세보기 삭제기능
    @GetMapping("/post/delete")
    @ResponseBody
    public String delete(RecruitStudy recruitStudy){
        String res = "no";
        RecruitStudy deleteRecruitStudy = recruitStudyService.findRecruitStudy(recruitStudy.getIdx());
        res = recruitStudyService.delete(deleteRecruitStudy);
        return res;
    }

    //댓글 작성
    @GetMapping("/post/comment")
    @ResponseBody
    public String comment(RecruitStudyComment recruitStudyComment){
        String res = "no";
        res = recruitStudyService.saveComment(recruitStudyComment);
        return res;
    }

    //댓글 삭제
    @GetMapping("/post/comment/delete")
    @ResponseBody
    public String commentDelete(RecruitStudyComment recruitStudyComment){
        String res = "no";
        res = recruitStudyService.deleteComment(recruitStudyComment.getCommentIdx());
        return res;
    }

    //댓글 수정
    @GetMapping("/post/comment/modify")
    @ResponseBody
    public String commentModify(RecruitStudyComment recruitStudyComment){
        String res = "no";
        res = recruitStudyService.modifyComment(recruitStudyComment);
        return res;
    }
}
