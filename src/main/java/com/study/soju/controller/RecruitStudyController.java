package com.study.soju.controller;

import com.study.soju.entity.Alarm;
import com.study.soju.entity.RecruitStudy;
import com.study.soju.entity.RecruitStudyComment;
import com.study.soju.entity.RecruitStudyLike;
import com.study.soju.service.RecruitStudyService;
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
@RequestMapping("/recruitstudy")
public class RecruitStudyController {

    @Autowired
    RecruitStudyService recruitStudyService;


    //스터디원 모집 메인페이지
    @GetMapping("")
    //value 에 페이지 라는 파라미터를 url 에서 가져온다. 만약 그 값이 없다면 0 으로 기본값을 잡아준다. 그값은 int 타입의 page 에 넣어준다.
    public String recruitStudy(Model model, @RequestParam(value="page", defaultValue="0") int page) {
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
        int rowTotal = recruitStudyService.rowTotal();
        //페이징 처리를 위해 맵을 이용해서 리스트 가져오기
        List<RecruitStudy> recruitStudyList = recruitStudyService.recruitStudyListAll(map);
        model.addAttribute("list", recruitStudyList);
        //페이징 처리를 위해 url, 이동할 페이지번호, 전체 열의 갯수, 페이징을 위해 설정을 잡아둔 값들을 가지고 HTML 에 작성해줄 내용을 생성한다.
        String pageMenu = Paging.getPaging("recruitstudy", nowPage, rowTotal, PageSetup.BLOCKLIST, PageSetup.BLOCKPAGE);
        model.addAttribute("pageMenu", pageMenu);
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
        long memberIdx = recruitStudyService.returnIdx(emailId);
        model.addAttribute("memberIdx", memberIdx);
        int count = recruitStudyService.likeCheck(idx, memberIdx);
        recruitStudy.setStudyLikeCheck(count);
        model.addAttribute("recruitStudy", recruitStudy);

        //알람 처리를 위해서 writer 의 emailId 를 넘겨준다.
        String email = recruitStudyService.returnEmailId(recruitStudy.getWriter());
        model.addAttribute("emailId", email);

        //댓글 리스트 보내주기
        List<RecruitStudyComment> recruitStudyCommentList = recruitStudyService.findCommentList(idx);
        model.addAttribute("list", recruitStudyCommentList);

        return "Recruit/RecruitStudyPost";
    }

    //스터디원 모집 글 상세보기 좋아요기능
    @GetMapping("/post/like")
    @ResponseBody
    public String like(RecruitStudyLike recruitStudyLike, Alarm alarm) {
        //알람을 만들어 주기 위해서 Type 추가
        alarm.setAlarmType(1);
        String res = "no";
        RecruitStudy afterRecruitStudy = recruitStudyService.likeUpdate(recruitStudyLike, alarm);
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
    public String comment(RecruitStudyComment recruitStudyComment, Alarm alarm){
        //알람을 만들어 주기 위해서 Type 추가
        alarm.setAlarmType(4);
        String res = "no";
        res = recruitStudyService.saveComment(recruitStudyComment, alarm);
        return res;
    }

    //댓글 삭제
    @GetMapping("/post/comment/delete")
    @ResponseBody
    public String commentDelete(RecruitStudyComment recruitStudyComment){
        String res = "no";
        res = recruitStudyService.deleteComment(recruitStudyComment.getIdx());
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

    //스터디원 구하기 신청
    @GetMapping("/post/apply")
    @ResponseBody
    public String studyApply(RecruitStudy recruitStudy, Alarm alarm) {
        String res = "no";
        //알람을 생성하기 위해서 타입 넣어주기
        alarm.setAlarmType(2);
        res = recruitStudyService.studyApply(alarm);
        return res;
    }
}
