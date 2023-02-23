package com.study.soju.controller;

import com.study.soju.entity.Member;
import com.study.soju.entity.Meta;
import com.study.soju.entity.MetaRoom;
import com.study.soju.service.MetaService;
import com.study.soju.service.SignUpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/meta")
public class MetaController {
    // 메타 서비스
    @Autowired
    MetaService metaService;
    // 회원가입 및 로그인 인증 서비스
    @Autowired
    SignUpService signUpService;
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // 메타 메인 페이지
    @GetMapping("")
    public String meta(Principal principal, Model model) {
        // 1. 서비스를 통해 현재 생성된 메타 방을 모두 조회해서, List 형태의 DTO로 반환 받아온다.
        List<Meta.rpMetaList> rpMetaList = metaService.metaList();
        // 2. 1에서 반환받은 List 형태의 DTO를 방의 타입별로 나누기 위해 각 방의 타입마다 List 형태의 DTO를 생성한다.
        // 2-1. 방 타입이 스터디일 경우 - studyRoom List DTO
        List<Meta.rpMetaList> studyList = new ArrayList<>();
        // 2-2. 방 타입이 카페일 경우 - cafeRoom List DTO
        List<Meta.rpMetaList> cafeList = new ArrayList<>();
        // 2-3. 방 타입이 자습일 경우 - oneRoom List DTO
        List<Meta.rpMetaList> oneList = new ArrayList<>();
        // 3. foreach문을 사용하여 1에서 반환받은 List 형태의 DTO에 들어있는 값들을 하나씩 가져온다.
        for ( Meta.rpMetaList metaList : rpMetaList ) {
            // 4. 3에서 가져온 값 중 방 타입을 가져와 체크한다.
            // 4-1. 방 타입이 스터디일 경우
            if ( metaList.getMetaType().equals("studyRoom") ) {
                // 4-1-1. 2-1에서 생성한 studyRoom List DTO에 추가한다.
                studyList.add(metaList);
            // 4-2. 방 타입이 카페일 경우
            } else if ( metaList.getMetaType().equals("cafeRoom") ) {
                // 4-2-1. 2-2에서 생성한 cafeRoom List DTO에 추가한다.
                cafeList.add(metaList);
            // 4-3. 방 타입이 자습일 경우
            } else {
                // 4-3-1. 2-3에서 생성한 oneRoom List DTO에 추가한다.
                oneList.add(metaList);
            }
        }

        // 5. Principal을 사용하여 로그인 유저의 아이디를 서비스에 전달한다.
        Member.rpMetaProfile rpMetaProfile = signUpService.metaProfile(principal.getName());

        // 6. 3에서 foreach문을 사용하여 방 타입별로 값을 새로 추가해 만든 List 형태의 DTO들을 바인딩한다.
        // 6-1. 2-1에서 생성하고 4-1에서 값을 추가한 studyRoom List DTO를 바인딩한다.
        model.addAttribute("studyList", studyList);
        // 6-2. 2-2에서 생성하고 4-2에서 값을 추가한 cafeRoom List DTO를 바인딩한다.
        model.addAttribute("cafeList", cafeList);
        // 6-3. 2-3에서 생성하고 4-3에서 값을 추가한 oneRoom List DTO를 바인딩한다.
        model.addAttribute("oneList", oneList);
        // 7. 5에서 반환받은 유저 정보 DTO를 바인딩한다.
        model.addAttribute("metaProfile", rpMetaProfile);
        // 8. 검색 체크값을 바인딩한다. - 0 : 검색 안했을 경우
        model.addAttribute("check", 0);
        // 9. 검색에 사용할 DTO를 바인딩한다.
        model.addAttribute("metaDTO", new Meta.rqSearchMetaList());
        // 10. 메타 메인 페이지로 이동한다.
        return "Meta/MetaRoom";
    }

    // 방 만들기 페이지
    @GetMapping("/createmetaform")
    public String createMetaForm(Model model) {
        // 1. 방 만들기에 사용할 DTO를 바인딩한다.
        model.addAttribute("metaDTO", new Meta.rqCreateMeta());
        // 2. 방만들기 페이지로 이동한다.
        return "Meta/CreateMetaForm";
    }

