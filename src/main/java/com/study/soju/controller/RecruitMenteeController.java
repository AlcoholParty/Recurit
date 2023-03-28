package com.study.soju.controller;

import com.study.soju.entity.*;
import com.study.soju.service.RecruitMenteeService;
import com.study.soju.util.PageSetup;
import com.study.soju.util.Paging;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/mentorprofilelist")
public class RecruitMenteeController {

    @Autowired
    RecruitMenteeService recruitMenteeService;

    //멘티 구하기 메인 페이지
    @GetMapping("")
    public String recruitMenteeList(Model model, @RequestParam(value = "page", defaultValue = "0") int page) {
        //현재 페이지의 숫자를 가지고 그 값에 맞는 리스트를 끌어온다.
        //페이징 처리
        int nowPage = 1;
        //만약 페이지값이 있다면 nowPage 값을 파라미터로 넘어온 page 값으로 변경
        if(page != 0) {
            nowPage = page;
        }
        // 한 페이지에 표시되는 게시물의 시작번호와 끝번호를 계산
        int start = (nowPage - 1) * PageSetup.BLOCKLIST + 1;
        int end = start + PageSetup.BLOCKLIST - 1;
        HashMap<String, Integer> map = new HashMap<String, Integer>();
        map.put("start", start);
        map.put("end", end);
        //전체 열의 갯수를 가지고온다.
        int rowTotal = recruitMenteeService.rowTotal();
        //페이징 처리를 위해 맵을 이용해서 리스트 가져오기
        List<RecruitMentee> recruitMenteeList = recruitMenteeService.recruitMenteeListAll(map);
        model.addAttribute("list", recruitMenteeList);
        //페이징 처리를 위해 url, 이동할 페이지번호, 전체 열의 갯수, 페이징을 위해 설정을 잡아둔 값들을 가지고 HTML 에 작성해줄 내용을 생성한다.
        String pageMenu = Paging.getPaging("mentorprofilelist", nowPage, rowTotal, PageSetup.BLOCKLIST, PageSetup.BLOCKPAGE);
        model.addAttribute("pageMenu", pageMenu);
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
        return "redirect:/mentorprofilelist";
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

        //알람 처리를 위해서 writer 의 emailId 를 넘겨준다.
        String email = recruitMenteeService.returnEmailId(recruitMentee.getWriter());
        model.addAttribute("emailId", email);

        List<RecruitMenteeComment> recruitMenteeCommentList = recruitMenteeService.findCommentList(idx);
        model.addAttribute("list", recruitMenteeCommentList);
        return "Recruit/RecruitMenteePost";
    }

    //멘티 글 좋아요
    @GetMapping("/post/like")
    @ResponseBody
    public String like(RecruitMenteeLike recruitMenteeLike, Alarm alarm) {
        String res = "no";
        RecruitMentee afterRecruitMentee = recruitMenteeService.likeUpdate(recruitMenteeLike, alarm);
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

    //스터디원 구하기 신청
    @GetMapping("/post/apply")
    @ResponseBody
    public String menteeApply(RecruitMentee recruitMentee, Alarm alarm) {
        String res = "no";
        //알람을 생성하기 위해서 타입 넣어주기
        alarm.setAlarmType(2);
        res = recruitMenteeService.menteeApply(alarm);
        return res;
    }
}
