package com.study.soju.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.study.soju.config.IamPortPass;
import com.study.soju.entity.Member;
import com.study.soju.entity.Pay;
import com.study.soju.entity.Store;
import com.study.soju.service.PayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;
import java.util.List;

@Controller
//맨 앞에 '/store' 라는 url 값이 들어와야지만 스토어에 관련된 페이지로 이동
@RequestMapping("/store")
public class StoreController {
    //PayService클래스를 쓰기위해 작성
    @Autowired
    PayService payService;

    //url값을 "" 로 설정해서 '/store' 만 들어오면 스토어의 메인페이지로 이동
    @GetMapping("")
    public String store(){
        return"Store/Store";
    }

    //'/store/memvershiplist' 이라는 url 값이 들어오면 memberShipList 메서드로 들어온다.
    @GetMapping("/membershiplist")
    //Model 은 이동하려는 페이지에 객체를 넘겨주기위해 선언하다.
    public String membershipList(Model model){
        //카테고리 별로 나눠서 검색
        //파라미터로 membership 을 넘겨줘서
        //카테고리가 membership 인 모든 객체들을 리스트로 반환
        List<Store> memberShipList = payService.findList("membership");
        //catagoryList 라는 이름으로 이동한 페이지에서 사용할 수 있게 리스트를 추가한다.
        model.addAttribute("categoryList", memberShipList);
        return "Store/MemberShipList";
    }

    //'/store/booklist' 이라는 url 값이 들어오면 bookList 메서드로 들어온다.
    @GetMapping("/booklist")
    public String bookList(Model model){
        //카테고리 별로 나눠서 검색
        //파라미터로 book 을 넘겨줘서
        //카테고리가 book 인 모든 객체들을 리스트로 반환
        List<Store> bookList = payService.findList("book");
        //catagoryList 라는 이름으로 이동한 페이지에서 사용할 수 있게 리스트를 추가한다.
        model.addAttribute("categoryList", bookList);
        return "Store/BookList";
    }

    //'/store/etclist' 이라는 url 값이 들어오면 etcList 메서드로 들어온다.
    @GetMapping("/etclist")
    public String etcList(Model model){
        //카테고리 별로 나눠서 검색
        //파라미터로 etc 을 넘겨줘서
        //카테고리가 etc 인 모든 객체들을 리스트로 반환
        List<Store> etcList = payService.findList("etc");
        //catagoryList 라는 이름으로 이동한 페이지에서 사용할 수 있게 리스트를 추가한다.
        model.addAttribute("categoryList", etcList);
        return "Store/EtcList";
    }

    //'/store/pay' 이라는 url 값이 들어오면 pay 메서드로 들어온다.
    @GetMapping("/pay")
    //이전 페이지의 url 에서 Store 객체에 맞는 값들을 자동으로 파라미터로 받아온다.
    public String pay(Store store, Model model){
        //Store 객체로 받아온 것중 itemName 값을 파라미터로 넘겨준다.
        //이후 itemName 으로 찾아온 Store 객체들을 buyItem 이라는 이름으로 저장
        Store buyItem = payService.findStore(store.getItemName());
        //저장된 Store 객체를 모델에 넣고 넘어가는 페이지에서 사용할 수 있도록 추가
        model.addAttribute("item", buyItem);
        return "Store/Pay";
    }

    //'/store/purchase' 이라는 url 값이 들어오면 popupPay 메서드로 들어온다.
    @GetMapping("/purchase")
    //이전 페이지의 url 에서 Store 객체에 맞는 값들을 자동으로 파라미터로 받아온다.
    ////이전 페이지의 url 에서 Principal 객체에 emailId 값을 담아서 파라미터로 받아온다.(스프링 시큐리티 부분)
    public String popupPay(Store store, Pay pay, Model model, Principal principal){
        //결제를 하려는 갯수를 저장한다.
        int itemCount = pay.getItemCount();
        //다음 팝업 페이지에서 사용할 수 있도록 모델에 값을 추가한다.
        model.addAttribute("itemCount", itemCount);
        //결제를 하려는 항목을 itemName 값으로 불러온다.
        Store payItem = payService.findStore(store.getItemName());
        //다음 팝업 페이지에서 사용할 수 있도록 모델에 Store 객체를 저장한다.
        model.addAttribute("item", payItem);
        //principal 에 저장된 emailId 값을 따로 변수에 저장한다.
        String emailId = principal.getName();
        //저장한 emailId 값으로 멤버 정보를 가지고 온다.
        Member member = payService.findAll(emailId);
        //다음 팝업 페이지에서 사용할 수 있도록 모델에 Member 객체를 저장한다.
        model.addAttribute("member", member);
        return "Store/PayPopup";
    }

    //PayPopup 페이지에서 Request 를 보낸걸 컨트롤러에서 받아올 수 있도록 어노테이션을 붙여준다
    //url 주소는 '/complete', 메서드는 'POST' 방식으로 들어온 값만 받을 수 있게 설정한다.
    @RequestMapping(value = {"/complete"}, method = {RequestMethod.POST})
    //ResponseBody 라는 어노테이션으로 방금 받아온 값들을 처리해서 결과값을 다시 이전 페이지로 보내준다.
    @ResponseBody
    public String payComplete(Pay pay){
        JsonNode jsonToken = IamPortPass.getToken(); //서버로 부터 토큰값 받아옴 (Json 형식)
        String accessToken = jsonToken.get("response").get("access_token").asText(); //서버로 부터 토큰값 받아옴 (Text)

        JsonNode payment = IamPortPass.getUserInfo(pay.getImpUid(), accessToken);
        System.out.println(payment);
        //가져온 json 형식의  payment 를 문자열로 변환
        String status = payment.get("response").get("status").asText();
        String resImpUid = payment.get("response").get("imp_uid").asText();
        String resMerchantUid = payment.get("response").get("merchant_uid").asText();

        String res = "no";
        if(pay.getImpUid().equals(resImpUid)){
            if(pay.getMerchantUid().equals(resMerchantUid)){
                if(status.equalsIgnoreCase("paid")){
                    //만약 모든 결과값이 일치한다면 결제 완료된 정보를 데이터베이스에 저장
                    payService.insertPay(pay);
                    res = "yes";
                }

            }
        }
        return res;
    }
}