    // 방 만들기
    @GetMapping("/createmetaform/createmeta")
    public String createMeta(Meta.rqCreateMeta rqCreateMeta) { // 1. 파라미터로 form에서 넘어온 DTO를 받아온다.
        // 2. 1에서 파라미터로 받아온 DTO를 서비스에 전달한다.
        metaService.createRoom(rqCreateMeta);
        // 3. 메타 메인 페이지로 리다이렉트한다.
        return "redirect:/meta";
    }

    // 방 이름 및 분야별로 검색
    @GetMapping("/search")
    public String searchMeta(Meta.rqSearchMetaList rqSearchMetaList, Principal principal, Model model) { // 1. 파라미터로 form에서 넘어온 DTO를 받아온다.
        // 2. 1에서 파라미터로 받아온 DTO를 서비스에 전달한다.
        List<Meta.rpSearchMetaList> rpSearchMetaList = metaService.searchMetaList(rqSearchMetaList);
        // 3. 2에서 반환받은 List 형태의 DTO를 방의 타입별로 나누기 위해 각 방의 타입마다 List를 생성한다.
        // 3-1. 방 타입이 스터디일 경우 - studyRoom List DTO
        List<Meta.rpSearchMetaList> studyList = new ArrayList<>();
        // 3-2. 방 타입이 카페일 경우 - cafeRoom List DTO
        List<Meta.rpSearchMetaList> cafeList = new ArrayList<>();
        // 3-3. 방 타입이 자습일 경우 - oneRoom List DTO
        List<Meta.rpSearchMetaList> oneList = new ArrayList<>();
        // 4. foreach문을 사용하여 2에서 반환받은 List 형태의 DTO에 들어있는 값들을 하나씩 가져온다.
        for ( Meta.rpSearchMetaList searchMetaList : rpSearchMetaList ) {
            // 5. 3에서 가져온 값 중 방 타입을 가져와 체크한다.
            // 5-1. 방 타입이 스터디일 경우
            if ( searchMetaList.getMetaType().equals("studyRoom") ) {
                // 5-1-1. 2-1에서 생성한 studyRoom List DTO에 추가한다.
                studyList.add(searchMetaList);
            // 5-2. 방 타입이 카페일 경우
            } else if ( searchMetaList.getMetaType().equals("cafeRoom") ) {
                // 5-2-1. 2-2에서 생성한 cafeRoom List DTO에 추가한다.
                cafeList.add(searchMetaList);
            // 5-3. 방 타입이 자습일 경우
            } else {
                // 5-3-1. 2-3에서 생성한 oneRoom List DTO에 추가한다.
                oneList.add(searchMetaList);
            }
        }

        // 6. Principal을 사용하여 로그인 유저의 아이디를 서비스에 전달한다.
        Member.rpMetaProfile rpMetaProfile = signUpService.metaProfile(principal.getName());

        // 7. 4에서 foreach문을 사용하여 방 타입별로 값을 새로 추가해 만든 List 형태의 DTO들을 바인딩한다.
        // 7-1. 3-1에서 생성하고 5-1에서 값을 추가한 studyRoom List DTO를 바인딩한다.
        model.addAttribute("studyList", studyList);
        // 7-2. 3-2에서 생성하고 5-2에서 값을 추가한 cafeRoom List DTO를 바인딩한다.
        model.addAttribute("cafeList", cafeList);
        // 7-3. 3-3에서 생성하고 5-3에서 값을 추가한 oneRoom List DTO를 바인딩한다.
        model.addAttribute("oneList", oneList);
        // 8. 6에서 반환받은 유저 정보 DTO를 바인딩한다.
        model.addAttribute("metaProfile", rpMetaProfile);
        // 9. 검색 체크값을 바인딩한다. - 1 : 검색 했을 경우
        model.addAttribute("check", 1);
        // 10. 검색에 사용할 DTO를 바인딩한다.
        model.addAttribute("metaDTO", new Meta.rqSearchMetaList());
        // 11. 메타 메인 페이지로 이동한다.
        return "Meta/MetaRoom";
    }

    // 스터디룸 페이지
    @GetMapping("/studyroom") // Principal - 자바의 표준 시큐리티 기술로, 로그인 유저의 정보를 담고 있다.
    public String studyRoom(@RequestParam long metaIdx, Principal principal, Model model) { // 1. 파라미터로 입장한 방 번호를 받아온다.
        // 2. Principal을 사용하여 로그인 유저의 아이디를 서비스에 전달한다.
        Member.rpNickImage rpNickImage = signUpService.memberNickImage(principal.getName());
        // 7. 1에서 파라미터로 받아온 방 번호와 2에서 반환받은 DTO를 서비스에 전달한다.
        Meta.rpEntrance rpEntrance = metaService.entrance(metaIdx, rpNickImage);
        // 14. 7에서 반환받은 DTO가 있는지 체크한다.
        // 14-1. 반환받은 DTO가 없는 경우 - 해당 방이 없는 경우
        if ( rpEntrance == null ) {
            // 14-1-1. 에러메세지를 바인딩한다.
            model.addAttribute("err", "해당 방의 정보가 없습니다.");
            // 14-1-2. 메타 메인 페이지로 이동한다.
            return "Meta/MetaRoom";
        // 14-2. 반환받은 DTO가 있는 경우 - 해당 방이 있는 경우
        } else {
            // 15. 7에서 반환받은 DTO 값 중 metaIdx가 0인지 체크한다.
            // 15-1. metaIdx가 0인 경우 - 모집인원이 정원초과
            if ( rpEntrance.getMetaIdx() == 0 ) {
                // 15-1-1. 7에서 반환받은 DTO 값 중 metaTitle에 저장되있는 에러메세지를 바인딩한다.
                model.addAttribute("err", rpEntrance.getMetaTitle());
                // 15-1-2. 메타 메인 페이지로 이동한다.
                return "Meta/MetaRoom";
            // 15-2. metaIdx가 0이 아닌 경우 - 해당 방에 입장
            } else {
                // 15-2-1. 1에서 파라미터로 받아온 방 번호를 서비스에 전달한다.
                List<MetaRoom.rpMetaRoomIdxList> rpMetaRoomIdxList = metaService.metaRoomParticipant(metaIdx);
                // 20. 2에서 반환받은 로그인 유저 정보 DTO를 바인딩한다.
                model.addAttribute("nickImage", rpNickImage);
                // 21. 7에서 반환받은 입장한 방 정보 DTO를 바인딩한다.
                model.addAttribute("metaRoom", rpEntrance);
                // 22. 15-2-1에서 반환받은 입장한 방 내부 참여자 명단 DTO를 바인딩한다.
                model.addAttribute("participantList", rpMetaRoomIdxList);
                // 23. 스터디룸 페이지로 이동한다.
                return "Meta/StudyRoom";
            }
        }
    }

    // 카페 페이지
    @GetMapping("/caferoom") // Principal - 자바의 표준 시큐리티 기술로, 로그인 유저의 정보를 담고 있다.
    public String cafeRoom(@RequestParam long metaIdx, Principal principal, Model model) { // 1. 파라미터로 입장한 방 번호를 받아온다.
        // 2. Principal을 사용하여 로그인 유저의 아이디를 서비스에 전달한다.
        Member.rpNickImage rpNickImage = signUpService.memberNickImage(principal.getName());
        // 7. 1에서 파라미터로 받아온 방 번호와 2에서 반환받은 DTO를 서비스에 전달한다.
        Meta.rpEntrance rpEntrance = metaService.entrance(metaIdx, rpNickImage);
        // 14. 7에서 반환받은 DTO가 있는지 체크한다.
        // 14-1. 반환받은 DTO가 없는 경우 - 해당 방이 없는 경우
        if ( rpEntrance == null ) {
            // 14-1-1. 에러메세지를 바인딩한다.
            model.addAttribute("err", "해당 방의 정보가 없습니다.");
            // 14-1-2. 메타 메인 페이지로 이동한다.
            return "Meta/MetaRoom";
        // 14-2. 반환받은 DTO가 있는 경우 - 해당 방이 있는 경우
        } else {
            // 15. 7에서 반환받은 DTO 값 중 metaIdx가 0인지 체크한다.
            // 15-1. metaIdx가 0인 경우 - 모집인원이 정원초과
            if ( rpEntrance.getMetaIdx() == 0 ) {
                // 15-1-1. 7에서 반환받은 DTO 값 중 metaTitle에 저장되있는 에러메세지를 바인딩한다.
                model.addAttribute("err", rpEntrance.getMetaTitle());
                // 15-1-2. 메타 메인 페이지로 이동한다.
                return "Meta/MetaRoom";
            // 15-2. metaIdx가 0이 아닌 경우 - 해당 방에 입장
            } else {
                // 15-2-1. 1에서 파라미터로 받아온 방 번호를 서비스에 전달한다.
                List<MetaRoom.rpMetaRoomIdxList> rpMetaRoomIdxList = metaService.metaRoomParticipant(metaIdx);
                // 20. 2에서 반환받은 로그인 유저 정보 DTO를 바인딩한다.
                model.addAttribute("nickImage", rpNickImage);
                // 21. 7에서 반환받은 입장한 방 정보 DTO를 바인딩한다.
                model.addAttribute("metaRoom", rpEntrance);
                // 22. 15-2-1에서 반환받은 입장한 방 내부 참여자 명단 DTO를 바인딩한다.
                model.addAttribute("participantList", rpMetaRoomIdxList);
                // 23. 카페 페이지로 이동한다.
                return "Meta/CafeRoom";
            }
        }
    }

    // 자습실 페이지
    @GetMapping("/oneroom") // Principal - 자바의 표준 시큐리티 기술로, 로그인 유저의 정보를 담고 있다.
    public String oneRoom(@RequestParam long metaIdx, Principal principal, Model model) { // 1. 파라미터로 입장한 방 번호를 받아온다.
        // 2. Principal을 사용하여 로그인 유저의 아이디를 서비스에 전달한다.
        Member.rpNickImage rpNickImage = signUpService.memberNickImage(principal.getName());
        // 7. 1에서 파라미터로 받아온 방 번호와 2에서 반환받은 DTO를 서비스에 전달한다.
        Meta.rpEntrance rpEntrance = metaService.entrance(metaIdx, rpNickImage);
        // 14. 7에서 반환받은 DTO가 있는지 체크한다.
        // 14-1. 반환받은 DTO가 없는 경우 - 해당 방이 없는 경우
        if ( rpEntrance == null ) {
            // 14-1-1. 에러메세지를 바인딩한다.
            model.addAttribute("err", "해당 방의 정보가 없습니다.");
            // 14-1-2. 메타 메인 페이지로 이동한다.
            return "Meta/MetaRoom";
        // 14-2. 반환받은 DTO가 있는 경우 - 해당 방이 있는 경우
        } else {
            // 15. 7에서 반환받은 DTO 값 중 metaIdx가 0인지 체크한다.
            // 15-1. metaIdx가 0인 경우 - 모집인원이 정원초과
            if ( rpEntrance.getMetaIdx() == 0 ) {
                // 15-1-1. 7에서 반환받은 DTO 값 중 metaTitle에 저장되있는 에러메세지를 바인딩한다.
                model.addAttribute("err", rpEntrance.getMetaTitle());
                // 15-1-2. 메타 메인 페이지로 이동한다.
                return "Meta/MetaRoom";
            // 15-2. metaIdx가 0이 아닌 경우 - 해당 방에 입장
            } else {
                // 15-2-1. 1에서 파라미터로 받아온 방 번호를 서비스에 전달한다.
                List<MetaRoom.rpMetaRoomIdxList> rpMetaRoomIdxList = metaService.metaRoomParticipant(metaIdx);
                // 20. 2에서 반환받은 로그인 유저 정보 DTO를 바인딩한다.
                model.addAttribute("nickImage", rpNickImage);
                // 21. 7에서 반환받은 입장한 방 정보 DTO를 바인딩한다.
                model.addAttribute("metaRoom", rpEntrance);
                // 22. 15-2-1에서 반환받은 입장한 방 내부 참여자 명단 DTO를 바인딩한다.
                model.addAttribute("participantList", rpMetaRoomIdxList);
                // 23. 자습실 페이지로 이동한다.
                return "Meta/OneRoom";
            }
        }
    }

    // 방 나가기
    @GetMapping("/exit")
    public String exitRoom(@RequestParam long metaIdx, Principal principal) { // 1. 파라미터로 입장한 방 번호를 받아온다.
        // 2. Principal을 사용하여 로그인 유저의 아이디를 서비스에 전달한다.
        Member.rpNickImage rpNickImage = signUpService.memberNickImage(principal.getName());
        // 3. 1에서 파라미터로 받아온 방 번호와 2에서 반환받은 DTO를 서비스에 전달하다.
        metaService.exit(metaIdx, rpNickImage);
        // 4. 메타 메인 페이지로 리다이렉트한다.
        return "redirect:/meta";
    }
}